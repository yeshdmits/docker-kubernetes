package com.yeshenko.director.repository;

import com.yeshenko.common.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Repository
public interface MovieByDirectorJpaRepository extends JpaRepository<Movie, String> {

    @Query("SELECT id from Movie")
    List<String> findAllId();
}
