package com.adwicorp.aanandamsn.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorCodes {

    // Internal Server Error Codes
    public static final String INTERNAL_SERVER_ERROR = "ERRIS001";

    // Database Error Codes
    public static final String DB_NO_DATA = "ERRDB001";
    public static final String DB_EXISTING_DATA = "ERRDB002";
    public static final String DB_EXCEED_DATA = "ERRDB003";

    // Parameter Error Codes
    public static final String PARAM_MISSING = "ERRPM001";
    public static final String PARAM_INVALID = "ERRPM002";
    public static final String PARAM_INVALID_WITH_SPACE = "ERRPM003";

    // Time Error Codes
    public static final String TIME_EXCEEDED = "ERRTM001";

    // Business Logic Error Codes
    public static final String BL_REQUEST_OLD = "ERRBL001";
    public static final String BL_SELF_RECOMMEND = "ERRBL002";
    public static final String BL_ALREADY_WITHDRAWAL_REQUESTED = "ERRBL003";
    public static final String BL_MIN_REFERRAL_COUNT = "ERRBL004";

    // Map for Error Codes and Messages
    public static final Map<String, String> ERROR_CODE_MESSAGE_MAP = new HashMap<>();

    static {
        ERROR_CODE_MESSAGE_MAP.put(INTERNAL_SERVER_ERROR, "Internal server error occurred");
        ERROR_CODE_MESSAGE_MAP.put(DB_NO_DATA, "No data found in db");
        ERROR_CODE_MESSAGE_MAP.put(DB_EXISTING_DATA, "Data already exists with {parameter} in db");
        ERROR_CODE_MESSAGE_MAP.put(DB_EXCEED_DATA, "More number of characters provided in {parameter} to store in db");
        ERROR_CODE_MESSAGE_MAP.put(PARAM_MISSING, "Missing mandatory parameter {parameter} not provided in the request");
        ERROR_CODE_MESSAGE_MAP.put(PARAM_INVALID, "Invalid mandatory parameter {parameter} provided in the request");
        ERROR_CODE_MESSAGE_MAP.put(PARAM_INVALID_WITH_SPACE, "Space is not allowed in parameter {parameter} provided in the request");
        ERROR_CODE_MESSAGE_MAP.put(TIME_EXCEEDED, "Time required for updating event exceeded");
        ERROR_CODE_MESSAGE_MAP.put(BL_REQUEST_OLD, "Request over the allowed duration for updating this record");
        ERROR_CODE_MESSAGE_MAP.put(BL_SELF_RECOMMEND, "Cannot use the self referral code");
        ERROR_CODE_MESSAGE_MAP.put(BL_ALREADY_WITHDRAWAL_REQUESTED, "Withdrawal already requested for the user");
        ERROR_CODE_MESSAGE_MAP.put(BL_MIN_REFERRAL_COUNT, "Minimum referral count not met for withdrawal");
    }

    private ErrorCodes() {

    }
}


