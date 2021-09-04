package com.hiring.decathlonv1.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class Team {
    private String teamName;
    private Set<Developer> developers;
}
