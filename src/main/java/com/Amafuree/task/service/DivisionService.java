package com.Amafuree.task.service;

import com.Amafuree.task.model.Division;

import java.time.LocalDateTime;
import java.util.List;

public interface DivisionService {

    List<Division> getDivisions();

    List<Division> getEagerLoadedDivisions();

    List<Division> getDivisionsOnDate(LocalDateTime dateTime);

    void addDivision(Division division);

    void addDivisions(Division... divisions);

    void removeDivisionById(Long id);

    void removeDivisionByName(String divisionName);
}
