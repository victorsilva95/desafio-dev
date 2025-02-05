package com.bycoders.challenge.commons;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static com.bycoders.challenge.commons.Constants.TIME_FORMAT_PATTERN;

public class LocalTimeUtils {

    public static LocalTime getByString(String value) {
        var timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT_PATTERN);
        return LocalTime.parse(value, timeFormatter);
    }
}
