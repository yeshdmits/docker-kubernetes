package com.yeshenko.director.repository;

import com.yeshenko.common.entity.Person;
import com.yeshenko.common.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Repository
public interface DirectorJpaRepository extends JpaRepository<Person, Long> {

    List<Person> findAllByRole(Role role);

    Person findPersonById(Long id);
}
