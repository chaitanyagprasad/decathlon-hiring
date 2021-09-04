package com.hiring.decathlonv1.service;

import com.hiring.decathlonv1.dto.AlertSentResponse;
import com.hiring.decathlonv1.dto.Team;

import java.util.List;

public interface TeamService {

    Long createTeam(Team team);

    AlertSentResponse alert(Long teamId);

    List<Team> getAllTeams();

    Team getTeamById(Long id);

}

