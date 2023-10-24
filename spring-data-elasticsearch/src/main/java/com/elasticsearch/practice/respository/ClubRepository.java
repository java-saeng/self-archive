package com.elasticsearch.practice.respository;

import com.elasticsearch.practice.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club, Long> {

    List<Club> findByTitleContaining(String title);
}
