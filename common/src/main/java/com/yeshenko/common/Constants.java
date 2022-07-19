package com.yeshenko.common;

/**
 * @author Yeshenko Dmitriy
 * @version 1.0
 */
public class Constants {

    private Constants() {
    }

    public static final String ACTOR_LOWER_CASE = "actor";

    public static final String ACTRESS_LOWER_CASE = "actress";

    public static final String DIRECTOR_LOWER_CASE = "director";

    public static final String MOVIE_LOWER_CASE = "movie";

    public static final String ROLES_FILE = "roles.tsv";

    public static final String PEOPLE_FILE = "people.tsv";

    public static final String RATINGS_FILE = "ratings.tsv";

    public static final String MOVIES_FILE = "movies.tsv";

    public static final String NAME_OF_FIRST_QUEUE_ACTIVE_MQ = "moviesIds";

    public static final String NAME_OF_SECOND_QUEUE_ACTIVE_MQ = "directorsIds";

    public static final String USERNAME_LOWER_CASE = "username";

    public static final String PROBLEM_WITH_FILE = "File '{}' not found or can't be read";

    public static final String LIST_IS_EMPTY = "List '{}' is empty.";

    public static final String PARAM_IS_BLANK = "Parameter is blank.";

    public static final String PARAM_HAS_WRONG_TYPE = "Parameter is not {} type.";

    public static final String METHOD_STARTED_WORK = "Method '{}' started work.";

    public static final String METHOD_STARTED_WORK_WITH_PARAM = "Method '{}' started work with parameter = {}.";

    public static final String METHOD_STARTED_WORK_WITH_TWO_PARAMS = "Method '{}' started work with parameters = {}, {}.";

    public static final String METHOD_STARTED_WORK_WITH_LIST = "Method '{}' started work with list size - {}.";

    public static final String METHOD_EXITED = "Method '{}' has successfully completed work.";

    public static final String METHOD_EXITED_BY_RETURNING_PARAM = "Method '{}' has successfully completed work by returning parameter = {}.";

    public static final String METHOD_EXITED_BY_RETURNING_LIST = "Method '{}' has successfully completed work by returning list with size = {}.";

    public static final String METHOD_EXITED_BY_RETURNING_MAP = "Method '{}' has successfully completed work by returning map with size = {}.";

    public static final String METHOD_EXITED_BY_RETURNING_OBJECT = "Method '{}' has successfully completed work by returning object.";
}
