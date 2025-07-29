# Job Portal - Spring Boot Project

A simple Job Portal application built using Spring Boot with role-based access using JWT authentication.

## Features:
- User Registration & Login
- JWT Token-based Authentication
- Role-based access: RECRUITER, JOB_SEEKER, ADMIN
- Recruiters can post/delete jobs
- Job Seekers can view/apply for jobs
- Admin has full access
- Public job listing available for all

## Technologies Used:
- Java 17
- Spring Boot
- Spring Security (JWT)
- JPA with Hibernate
- MySQL (can use H2 for testing)
- Lombok

## API Endpoints:

| Method | Endpoint       | Access             | Description          |
|--------|----------------|--------------------|----------------------|
| POST   | /auth/register | Public             | Register a new user  |
| POST   | /auth/login    | Public             | Login and get token  |
| GET    | /jobs          | Public             | View all jobs        |
| POST   | /jobs          | RECRUITER/ADMIN    | Post a job           |
| DELETE | /jobs/{id}     | RECRUITER/ADMIN    | Delete a job         |
| POST   | /apply/{id}    | JOB_SEEKER/ADMIN   | Apply for a job      |

## Sample Users:

| Email              | Password | Role        |
|-------------------|----------|-------------|
| teja@example.com  | 12345    | RECRUITER   |
| seeker@example.com| 12345    | JOB_SEEKER  |
| admin@example.com | 12345    | ADMIN       |

## How to Run:
1. Clone the repo
2. Configure DB in `application.properties`
3. Run the app using your IDE or command:
