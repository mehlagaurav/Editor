package com.reverb.editor.constants;

public class EditorConstants {
    public static String KEYWORDS = "keywords";

    public static String AUHTOR = "author";

    public static final String ARTICLE_CREATION_FAIL_MSG = "Article creation failed due to {}";

    public static final String ARTICLE_UPDATION_FAIL_MSG = "Article updation failed due to {}";

    public static final String ARTICLE_DELETION_FAIL_MSG = "Article deletion failed due to {}";

    public static final String INTERNAL_PROCESSING_ERROR = "Article could not be created due to failure during internal processing. "
            + "Please try again later.";

    public static final String EDITORDAOEXCEPTION = "EditorDaoException Occured : {}";

    public static final String DELETING_ARTICLE_WITH_HEADER = "Deleting the article with header {}";

    public static final String RESOURCE_NOT_FOUND = "Article with criteria {} does not exist";

    public static final String INVALID_SEARCH_CRITERIA = "Filter criteria used is invalid";

    public static final String _500_INTERNAL_SERVER_ERROR_MESSAGE = "Unable to process request.";

    public static final int MAX_HEADER_SIZE_ALLOWED = 20;

    public static final int MAX_DESCRIPTION_SIZE_ALLOWED = 255;

    public static final String HEADER_CONSTRAINT_MESSAGE = "Article name field cannot be longer than 40 characters";

    public static final String HEADER_EMPTY_MESSAGE = "Header cannot be empty";

    public static final String ARTICLE_UUID_CONSTRAINT = "Article UUID should not be null / empty.";

    public static final String DUPLICATE_ENTRY = "Article you are trying to add already exists.";

    public static final String ALREADY_MODIFIED = "Article you are trying to modify had already been modified before."
            + "Please re-fetch the article and re-try";

    public static final String ARTICLE_NOT_FOUND = "Article you specified does not exists.";

    public static final String INVALID_PAYLOAD = "Invalid Payload";

    public static final String STALE_DATA = "Stale Data";
    public static final String UNABLE_TO_FETCH_ARTICLES = "Unable to find articles with provided specification";

}
