package com.yeshenko.director.util;

import com.yeshenko.common.error.handler.exceptions.DataNotFoundException;
import com.yeshenko.common.error.handler.exceptions.InvalidParameterException;
import com.yeshenko.director.dto.DirectorDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

import static com.yeshenko.common.Constants.LIST_IS_EMPTY;
import static com.yeshenko.common.Constants.PARAM_IS_BLANK;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
@Slf4j
public class Validator {

    /**
     * Validate list of dto.
     *
     * @param directorDtoList list of DirectorDto objects
     * @param nameList        string value
     */
    public static void checkOnEmpty(List<DirectorDto> directorDtoList, String nameList) {
        if (CollectionUtils.isEmpty(directorDtoList)) {
            log.warn(LIST_IS_EMPTY, nameList);
            throw new DataNotFoundException();
        }
    }

    /**
     * Validate string value for emptiness
     *
     * @param name string value
     */
    public static void checkInputParameter(String name) {
        if (StringUtils.isBlank(name)) {
            log.warn(PARAM_IS_BLANK);
            throw new InvalidParameterException();
        }
    }
}
