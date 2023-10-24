package com.elasticsearch.practice.controller;

import com.elasticsearch.practice.entity.Club;
import com.elasticsearch.practice.entity.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ClubController {

    private final ClubService clubService;

    @PostMapping("/clubs")
    public ResponseEntity<Void> save(@RequestParam("title") String title, @RequestParam("description") String description) {
        Long id = clubService.save(title, description);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //mysql like query 사용하여 full text
    @GetMapping("/clubs/{title}/v1")
    public ResponseEntity<List<Club>> searchV1(@PathVariable String title) {
        long start = System.currentTimeMillis();

        List<Club> searchedClubs = new ArrayList<>(clubService.findByTitleWithoutEs(title));

        long finish = System.currentTimeMillis();

        System.out.println("시간 = " + (finish - start));
        System.out.println("searchedClubs = " + searchedClubs.size());

        return ResponseEntity.ok(searchedClubs);
    }

    //non contain
    @GetMapping("/clubs/{title}/v2")
    public ResponseEntity<List<Club>> searchV2(@PathVariable String title) {
        long start = System.currentTimeMillis();
        List<Club> searchedClubs = new ArrayList<>(clubService.findByTitleUsingElasticSearchWithNotContain(title));
        long finish = System.currentTimeMillis();

        System.out.println("시간 = " + (finish - start));
        System.out.println("searchedClubs = " + searchedClubs.size());

        return ResponseEntity.ok(searchedClubs);
    }

    //쿼리 빌더 사용(with contain)
    @GetMapping("/clubs/{title}/v3")
    public ResponseEntity<List<Club>> searchV3(@PathVariable String title) {
        long start = System.currentTimeMillis();

        List<Club> searchedClubs = new ArrayList<>(clubService.searchByTitleUsingElasticSearchWithContainQueryBuilder(title));
        long finish = System.currentTimeMillis();

        System.out.println("시간 = " + (finish - start));
        System.out.println("searchedClubs = " + searchedClubs.size());

        return ResponseEntity.ok(searchedClubs);
    }

    //using contain
    @GetMapping("/clubs/{title}/v4")
    public ResponseEntity<List<Club>> searchV4(@PathVariable String title) {
        long start = System.currentTimeMillis();

        List<Club> searchedClubs = new ArrayList<>(clubService.findByTitleUsingEsWithContaining(title));
        long finish = System.currentTimeMillis();

        System.out.println("시간 = " + (finish - start));
        System.out.println("searchedClubs = " + searchedClubs.size());

        return ResponseEntity.ok(searchedClubs);
    }
}
