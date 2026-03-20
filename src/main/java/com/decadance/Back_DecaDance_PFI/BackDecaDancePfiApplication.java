package com.decadance.Back_DecaDance_PFI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.boot.CommandLineRunner;
// import com.decadance.Back_DecaDance_PFI.service.MusicImportService;

@SpringBootApplication
public class BackDecaDancePfiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackDecaDancePfiApplication.class, args);
	}

	// @Bean
	// CommandLineRunner initData(MusicImportService musicImportService) {
	// 	return args -> {
	// 		musicImportService.importSongsFromCsv();
	// 	};
	// }

}