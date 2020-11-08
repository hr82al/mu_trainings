package ru.haval.muTrainings.accessingdatajpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@ToString
@Slf4j
@NoArgsConstructor
@Entity
@Table(name="trainings_positions")
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "positionId"/*, updatable = false, nullable = false*/)
    private Long id;
    @Column(name = "position", unique = true)
    private String text;

    public Position(String text) {
        this.text = text;
    }
}
