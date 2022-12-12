package com.Amafuree.task.service;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.repository.DivisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.logging.Logger;

@Service
public class DivisionServiceImpl implements DivisionService {

    private static final Logger LOGGER = Logger.getLogger("DivisionServiceImpl");
    private final DivisionRepository divisionRepository;

    private final BiPredicate<List<Division>, Division> acceptable =
            (divisions, division) -> {
                boolean condition1 = divisions.isEmpty() && division.getIsSystem()
                        && division.getParentDivision() == null;
                boolean condition2 = !divisions.isEmpty() && !division.getIsSystem()
                        && division.getParentDivision() != null;
                return condition1 || condition2;
            };

    @Autowired
    public DivisionServiceImpl(DivisionRepository divisionRepository) {
        this.divisionRepository = divisionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    @Lock(LockModeType.PESSIMISTIC_READ)
    public Optional<Division> getDivisionById(Long id) {
        return divisionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    @Lock(LockModeType.PESSIMISTIC_READ)
    public Optional<Division> getDivisionByName(String name) {
        return divisionRepository.getDivisionByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    @Lock(LockModeType.PESSIMISTIC_READ)
    public List<Division> getEagerLoadedDivisions() {
        return divisionRepository.getEagerLoadedDivisions();
    }

    @Override
    @Transactional(readOnly = true)
    @Lock(LockModeType.PESSIMISTIC_READ)
    public List<Division> getDivisionsOnDate(LocalDateTime dateTime) {
        return divisionRepository.getDivisionsByDate(dateTime);
    }

    @Override
    @Transactional
    public void addDivision(Division division) {
        List<Division> divisions = divisionRepository.findAll();

        if (acceptable.test(divisions, division)) {
            divisionRepository.save(division);
        } else {
            LOGGER.info("Division is not acceptable for saving!");
        }
    }

    @Override
    @Transactional
    public void addDivisions(Division... divisions) {
        List<Division> divisionList = divisionRepository.findAll();
        boolean check = true;

        for (Division div : divisions) {
            if (!acceptable.test(divisionList, div)) {
                check = false;
            }
        }

        if (check) {
            divisionRepository.saveAll(Arrays.asList(divisions));
        } else {
            LOGGER.info("Cannot save list of divisions." +
                    " Some divisions are unacceptable for saving!");
        }
    }

    @Override
    @Transactional
    public void removeDivisionById(Long id) {
        Optional<Division> optDivision = divisionRepository.findById(id);
        if (optDivision.isPresent() && !optDivision.get().getIsSystem()
                && optDivision.get().getChildDivisions().isEmpty()) {
            divisionRepository.deleteById(id);
        } else {
            LOGGER.info("The division cannot be deleted!");
        }
    }

    @Override
    @Transactional
    public void correctDivision(Division division) {
        Optional<Division> optDivision = divisionRepository.findById(division.getId());
        if (optDivision.isPresent()) {
            division.setId(null);
            Division sourceDivision = optDivision.get();
            sourceDivision.setDateTill(LocalDateTime.now());
            sourceDivision.setEdited(LocalDateTime.now());
            division.setEdited(LocalDateTime.now());
            division.setCreated(LocalDateTime.now());

            if (division.getDateFrom().isBefore(LocalDateTime.now())) {
                division.setDateFrom(LocalDateTime.now());
                LOGGER.info("Wrong dt_from value! Reassigning...");
            }

            if (division.getParentDivision() == null
                    && sourceDivision.getParentDivision() != null) {
                division.setParentDivision(sourceDivision.getParentDivision());
            }

            divisionRepository.saveAll(List.of(sourceDivision, division));
        }
    }
}
