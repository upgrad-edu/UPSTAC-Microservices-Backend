package org.upgrad.upstac.shared;

import org.upgrad.upstac.exception.AppException;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

public class DateParser {


    public static LocalDate getDateFromString(String input) {

        try {
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return asLocalDate(simpleDateFormat.parse(input));
        } catch (Exception e) {
            e.printStackTrace();
            throw new AppException("Invalid Date String" + input);

        }


    }

    public static String getStringFromDate(LocalDate input) {

        //"2018-09-09"
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(asDate(input));


    }

    public static LocalDate asLocalDate(Date input) {
        return Optional.ofNullable(input)
                .map(date -> Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate())
                .orElseThrow(() -> new AppException("Invalid Input"));

    }

    public static Date asDate(LocalDate input) {

        return Optional.ofNullable(input)
                .map(date -> Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
                .orElseThrow(() -> new AppException("Invalid Input"));

    }


}
