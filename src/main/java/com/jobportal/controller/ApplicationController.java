package com.jobportal.controller;

import com.jobportal.entity.Application;
import com.jobportal.service.ApplicationService;
import com.jobportal.entity.User;
import com.jobportal.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apply")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // ✅ POST /apply/{jobId} - Job Seeker applies
    @PostMapping("/{jobId}")
    public ResponseEntity<?> applyToJob(@PathVariable Long jobId,
                                        @AuthenticationPrincipal User user) {
        if (user.getRole() != UserRole.JOBSEEKER) {
            return ResponseEntity.status(403).body("Only job seekers can apply to jobs.");
        }
        String message = applicationService.applyToJob(jobId, user);
        return ResponseEntity.ok(message);
    }

    // ✅ GET /apply/my-applications - Job Seeker views their apps
    @GetMapping("/my-applications")
    public ResponseEntity<?> myApplications(@AuthenticationPrincipal User user) {
        if (user.getRole() != UserRole.JOBSEEKER) {
            return ResponseEntity.status(403).body("Only job seekers can view applications.");
        }
        List<Application> apps = applicationService.getMyApplications(user);
        return ResponseEntity.ok(apps);
    }

    // ✅ GET /apply/job/{jobId} - Recruiter views applicants
    @GetMapping("/job/{jobId}")
    public ResponseEntity<?> viewApplicants(@PathVariable Long jobId,
                                            @AuthenticationPrincipal User user) {
        if (user.getRole() != UserRole.RECRUITER && user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(403).body("Only recruiters or admins can view applicants.");
        }

        List<Application> applicants = applicationService.getApplicantsForJob(jobId);
        return applicants != null
                ? ResponseEntity.ok(applicants)
                : ResponseEntity.status(404).body("Job not found");
    }
}
