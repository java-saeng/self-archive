package com.elasticsearch.practice.entity;

import com.elasticsearch.practice.respository.ClubRepository;
import com.elasticsearch.practice.respository.elasticsearch.ClubSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubSearchRepository clubSearchRepository;

    @Transactional
    public Long save(String title, String description) {
        Club club = new Club(title, description);

        Club savedClub = clubRepository.save(club);
        clubSearchRepository.save(savedClub);
        return savedClub.getId();
    }

    public List<Club> searchByTitleUsingElasticSearchWithContainQueryBuilder(String title) {
        return new ArrayList<>(clubSearchRepository.searchByTitle(title));
    }


    public List<Club> findByTitleUsingElasticSearchWithNotContain(String title) {
        return clubSearchRepository.findByTitle(title);
    }

    public List<Club> findByTitleWithoutEs(String title) {
        return clubRepository.findByTitleContaining(title);
    }

    public List<Club> findByTitleUsingEsWithContaining(String title) {
        return new ArrayList<>(clubSearchRepository.findByTitleContaining(title));
    }
}
