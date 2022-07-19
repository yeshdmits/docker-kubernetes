package com.yeshenko.movie.repository;

import com.yeshenko.common.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Repository
public interface MovieJpaRepository extends JpaRepository<Movie, String> {

    List<Movie> findByName(String name);
}