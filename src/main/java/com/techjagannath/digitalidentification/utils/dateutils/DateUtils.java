package com.techjagannath.digitalidentification.utils.dateutils;

import jakarta.validation.ValidationException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate parseDate(String date) {
        if (date == null || date.isBlank())
            throw new ValidationException("date cannot be empty");

        try {
            return LocalDate.parse(date.trim(), DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new ValidationException("Date must be in yyyy-MM-dd format");
        }
    }

    public static void validateDateRange(LocalDate fromDate, LocalDate toDate) {
        if (fromDate.isAfter(toDate))
            throw new ValidationException("FROM DATE cannot be greater than TO DATE");
    }

    public static String formatTimeTo12Hour(String time) {
        if (time == null || time.isBlank())
            return null;
        LocalTime localTime = LocalTime.parse(time);
        return localTime.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    public static String dateFormatter(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}