package com.techjagannath.digitalidentification.controller;

import com.techjagannath.digitalidentification.schedulers.AttendanceRecordsScheduler;
import com.techjagannath.digitalidentification.utils.apiresponse.ApiResponse;
import com.techjagannath.digitalidentification.utils.apiresponse.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/checks")
public class TestingHealthAndSchedulerController {

    @Autowired
    AttendanceRecordsScheduler attendanceRecordsScheduler;

    @GetMapping("/health-check")
    public ResponseEntity<ApiResponse<String>> healthCheck() {
        return ResponseBuilder.success("ok", "Success");
    }

    @PostMapping("/attendance-records")
    public ResponseEntity<ApiResponse<String>> checkAttendanceScheduler(@RequestParam("attendance_date")LocalDate attendanceDate) {
        attendanceRecordsScheduler.updateUsingController(attendanceDate);
        return ResponseBuilder.success("ok", "Success");
    }

}
