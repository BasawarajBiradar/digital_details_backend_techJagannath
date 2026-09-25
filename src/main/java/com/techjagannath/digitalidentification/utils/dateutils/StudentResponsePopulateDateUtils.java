package com.techjagannath.digitalidentification.utils.dateutils;

import com.techjagannath.digitalidentification.models.student.attendancepage.calendarview.GetStudentAttendancePageCalendarViewResultModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentResponsePopulateDateUtils {

    private StudentResponsePopulateDateUtils(){}

    public static List<GetStudentAttendancePageCalendarViewResultModel> populateDatesInCalendarView(
            LocalDate fromDate, LocalDate toDate, List<GetStudentAttendancePageCalendarViewResultModel> result) {
        Map<LocalDate, GetStudentAttendancePageCalendarViewResultModel> existingDates =
                result.stream().collect(
                        Collectors.toMap(obj -> LocalDate.parse(obj.getDate()),obj -> obj));

        List<GetStudentAttendancePageCalendarViewResultModel> populatedResult = new ArrayList<>();
        LocalDate currentDate = fromDate;
        while (!currentDate.isAfter(toDate)) {
            GetStudentAttendancePageCalendarViewResultModel existing = existingDates.get(currentDate);
            if (existing != null) {
                populatedResult.add(existing);
            } else {
                populatedResult.add(new GetStudentAttendancePageCalendarViewResultModel(currentDate.toString(), null));
            }
            currentDate = currentDate.plusDays(1);
        }
        return populatedResult;
    }

}
