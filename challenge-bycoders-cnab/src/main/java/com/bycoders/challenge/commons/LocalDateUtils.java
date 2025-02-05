package com.bycoders.challenge.commons;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.bycoders.challenge.commons.Constants.DATE_FORMAT_PATTERN;

public class LocalDateUtils {

    public static LocalDate getByString(String value) {
        var dateFormat = DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN);
        return LocalDate.parse(value, dateFormat);
    }
}
