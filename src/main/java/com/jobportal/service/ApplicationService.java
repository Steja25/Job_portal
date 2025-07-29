package com.jobportal.service;

import com.jobportal.entity.Application;
import com.jobportal.enums.ApplicationStatus;
import com.jobportal.entity.Job;
import com.jobportal.repository.JobRepository;
import com.jobportal.repository.ApplicationRepository;
import com.jobportal.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobRepository jobRepository;

    @Transactional
    public String applyToJob(Long jobId, User user) {
        Optional<Job> optionalJob = jobRepository.findById(jobId);
        if (optionalJob.isEmpty()) {
            return "Job not found.";
        }

        Job job = optionalJob.get();

        if (applicationRepository.existsByUserAndJob(user, job)) {
            return "You have already applied to this job.";
        }

        Application application = Application.builder()
                .job(job)
                .user(user)
                .status(ApplicationStatus.APPLIED)
                .appliedAt(LocalDateTime.now())
                .build();

        applicationRepository.save(application);

        return "Application submitted successfully.";
    }

    public List<Application> getMyApplications(User user) {
        return applicationRepository.findByUser(user);
    }

    public List<Application> getApplicantsForJob(Long jobId) {
        Optional<Job> job = jobRepository.findById(jobId);
        return job.map(applicationRepository::findByJob).orElse(null);
    }
}
