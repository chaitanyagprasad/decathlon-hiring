package com.hiring.decathlonv1.service;

import com.hiring.decathlonv1.dto.AlertSentResponse;
import com.hiring.decathlonv1.dto.SmsRequest;
import com.hiring.decathlonv1.dto.SmsResponse;
import com.hiring.decathlonv1.dto.Team;
import com.hiring.decathlonv1.exceptions.BusinessLogicException;
import com.hiring.decathlonv1.mapper.TeamMapper;
import com.hiring.decathlonv1.repository.DeveloperEntity;
import com.hiring.decathlonv1.repository.TeamEntity;
import com.hiring.decathlonv1.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SimpleTeamService implements TeamService {

    private final TeamRepository teamRepository;

    @Value("${sms.url}")
    private String smsUrl;

    @Value("${sms.host}")
    private String smsHost;

    @Override
    public Long createTeam(Team team) {
        TeamEntity teamEntity = teamRepository.save(
                TeamMapper.toTeamEntity(team)
        );
        return teamEntity.getId();
    }

    @Override
    public AlertSentResponse alert(Long teamId) {
        TeamEntity teamEntity = teamRepository.findById(
                teamId
        ).orElseThrow(() -> new BusinessLogicException("Unable to find team with id : "+teamId));

        DeveloperEntity developer = getRandomObject(teamEntity.getDevelopers());

        SmsRequest request = new SmsRequest();
        request.setPhone_number(developer.getPhoneNumber());
        WebClient webClient = WebClient.builder()
                .baseUrl(smsHost)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
        Mono<SmsResponse> smsResponseMono = webClient.post()
                .uri(smsUrl)
                .body(Mono.just(request), SmsRequest.class)
                .retrieve().bodyToMono(SmsResponse.class);

        try {
        AlertSentResponse alertSentResponse = new AlertSentResponse();
        alertSentResponse.setAlertSent(false);
        SmsResponse response = smsResponseMono.block();
        if( response.getSuccess().equalsIgnoreCase("alert sent") ) {
            alertSentResponse.setAlertSent(true);
            alertSentResponse.setAlertSentTo(TeamMapper.toDeveloper(developer));
        }
        return alertSentResponse;

        }catch (Exception e) {
            throw new BusinessLogicException("Unable to send sms alert.");
        }
    }

    @Override
    public List<Team> getAllTeams() {
        return teamRepository.findAll()
                .stream()
                .map(TeamMapper::toTeam)
                .collect(Collectors.toList());
    }

    @Override
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .map(TeamMapper::toTeam)
                .orElseThrow(() -> new RuntimeException("Team not found"));
    }

    private DeveloperEntity getRandomObject(Set<DeveloperEntity> input) {
        int size = input.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(DeveloperEntity obj : input)
        {
            if (i == item)
                return obj;
            i++;
        }
        return null;
    }
}

