package ru.haval.muTrainings.accessingdatajpa;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trainings_positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "positionId")
    private Long id;

    @Column(name = "position", unique = true)
    private String text;

    private Boolean del = false;

    public Position(String text) {
        this.text = text;
    }
}
