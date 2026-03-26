package com.decadance.Back_DecaDance_PFI.service;

import com.decadance.Back_DecaDance_PFI.dto.external.ExportifyCsvDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.DataResponseDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.DeezerSearchResponseDTO;
import com.decadance.Back_DecaDance_PFI.dto.request.SongRequestDTO;
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

    public MusicImportService(WebClient webClient, SongRepository songRepository, SongMapper songMapper) {
        this.webClient = webClient;
        this.songRepository = songRepository;
        this.songMapper = songMapper;
    }

    public void importSongsFromCsv() {
        try {
            ClassPathResource resource = new ClassPathResource("data/pills.csv");
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
        } catch (Exception e) {
            System.err.println("Error al leer el CSV: " + e.getMessage());
        }
    }

    private void fetchAndSaveSong(ExportifyCsvDTO row) {
        String track = row.getTrackName();
        String cleanArtist = row.getArtistName().split(",")[0].trim();
        String query = track + " " + cleanArtist;

        DeezerSearchResponseDTO searchResponse = executeDeezerSearch(query);

        if (searchResponse != null && !searchResponse.data().isEmpty()) {
            DataResponseDTO trackInfo = searchResponse.data().get(0);

            EnrichedData enriched = enrichSongData(trackInfo.album().id(), trackInfo.title());

            Song song = songMapper.toEntity(trackInfo);
            song.setYear(enriched.year());
            song.setGenre(enriched.genre());
            songRepository.save(song);
        }
    }

    public List<SongRequestDTO> searchSongsInDeezer(String query) {
        DeezerSearchResponseDTO searchResponse = executeDeezerSearch(query);

        if (searchResponse == null || searchResponse.data() == null || searchResponse.data().isEmpty()) {
            return List.of();
        }
        List<DataResponseDTO> topResults = searchResponse.data().stream().limit(50).toList();
        List<SongRequestDTO> finalResults = new ArrayList<>();
        for (DataResponseDTO trackInfo : topResults) {
            EnrichedData enriched = enrichSongData(trackInfo.album().id(), trackInfo.title());
            SongRequestDTO songDTO = new SongRequestDTO(
                    trackInfo.id(),
                    trackInfo.title(),
                    trackInfo.artist().name(),
                    enriched.year(),
                    enriched.genre(),
                    trackInfo.album().coverMedium(),
                    trackInfo.preview());
            finalResults.add(songDTO);
        }
        return finalResults;
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
        String genre = "all";
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
                    genre = albumDetail.genres().data().get(0).name();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al cargar detalles del álbum para: " + title);
        }
        return new EnrichedData(year, genre);
    }

    private record EnrichedData(Integer year, String genre) {
    }

}