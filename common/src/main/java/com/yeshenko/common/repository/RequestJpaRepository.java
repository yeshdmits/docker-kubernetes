package com.yeshenko.common.repository;

import com.yeshenko.common.entity.Request;
import com.yeshenko.common.entity.RequestType;
import com.yeshenko.common.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Repository
public interface RequestJpaRepository extends JpaRepository<Request, Long> {

    List<Request> findAllByUsername(String username);

    List<Request> findAllByType(RequestType type);

    List<Request> findAllByResult(Result result);
}