package com.decadance.Back_DecaDance_PFI.service;

import com.decadance.Back_DecaDance_PFI.dto.external.ExportifyCsvDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.DataResponseDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.DeezerSearchResponseDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.SongRequestDTO;
import com.decadance.Back_DecaDance_PFI.entity.Genre;
import com.decadance.Back_DecaDance_PFI.entity.Song;
import com.decadance.Back_DecaDance_PFI.mapper.SongMapper;
import com.decadance.Back_DecaDance_PFI.repository.SongRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MusicImportService {

    private final WebClient webClient;
    private final SongRepository songRepository;
    private final SongMapper songMapper;
    private final GenreService genreService;

    public MusicImportService(WebClient webClient, SongRepository songRepository, SongMapper songMapper , GenreService genreService) {
        this.webClient = webClient;
        this.songRepository = songRepository;
        this.songMapper = songMapper;
        this.genreService = genreService;
    }

    public void importSongsFromCsv(String fileName) {
        try {
            ClassPathResource resource = new ClassPathResource("data/" + fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream()));

            List<ExportifyCsvDTO> csvSongs = new CsvToBeanBuilder<ExportifyCsvDTO>(reader)
                    .withType(ExportifyCsvDTO.class)
                    .withSkipLines(1)
                    .build()
                    .parse();
            for (ExportifyCsvDTO row : csvSongs) {
                fetchAndSaveSong(row);
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            System.out.println("Importación de " + fileName + " completada con éxito.");
        } catch (Exception e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
        }
    }

    public List<SongRequestDTO> searchSongsInDeezer(String query) {
        DeezerSearchResponseDTO searchResponse = executeDeezerSearch(query);

        if (searchResponse == null || searchResponse.data() == null)
            return List.of();
        
        List<SongRequestDTO> finalResults = new ArrayList<>();
        for (DataResponseDTO trackInfo : searchResponse.data().stream().limit(50).toList()) {
            EnrichedData enriched = enrichSongData(trackInfo.album().id(), trackInfo.title());
            Genre genre = genreService.getOrCreateGenre(enriched.genreName());
            SongRequestDTO songDTO = new SongRequestDTO(
                    trackInfo.id(),
                    trackInfo.title(),
                    trackInfo.artist().name(),
                    enriched.year(),
                    genre.getIdGenre(),
                    trackInfo.album().coverMedium(),
                    trackInfo.preview());
            finalResults.add(songDTO);
        }
        return finalResults;
    }

    private void fetchAndSaveSong(ExportifyCsvDTO row) {
        String track = row.getTrackName();
        String cleanArtist = row.getArtistName().split(",")[0].trim();
        String query = track + " " + cleanArtist;

        DeezerSearchResponseDTO searchResponse = executeDeezerSearch(query);

        if (searchResponse != null && !searchResponse.data().isEmpty()) {
            DataResponseDTO trackInfo = searchResponse.data().get(0);

            if (songRepository.findByDeezerId(trackInfo.id()).isPresent()) {
                System.out.println("⏭️ Saltando (ya existe): " + trackInfo.title());
                return;
            }

            EnrichedData enriched = enrichSongData(trackInfo.album().id(), trackInfo.title());

            Song song = songMapper.toEntity(trackInfo);
            song.setYear(enriched.year());
            
            Genre genreEntity = genreService.getOrCreateGenre(enriched.genreName());
            song.setGenre(genreEntity);
            
            songRepository.save(song);
        }
    }

    private DeezerSearchResponseDTO executeDeezerSearch(String query) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .bodyToMono(DeezerSearchResponseDTO.class)
                .block();
    }

    private EnrichedData enrichSongData(Long albumId, String title) {
        Integer year = 0;
        String genreName = "unknown";
        try {
            DataResponseDTO.AlbumDTO albumDetail = webClient.get()
                    .uri("/album/" + albumId)
                    .retrieve()
                    .bodyToMono(DataResponseDTO.AlbumDTO.class)
                    .block();
            if (albumDetail != null) {
                if (albumDetail.releaseDate() != null && albumDetail.releaseDate().length() >= 4) {
                    year = Integer.parseInt(albumDetail.releaseDate().substring(0, 4));
                }
                if (albumDetail.genres() != null && albumDetail.genres().data() != null
                        && !albumDetail.genres().data().isEmpty()) {
                    genreName = albumDetail.genres().data().get(0).name();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar detalles del álbum para: " + title);
        }
        return new EnrichedData(year, genreName);
    }

    private record EnrichedData(Integer year, String genreName) {
    }

}