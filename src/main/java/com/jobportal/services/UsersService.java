package com.jobportal.services;

import com.jobportal.entity.JobSeekerProfile;
import com.jobportal.entity.RecruiterProfile;
import com.jobportal.entity.Users;
import com.jobportal.repository.JobSeekerProfileRepository;
import com.jobportal.repository.RecruiterProfileRepository;
import com.jobportal.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private  final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private  final PasswordEncoder passwordEncoder;


    @Autowired
    public UsersService(UsersRepository usersRepository, JobSeekerProfileRepository jobSeekerProfileRepository,
                        RecruiterProfileRepository recruiterProfileRepository,PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users){
        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUsers = usersRepository.save(users);
        int userTypeId = users.getUserTypeId().getUserTypeId();
        if(userTypeId ==1){
            recruiterProfileRepository.save(new RecruiterProfile(savedUsers));
        }
        else{
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUsers));
        }
        return savedUsers;

    }
}
