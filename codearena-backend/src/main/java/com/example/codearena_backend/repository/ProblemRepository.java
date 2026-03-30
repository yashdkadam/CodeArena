package com.example.codearena_backend.repository;

import com.example.codearena_backend.entity.Problem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {

    @Query(value = "SELECT * FROM problem ORDER BY RAND() LIMIT 1", nativeQuery = true)
    Problem findRandomProblem();
}