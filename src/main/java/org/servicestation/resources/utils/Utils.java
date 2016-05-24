package org.servicestation.resources.utils;


import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Utils {

    public static String generateRandomString(final int minTokenSize) {
        return RandomStringUtils.randomAlphanumeric(minTokenSize + (int) (Math.random() * 32));
    }

}
