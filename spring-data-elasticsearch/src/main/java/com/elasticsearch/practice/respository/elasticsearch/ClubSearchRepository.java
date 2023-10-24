package com.elasticsearch.practice.respository.elasticsearch;

import com.elasticsearch.practice.entity.Club;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface ClubSearchRepository extends ElasticsearchRepository<Club, Long>, CustomClubSearchRepository {
    List<Club> findByTitle(String title);

    List<Club> findByTitleContaining(String title);
}
