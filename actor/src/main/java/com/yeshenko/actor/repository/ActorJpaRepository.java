package com.yeshenko.actor.repository;

import com.yeshenko.actor.dto.db.HardworkingActor;
import com.yeshenko.common.entity.Person;
import com.yeshenko.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Repository
public interface ActorJpaRepository extends JpaRepository<Person, String> {

    List<Person> findAllByRoleIn(List<Role> roles);

    @Query(value = "" +
            "SELECT p.full_name AS fullName, COUNT(mp.person_id) AS maxCountMovies " +
            "FROM people AS p " +
            "INNER JOIN movie_person AS mp " +
            "ON mp.person_id = p.person_id " +
            "WHERE p.role = 'ACTOR' OR p.role = 'ACTRESS' " +
            "GROUP BY p.person_id " +
            "HAVING COUNT(mp.person_id) = " +
            "(" +
            "SELECT COUNT(mp.person_id) AS max_count_movies " +
            "FROM movie_person AS mp " +
            "INNER JOIN people AS p " +
            "ON p.person_id = mp.person_id " +
            "WHERE p.role = 'ACTOR' OR p.role = 'ACTRESS' " +
            "GROUP BY mp.person_id "  +
            "ORDER BY max_count_movies DESC " +
            "LIMIT 1" +
            ")", nativeQuery = true)
    List<HardworkingActor> findAllHardworkingActors();

    @Query("SELECT stringId FROM Person")
    List<String> findAllId();

    List<Person> findAllByStringId(String stringId);
}
