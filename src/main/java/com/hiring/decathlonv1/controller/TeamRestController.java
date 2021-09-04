package com.hiring.decathlonv1.controller;

import com.hiring.decathlonv1.dto.AlertSentResponse;
import com.hiring.decathlonv1.dto.Team;
import com.hiring.decathlonv1.service.InputValidator;
import com.hiring.decathlonv1.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TeamRestController {

    private final TeamService teamService;
    private final InputValidator inputValidator;

    @PostMapping("team")
    public ResponseEntity<Long> createTeam(@RequestBody final Team team) {
        inputValidator.validateTeam(team);
        return ResponseEntity.ok(teamService.createTeam(team));
    }

    @PostMapping("{teamId}/alert")
    public ResponseEntity<AlertSentResponse> alertTeamMember(
            @PathVariable("teamId") Long teamId
    ) {
        return ResponseEntity.ok(teamService.alert(teamId));
    }

    @GetMapping("team")
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("team/{id}")
    public ResponseEntity<Team> getTeamById(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }


}


