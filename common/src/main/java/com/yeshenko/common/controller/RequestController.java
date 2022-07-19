package com.yeshenko.common.controller;

import com.yeshenko.common.Constants;
import com.yeshenko.common.entity.Request;
import com.yeshenko.common.entity.RequestType;
import com.yeshenko.common.entity.Result;
import com.yeshenko.common.error.handler.exceptions.DataNotFoundException;
import com.yeshenko.common.error.handler.exceptions.InvalidParameterException;
import com.yeshenko.common.service.RequestJpaService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.yeshenko.common.Constants.LIST_IS_EMPTY;
import static com.yeshenko.common.Constants.PARAM_HAS_WRONG_TYPE;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/requests")
@ComponentScan(basePackages = "com.yeshenko.common")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class RequestController {

    private RequestJpaService requestJpaService;

    /**
     * Get all requests by type.
     * Search by database.
     *
     * @param type string value
     * @return list of Request objects
     */
    @GetMapping("/type")
    public List<Request> findAllByType(@RequestParam("type") String type) {

        if (!EnumUtils.isValidEnum(RequestType.class, type)) {
            log.warn(PARAM_HAS_WRONG_TYPE, "RequestType");
            throw new InvalidParameterException();
        }

        List<Request> requests = requestJpaService.findAllByType(type);

        if (requests.size() == 0) {
            log.warn(LIST_IS_EMPTY, "requests");
            throw new DataNotFoundException();
        }
        return requests;
    }

    /**
     * Get all requests by username.
     * Search by database.
     *
     * @param username string value
     * @return list of Request objects
     */
    @GetMapping("/username")
    public List<Request> findAllByUsername(@RequestParam(Constants.USERNAME_LOWER_CASE) String username) {
        List<Request> requests = requestJpaService.findAllByUsername(username);
        if (requests.size() == 0) {
            log.warn(LIST_IS_EMPTY, "requests");
            throw new DataNotFoundException();
        }
        return requests;
    }

    /**
     * Get all requests by result.
     * Search by database.
     *
     * @param result string value
     * @return list of Request objects
     */
    @GetMapping("/result")
    public List<Request> findAllByResult(@RequestParam("result") String result) {

        if (!EnumUtils.isValidEnum(Result.class, result)) {
            log.warn(PARAM_HAS_WRONG_TYPE, "RequestType");
            throw new InvalidParameterException();
        }

        List<Request> requests = requestJpaService.findAllByResult(result);
        if (requests.size() == 0) {
            log.warn(LIST_IS_EMPTY, "requests");
            throw new DataNotFoundException();
        }
        return requests;
    }
}
