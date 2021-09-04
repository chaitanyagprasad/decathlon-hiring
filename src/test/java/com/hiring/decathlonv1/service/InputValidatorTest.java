package com.hiring.decathlonv1.service;

import com.hiring.decathlonv1.dto.Developer;
import com.hiring.decathlonv1.dto.Team;
import com.hiring.decathlonv1.exceptions.BusinessLogicException;
import com.hiring.decathlonv1.exceptions.PhoneNumberNotValidException;
import com.hiring.decathlonv1.repository.DeveloperEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {

    private InputValidator underTest;

    @BeforeEach
    void setUp() {
        underTest = new InputValidator();
    }

    @Test
    void validateTeamPositive() {
        Developer developer = new Developer();
        developer.setName("Chait");
        developer.setPhoneNumber("5236654521");

        Team team = new Team();
        team.setTeamName("bruno");
        team.setDevelopers(Collections.singleton(developer));

        assertThatCode(() -> underTest.validateTeam(team)).doesNotThrowAnyException();
    }

    @Test
    void validateTeamInvalidPhone() {
        Developer developer = new Developer();
        developer.setName("Chait");
        developer.setPhoneNumber("52366545215214");

        Team team = new Team();
        team.setTeamName("bruno");
        team.setDevelopers(Collections.singleton(developer));

        assertThatThrownBy(() -> underTest.validateTeam(team))
                .isInstanceOf(PhoneNumberNotValidException.class);
    }

    @Test
    void validateTeamInvalidTeamName() {
        Developer developer = new Developer();
        developer.setName("Chait");
        developer.setPhoneNumber("9523665452");

        Team team = new Team();
        team.setTeamName("");
        team.setDevelopers(Collections.singleton(developer));

        assertThatThrownBy(() -> underTest.validateTeam(team))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("Team name cannot be null");
    }

    @Test
    void validateTeamEmptyDeveloper() {
        Team team = new Team();
        team.setTeamName("bruno");
        team.setDevelopers(new HashSet<>());

        assertThatThrownBy(() -> underTest.validateTeam(team))
                .isInstanceOf(BusinessLogicException.class)
                .hasMessageContaining("A team must contain at least one developer.");
    }
}