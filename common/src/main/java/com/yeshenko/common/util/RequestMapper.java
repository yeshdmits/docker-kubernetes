package com.yeshenko.common.util;

import com.yeshenko.common.Constants;
import com.yeshenko.common.entity.Request;
import com.yeshenko.common.entity.RequestType;
import com.yeshenko.common.entity.Result;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
public class RequestMapper {

    /**
     * Static method that returns custom Request,
     * sets the username, time, request result and type.
     *
     * @param httpServletRequest from controllers from any module
     * @param type               which module called this method
     * @return custom {@code com.yeshenko.common.entity.Request}
     */
    public static Request mapDefaultRequest(HttpServletRequest httpServletRequest, RequestType type) {

        String username = httpServletRequest.getHeader(Constants.USERNAME_LOWER_CASE);
        if (Objects.isNull(username) || username.isEmpty()) {
            username = "user";
        }
        Request request = new Request();
        request.setUsername(username);
        request.setTime(LocalDateTime.now());
        request.setResult(Result.FAILURE);
        request.setType(type);
        return request;
    }
}