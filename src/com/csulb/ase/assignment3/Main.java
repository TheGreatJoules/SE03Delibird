package com.csulb.ase.assignment3;

import com.csulb.ase.assignment3.controller.OwnerController;
import com.csulb.ase.assignment3.models.Owner;
import com.csulb.ase.assignment3.models.PersonEnum;
import com.csulb.ase.assignment3.utils.ProjectUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Owner owner = Owner.builder()
                .id(ProjectUtils.generatePersonId(PersonEnum.OWNER))
                .username("admin")
                .password("password")
                .email("steve_jobs@csulb.edu")
                .phone_number("(562)985-4111")
                .first_name("steve")
                .last_name("jobs")
                .address("1250 Bellflower Blvd, Long Beach, CA 90840")
                .start(new Date(Long.parseLong("1647721600")))
                .build();

        OwnerController ownerController = new OwnerController(owner);
        log.info("End.");
    }
}
