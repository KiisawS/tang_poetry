package com.tang_poetry.repository;

import com.tang_poetry.domain.Poetries;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PoetriesRepository extends JpaRepository<Poetries, Integer> {

    List<Poetries> findByTitleIsLike(String title);

    List<Poetries> findAllByPoetId(Integer id);


    List<Poetries> findAllByTitle(String title, Pageable pageable);
}
