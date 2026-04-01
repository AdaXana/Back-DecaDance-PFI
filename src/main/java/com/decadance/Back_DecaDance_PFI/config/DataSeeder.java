// package com.decadance.Back_DecaDance_PFI.config;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;
// import com.decadance.Back_DecaDance_PFI.repository.SongRepository;
// import com.decadance.Back_DecaDance_PFI.service.MusicImportService;

// @Component
// public class DataSeeder implements CommandLineRunner {

//     private final MusicImportService musicImportService;
//     private final SongRepository songRepository;

//     public DataSeeder(MusicImportService musicImportService, SongRepository songRepository) {
//         this.musicImportService = musicImportService;
//         this.songRepository = songRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (songRepository.count() == 0) {
//             System.out.println("Base de datos vacía. Iniciando importación del mazo (futuras salas para la V2.0) desde CSV...");
//             musicImportService.importSongsFromCsv();
//             System.out.println("Importación de canciones completada con éxito.");
//         } else {
//             System.out.println("La base de datos ya contiene canciones. Saltando importación del CSV.");
//         }
//     }
// }