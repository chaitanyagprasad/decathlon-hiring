package com.hiring.decathlonv1.service;

import com.hiring.decathlonv1.dto.Developer;
import com.hiring.decathlonv1.dto.Team;
import com.hiring.decathlonv1.exceptions.BusinessLogicException;
import com.hiring.decathlonv1.exceptions.PhoneNumberNotValidException;
import com.hiring.decathlonv1.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class InputValidator {

    public void validateTeam(Team team) {
        validateTeamName(team);
        if( team.getDevelopers().isEmpty() ) {
            throw new BusinessLogicException("A team must contain at least one developer.");
        }
        validateDeveloperPhoneNumber(team.getDevelopers());
    }

    private void validateTeamName(Team team) {
        if( team.getTeamName() == null ||
                team.getTeamName().isEmpty() ||
                team.getTeamName().isBlank() ) {
            throw new BusinessLogicException("Team name cannot be null");
        }
    }

    private void validateDeveloperPhoneNumber(Set<Developer> developerSet) {
        Pattern pattern = Pattern.compile("^\\d{10}$");
        List<String> invalidPhoneNumbers = new ArrayList<>();
        developerSet.forEach(
                developer -> {
                    Matcher matcher = pattern.matcher(developer.getPhoneNumber());
                    if( !matcher.matches() ) {
                        invalidPhoneNumbers.add(developer.getPhoneNumber());
                    }
                }
        );

        if( invalidPhoneNumbers.size() > 0 ) {
            throw new PhoneNumberNotValidException(invalidPhoneNumbers);
        }
    }
}
