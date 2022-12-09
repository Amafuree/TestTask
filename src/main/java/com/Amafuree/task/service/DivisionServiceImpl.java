package com.Amafuree.task.service;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class DivisionServiceImpl implements DivisionService {

    private final DivisionRepository divisionRepository;

    @Autowired
    public DivisionServiceImpl(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Division> getDivisions() {
        return divisionRepository.findAll();
    }

    @Override
    public List<Division> getEagerLoadedDivisions() {
        return divisionRepository.getEagerLoadedDivisions();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Division> getDivisionsOnDate(LocalDateTime dateTime) {
        return divisionRepository.getDivisionsByDate(dateTime);
    }

    @Override
    @Transactional
    public void addDivision(Division division) {
        divisionRepository.save(division);
    }

    @Override
    @Transactional
    public void addDivisions(Division... divisions) {
        divisionRepository.saveAll(Arrays.asList(divisions));
    }

    @Override
    @Transactional
    public void removeDivisionById(Long id) {
    }

    @Override
    @Transactional
    public void removeDivisionByName(String divisionName) {
    }
}
