package com.jobportal.entity;

import com.jobportal.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String location;

    private String companyName;

    private LocalDateTime postedAt;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private User postedBy;
}
