package ru.haval.muTrainings.accessingdatajpa;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
@NoArgsConstructor
public class Position {
    private String pos;
    private List<String> positions = new ArrayList<>();

    {
        positions.add("worker");
        positions.add("technician");
    }
}
