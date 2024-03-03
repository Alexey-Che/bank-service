package org.example.bankservice.service;

import org.example.bankservice.domain.QueryType;
import org.springframework.stereotype.Service;

@Service
public class QueryService {

    private static final String EMAIL_REGEX = "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,5}\\b";
    private static final String PHONE_NUMBER_REGEX = "\\+\\d{11}";
    private static final String FULL_NAME_REGEX = "[А-Яа-я\\s]+";
    private static final String DATE_REGEX = "^\\d{4}-\\d{2}-\\d{2}$";

    public QueryType detectQueryType(String query) {
        if (isDateFormat(query)) {
            return QueryType.DATE;
        } else if (isEmailFormat(query)) {
            return QueryType.EMAIL;
        } else if (isPhoneNumberFormat(query)) {
            return QueryType.PHONE_NUMBER;
        } else if (isFullNameFormat(query)) {
            return QueryType.FULL_NAME;
        } else {
            return QueryType.UNKNOWN;
        }
    }

    private boolean isDateFormat(String query) {
        return query.matches(DATE_REGEX);
    }

    private boolean isPhoneNumberFormat(String query) {
        return query.matches(PHONE_NUMBER_REGEX);
    }

    private boolean isEmailFormat(String query) {
        return query.matches(EMAIL_REGEX);
    }

    private boolean isFullNameFormat(String query) {
        return query.matches(FULL_NAME_REGEX);
    }
}
