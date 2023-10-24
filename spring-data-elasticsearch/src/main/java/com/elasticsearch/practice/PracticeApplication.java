package com.elasticsearch.practice;

import com.elasticsearch.practice.respository.ClubRepository;
import com.elasticsearch.practice.respository.elasticsearch.ClubSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
		type = FilterType.ASSIGNABLE_TYPE,
		classes = ClubSearchRepository.class
))
@SpringBootApplication
public class PracticeApplication {

	@Autowired
	ClubRepository clubRepository;

	@Autowired
	ClubSearchRepository clubSearchRepository;

	public static void main(String[] args) {
		SpringApplication.run(PracticeApplication.class, args);
	}

//	@PostConstruct
//	public void init() {
//		for (int i = 10000; i <= 100000; i++) {
//			Club club = new Club("hyeonwoo " + i, "description" + i);
//			Club savedClub = clubRepository.save(club);
//			clubSearchRepository.save(savedClub);
//		}
//	}
}
