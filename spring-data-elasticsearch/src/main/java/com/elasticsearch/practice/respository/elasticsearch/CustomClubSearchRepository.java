package com.elasticsearch.practice.respository.elasticsearch;

import com.elasticsearch.practice.entity.Club;

import java.util.List;

public interface CustomClubSearchRepository {
    List<Club> searchByTitle(String title);
}
