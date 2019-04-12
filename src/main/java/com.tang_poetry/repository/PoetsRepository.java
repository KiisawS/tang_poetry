package com.tang_poetry.repository;

import com.tang_poetry.domain.Poets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PoetsRepository extends JpaRepository<Poets, Integer> {

    Poets findByName(String name);
}
