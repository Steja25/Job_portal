package com.jobportal.controller;

import com.jobportal.entity.Job;
import com.jobportal.service.JobService;
import com.jobportal.entity.User;
import com.jobportal.enums.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {

    @Autowired
    private JobService jobService;

    @PostMapping("/main")
    public ResponseEntity<?> postJob(@RequestBody Job job,
                                     @AuthenticationPrincipal User currentUser) {
    	
    	
    	
    	  if (currentUser.getRole() != UserRole.RECRUITER && currentUser.getRole() != UserRole.ADMIN) {
              return ResponseEntity.status(403).body("Only recruiters or admins can post jobs.");
          }
    	  System.out.println("Creating job by: " + currentUser.getEmail());

        Job postedJob = jobService.postJob(job, currentUser);
        return ResponseEntity.ok(postedJob);
      

    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getJobById(@PathVariable Long id) {
        return jobService.getJobById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable Long id,
                                       @AuthenticationPrincipal User currentUser) {
        return jobService.getJobById(id).map(job -> {
            if (currentUser.getRole() == UserRole.ADMIN ||
                job.getPostedBy().getId().equals(currentUser.getId())) {
                jobService.deleteJob(id);
                return ResponseEntity.ok("Job deleted.");
            } else {
                return ResponseEntity.status(403).body("You are not authorized to delete this job.");
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
