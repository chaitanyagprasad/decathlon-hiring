package com.hiring.decathlonv1.mapper;

import com.hiring.decathlonv1.dto.Developer;
import com.hiring.decathlonv1.dto.Team;
import com.hiring.decathlonv1.repository.DeveloperEntity;
import com.hiring.decathlonv1.repository.TeamEntity;

import java.util.stream.Collectors;

public class TeamMapper {

    public static TeamEntity toTeamEntity(Team team) {
       TeamEntity teamEntity = new TeamEntity();
       teamEntity.setTeamName(team.getTeamName());
       teamEntity.setDevelopers(
               team.getDevelopers()
                       .stream()
                       .map(TeamMapper::toDeveloperEntity)
                       .collect(Collectors.toSet())
       );

        return teamEntity;
    }

    public static Team toTeam(TeamEntity input) {
        Team team = new Team();
        team.setTeamName(input.getTeamName());
        team.setDevelopers(
                input.getDevelopers().stream().map(TeamMapper::toDeveloper).collect(Collectors.toSet())
        );
        return team;
    }

    public static DeveloperEntity toDeveloperEntity(Developer developer){
        DeveloperEntity developerEntity = new DeveloperEntity();
        developerEntity.setPhoneNumber(developer.getPhoneNumber());
        developerEntity.setName(developer.getName());
        return developerEntity;
    }

    public static Developer toDeveloper(DeveloperEntity input){
        Developer developer = new Developer();
        developer.setName(input.getName());
        developer.setPhoneNumber(input.getPhoneNumber());
        return developer;
    }

}
