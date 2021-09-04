package com.hiring.decathlonv1.repository;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "teams")
@Getter@Setter
@NoArgsConstructor
public class TeamEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;


    @OneToMany(
            cascade = CascadeType.ALL
    )
    private Set<DeveloperEntity> developers;

}
