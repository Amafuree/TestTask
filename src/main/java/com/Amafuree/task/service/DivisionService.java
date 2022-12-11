package com.Amafuree.task.service;

import com.Amafuree.task.model.Division;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface DivisionService {

    Optional<Division> getDivisionById(Long id);

    Optional<Division> getDivisionByName(String name);
    List<Division> getDivisions();

    List<Division> getEagerLoadedDivisions();

    List<Division> getDivisionsOnDate(LocalDateTime dateTime);

    void addDivision(Division division);

    void addDivisions(Division... divisions);

    void removeDivisionById(Long id);

    void correctDivision(Division division);
}
