package com.hiring.decathlonv1.service;

import com.hiring.decathlonv1.dto.Developer;
import com.hiring.decathlonv1.dto.SmsRequest;
import com.hiring.decathlonv1.dto.SmsResponse;
import com.hiring.decathlonv1.dto.Team;
import com.hiring.decathlonv1.mapper.TeamMapper;
import com.hiring.decathlonv1.repository.TeamEntity;
import com.hiring.decathlonv1.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SimpleTeamServiceTest {

    private SimpleTeamService underTest;

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriMock;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;
    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        underTest = new SimpleTeamService(teamRepository);
    }

    @Test
    void createTeam() {

        Developer developer = new Developer();
        developer.setName("Chait");
        developer.setPhoneNumber("5236654521");

        Team team = new Team();
        team.setTeamName("bruno");
        team.setDevelopers(Collections.singleton(developer));

        // when
        TeamEntity teamEntity = TeamMapper.toTeamEntity(team);
        teamEntity.setId(5L);
        when(teamRepository.save(any()))
                .thenReturn(
                        teamEntity
                );

        Long actual = underTest.createTeam(team);

        assertThat(actual).isNotNull();

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
        developerSet.add(new Developer("aditi", "85453695452"));
        developerSet.add(new Developer("neha", "9633695452"));
        developerSet.add(new Developer("ayush", "9253695452"));

        team.setDevelopers(developerSet);
        return team;
    }
}