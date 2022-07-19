package com.yeshenko.actor.repository;

import com.yeshenko.common.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
public interface MovieJpaRepository extends JpaRepository<Movie, String> {
    Movie findMovieById(String id);
}
