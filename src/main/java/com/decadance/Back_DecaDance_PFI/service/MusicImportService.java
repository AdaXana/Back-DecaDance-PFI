package com.decadance.Back_DecaDance_PFI.service;

import com.decadance.Back_DecaDance_PFI.dto.external.ExportifyCsvDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.DataResponseDTO;
import com.decadance.Back_DecaDance_PFI.dto.response.DeezerSearchResponseDTO;
import com.decadance.Back_DecaDance_PFI.entity.Song;
import com.decadance.Back_DecaDance_PFI.mapper.SongMapper;
import com.decadance.Back_DecaDance_PFI.repository.SongRepository;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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

        DeezerSearchResponseDTO searchResponse = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("q", query)
                        .build())
                .retrieve()
                .bodyToMono(DeezerSearchResponseDTO.class)
                .block();

        if (searchResponse != null && !searchResponse.data().isEmpty()) {
            DataResponseDTO trackInfo = searchResponse.data().get(0);

            DataResponseDTO.AlbumDTO albumDetail = webClient.get()
                    .uri("/album/" + trackInfo.album().id())
                    .retrieve()
                    .bodyToMono(DataResponseDTO.AlbumDTO.class)
                    .block();

            Song song = songMapper.toEntity(trackInfo);

            if (albumDetail != null) {
                if (albumDetail.releaseDate() != null && albumDetail.releaseDate().length() >= 4) {
                    song.setYear(Integer.parseInt(albumDetail.releaseDate().substring(0, 4)));
                }

                if (albumDetail.genres() != null && !albumDetail.genres().data().isEmpty()) {
                    song.setGenre(albumDetail.genres().data().get(0).name());
                } else {
                    song.setGenre("all");
                }
            }
            songRepository.save(song);
        }
    }
}