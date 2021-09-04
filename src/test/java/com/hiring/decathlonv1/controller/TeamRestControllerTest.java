package com.hiring.decathlonv1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hiring.decathlonv1.dto.AlertSentResponse;
import com.hiring.decathlonv1.dto.Developer;
import com.hiring.decathlonv1.dto.Team;
import com.hiring.decathlonv1.exceptions.BusinessLogicException;
import com.hiring.decathlonv1.mapper.TeamMapper;
import com.hiring.decathlonv1.repository.TeamEntity;
import com.hiring.decathlonv1.repository.TeamRepository;
import com.hiring.decathlonv1.service.TeamService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
@ExtendWith(MockitoExtension.class)
class TeamRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Mock
    private TeamRepository teamRepository;

    @Test
    void createTeam() throws Exception {
        Team team = getTestData();

        mockMvc.perform(
                post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(team))

        ).andExpect(status().isOk());
    }

    @Test
    void createTeamNegative() throws Exception {

        Team team = getTeamDataNegative();

        mockMvc.perform(
                post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(team))

        ).andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException()).isInstanceOf(BusinessLogicException.class))
                .andExpect(result -> assertThat(result.getResolvedException().getMessage())
                        .contains("Team name cannot be null"));

    }

    @Test
    void alertTeamMember() throws Exception {

        AlertSentResponse alertSentResponse = new AlertSentResponse();
        alertSentResponse.setAlertSent(true);
        alertSentResponse.setAlertSentTo(new Developer("chait", "1236547856"));

        when(teamService.alert(anyLong()))
                .thenReturn(alertSentResponse);

        mockMvc.perform(
                post("/1/alert")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());

    }

    @Test
    void alertTeamMemberNegative() throws Exception {

        AlertSentResponse alertSentResponse = new AlertSentResponse();
        alertSentResponse.setAlertSent(true);
        alertSentResponse.setAlertSentTo(new Developer("chait", "1236547856"));



        mockMvc.perform(
                post("/1/alert")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isBadRequest());

    }

    private Team getTestData() {
        Team team = new Team();
        team.setTeamName("kaigho");

        Set<Developer> developerSet = new HashSet<>();
        developerSet.add(new Developer("chait", "1253695452"));
        developerSet.add(new Developer("shba", "1253697452"));
        developerSet.add(new Developer("akki", "1453695452"));
        developerSet.add(new Developer("bharti", "2553695452"));
        developerSet.add(new Developer("geepee", "1253695236"));
        developerSet.add(new Developer("padma", "1856695452"));
        developerSet.add(new Developer("aditi", "8545369545"));
        developerSet.add(new Developer("neha", "9633695452"));
        developerSet.add(new Developer("ayush", "9253695452"));

        team.setDevelopers(developerSet);
        return team;
    }

    private Team getTeamDataNegative() {
        Team team = new Team();

        Set<Developer> developerSet = new HashSet<>();
        developerSet.add(new Developer("chait", "1253695452"));
        developerSet.add(new Developer("shba", "1253697452"));
        developerSet.add(new Developer("akki", "1453695452"));
        developerSet.add(new Developer("bharti", "2553695452"));
        developerSet.add(new Developer("geepee", "1253695236"));
        developerSet.add(new Developer("padma", "1856695452"));
        developerSet.add(new Developer("aditi", "8545369545"));
        developerSet.add(new Developer("neha", "9633695452"));
        developerSet.add(new Developer("ayush", "9253695452"));

        team.setDevelopers(developerSet);
        return team;
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}