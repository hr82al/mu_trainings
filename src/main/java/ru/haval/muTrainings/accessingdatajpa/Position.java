package ru.haval.muTrainings.accessingdatajpa;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Data
@ToString
@Slf4j
@RequiredArgsConstructor
@Entity
@Table(name="trainings_positions")
public class Position {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "position_id")
    private Long id;
    @Column(name = "position", unique = true)
    private String text;
}
