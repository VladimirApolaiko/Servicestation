package org.servicestation.resources.utils;


import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern TIME_RANGE_PATTERN = Pattern.compile("\\[(\\d+),(\\d+)\\)");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");

    public static final DateTimeFormatter HOURS_MINUTES = DateTimeFormatter.ofPattern("hh:mm");


    public static String generateRandomString(final int minTokenSize) {
        return RandomStringUtils.randomAlphanumeric(minTokenSize + (int) (Math.random() * 32));
    }

    public static String transformWorkingTimeToDatabaseRange(String workingTime) {
        String[] workingHours = workingTime.split("-");

        if (workingHours.length == 2) {
            return "[" + workingHours[0] + "," + workingHours[1] + "]";
        }

        return null;
    }

    public static String transformWorkingTimeToDtoRange(String workingTime) {
        Matcher matcher = TIME_RANGE_PATTERN.matcher(workingTime);
        if (matcher.find()) {
            return matcher.group(1) + "-" + (Integer.parseInt(matcher.group(2)) - 1);
        }
        return null;
    }

    public static LocalDate getLocalDate(String localDateTime) {
        return LocalDate.parse(localDateTime, DATE_FORMATTER);
    }

    public static LocalDateTime getLocalDateTime(String localDateTime) {
        return LocalDateTime.parse(localDateTime, DATE_TIME_FORMATTER);
    }

    public static String getStringLocalDateTimeFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DATE_TIME_FORMATTER);
    }

    public static String getStringLocalDateFormat(LocalDate localDate) {
        return localDate.format(DATE_FORMATTER);
    }

    public static String getTime(LocalDateTime localDateTime) {
        return localDateTime.format(HOURS_MINUTES);
    }


}
