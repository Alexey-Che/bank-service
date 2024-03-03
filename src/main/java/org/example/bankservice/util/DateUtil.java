package org.example.bankservice.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.text.SimpleDateFormat;
import java.util.Date;

@UtilityClass
public class DateUtil {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @SneakyThrows
    public Date stringToDate(String date) {
        return formatter.parse(date);
    }
}
