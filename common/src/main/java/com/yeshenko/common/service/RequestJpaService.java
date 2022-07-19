package com.yeshenko.common.service;

import com.yeshenko.common.entity.Request;
import com.yeshenko.common.entity.RequestType;
import com.yeshenko.common.entity.Result;
import com.yeshenko.common.repository.RequestJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.yeshenko.common.Constants.METHOD_EXITED_BY_RETURNING_LIST;
import static com.yeshenko.common.Constants.METHOD_STARTED_WORK_WITH_PARAM;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RequestJpaService {

    private RequestJpaRepository repository;

    /**
     * Get all requests by username, which saved in the database.
     *
     * @param username was used in request
     * @return list of Request objects
     */
    @Transactional(readOnly = true)
    public List<Request> findAllByUsername(String username) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "findAllByUsername(String username)", username);
        List<Request> allByUsername = repository.findAllByUsername(username);
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findAllByUsername(String username)", allByUsername.size());
        return allByUsername;
    }

    /**
     * Get all requests by type, which saved in the database.
     *
     * @param type was used in request
     * @return list of Request objects
     */
    @Transactional(readOnly = true)
    public List<Request> findAllByType(String type) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "findAllByType(String type)", type);
        List<Request> allByType = repository.findAllByType(RequestType.valueOf(type));
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findAllByType(String type)", allByType.size());
        return allByType;
    }

    /**
     * Get all requests by result, which saved in the database.
     *
     * @param result was used in request
     * @return list of Request objects
     */
    @Transactional(readOnly = true)
    public List<Request> findAllByResult(String result) {
        log.info(METHOD_STARTED_WORK_WITH_PARAM, "findAllByResult(String result)", result);
        List<Request> allByResult = repository.findAllByResult(Result.valueOf(result));
        log.info(METHOD_EXITED_BY_RETURNING_LIST, "findAllByResult(String result)", allByResult.size());
        return allByResult;
    }

    /**
     * Save request in database.
     *
     * @param request Request object from controllers
     * @return Request object
     */
    @Transactional
    public Request create(Request request) {
        return repository.save(request);
    }
}