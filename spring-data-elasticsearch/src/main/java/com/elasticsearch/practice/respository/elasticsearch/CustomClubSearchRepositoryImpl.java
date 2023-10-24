package com.elasticsearch.practice.respository.elasticsearch;

import com.elasticsearch.practice.entity.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

@RequiredArgsConstructor
@Repository
public class CustomClubSearchRepositoryImpl implements CustomClubSearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<Club> searchByTitle(String title) {
        Query query = new NativeSearchQueryBuilder()
                .withFilter(matchQuery("title", "*" + title + "*"))
                .build();

        SearchHits<Club> search = elasticsearchOperations.search(query, Club.class);
        return search.stream()
                     .map(SearchHit::getContent)
                     .collect(Collectors.toList());
    }
}
