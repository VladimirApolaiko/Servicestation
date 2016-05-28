package org.servicestation.resources.utils;


import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final Pattern TIME_RANGE_PATTERN = Pattern.compile("\\[(\\d+),(\\d+)\\)");

    public static String generateRandomString(final int minTokenSize) {
        return RandomStringUtils.randomAlphanumeric(minTokenSize + (int) (Math.random() * 32));
    }

    public static String transformWorkingTimeToDatabaseRange(String workingTime) {
        String[] workingHours = workingTime.split("-");

        if(workingHours.length == 2) {
            return "[" + workingHours[0] + "," + workingHours[1] + "]";
        }

        return null;
    }

    public static String transformWorkingTimeToDtoRange(String workingTime) {
        Matcher matcher = TIME_RANGE_PATTERN.matcher(workingTime);
        if(matcher.find()){
            return matcher.group(1) + "-" + (Integer.parseInt(matcher.group(2)) - 1);
        }
        return null;
    }

}
