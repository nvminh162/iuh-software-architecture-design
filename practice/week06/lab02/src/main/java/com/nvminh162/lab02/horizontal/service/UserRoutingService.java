package com.nvminh162.lab02.horizontal.service;

import org.springframework.stereotype.Service;
import com.nvminh162.lab02.horizontal.entity.UserMale;
import com.nvminh162.lab02.horizontal.entity.UserFemale;
import com.nvminh162.lab02.horizontal.repository.UserMaleRepository;
import com.nvminh162.lab02.horizontal.repository.UserFemaleRepository;

@Service
public class UserRoutingService {
    private final UserMaleRepository maleRepo;
    private final UserFemaleRepository femaleRepo;

    public UserRoutingService(UserMaleRepository maleRepo, UserFemaleRepository femaleRepo) {
        this.maleRepo = maleRepo;
        this.femaleRepo = femaleRepo;
    }

    public void saveUser(String name, String gender) {
        if ("NAM".equalsIgnoreCase(gender)) {
            UserMale male = new UserMale();
            male.setName(name);
            male.setGender(gender);
            maleRepo.save(male);
        } else if ("NU".equalsIgnoreCase(gender)) {
            UserFemale female = new UserFemale();
            female.setName(name);
            female.setGender(gender);
            femaleRepo.save(female);
        } else {
            throw new IllegalArgumentException("Giới tính không hợp lệ: " + gender);
        }
    }
}
