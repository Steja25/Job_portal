package com.jobportal.repository;

import com.jobportal.entity.Application;
import com.jobportal.entity.Job;
import com.jobportal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Long> {

    List<Application> findByUser(User user); // Get all applications by a job seeker

    List<Application> findByJob(Job job); // Get all applicants for a job

    boolean existsByUserAndJob(User user, Job job); // Prevent duplicate applications
}
