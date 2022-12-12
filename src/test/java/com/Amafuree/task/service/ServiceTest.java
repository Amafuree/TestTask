package com.Amafuree.task.service;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.model.dto.TreeifiedDivisionDtos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class ServiceTest {

    private final DivisionService divisionService;

    @Autowired
    public ServiceTest(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @Test
    public void checkGetDivisionsByDate() {
        List<Division> divisions2021 = divisionService
                .getDivisionsOnDate(LocalDateTime.parse("2021-10-10T00:00:00"));
        List<Division> divisions2024 = divisionService
                .getDivisionsOnDate(LocalDateTime.parse("2024-10-10T00:00:00"));
        List<Division> divisions2030 = divisionService
                .getDivisionsOnDate(LocalDateTime.parse("2030-10-10T00:00:00"));
        List<Division> divisions2041 = divisionService
                .getDivisionsOnDate(LocalDateTime.parse("2041-10-10T00:00:00"));
        List<Division> divisions2048 = divisionService
                .getDivisionsOnDate(LocalDateTime.parse("2048-10-10T00:00:00"));

        assertEquals(5, divisions2021.size());
        assertEquals(6, divisions2024.size());
        assertEquals(4, divisions2030.size());
        assertEquals(4, divisions2041.size());
        assertEquals(4, divisions2048.size());

        checkProperTreeifying(divisions2021, divisions2024);
    }

    public void checkProperTreeifying(List<Division> list1, List<Division> list2) {
        TreeifiedDivisionDtos tree1 = new TreeifiedDivisionDtos(list1);
        TreeifiedDivisionDtos tree2 = new TreeifiedDivisionDtos(list2);

        assertEquals(list1.size(), tree1.size());
        assertEquals(list2.size(), tree2.size());
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void checkSaveEntity() {
        Division systemDivision = new Division("System",
                LocalDateTime.parse("2222-10-10T00:00:00"),
                LocalDateTime.parse("2300-10-10T00:00:00"));
        systemDivision.setIsSystem(true);

        Division orphanDivision = new Division("Orphan",
                LocalDateTime.parse("2222-10-10T00:00:00"),
                LocalDateTime.parse("2300-10-10T00:00:00"));

        Division correctDivision = new Division("Correct",
                LocalDateTime.parse("2222-10-10T00:00:00"),
                LocalDateTime.parse("2300-10-10T00:00:00"));
        correctDivision.setParentDivision(divisionService.getDivisionById(1L).get());

        divisionService.addDivisions(systemDivision, orphanDivision, correctDivision);

        if (divisionService.getDivisionByName("Correct").isPresent()) {
            fail();
        }

        divisionService.addDivision(systemDivision);
        divisionService.addDivision(orphanDivision);
        divisionService.addDivision(correctDivision);

        if (divisionService.getDivisionByName("System").isPresent()) {
            fail();
        }

        if (divisionService.getDivisionByName("Orphan").isPresent()) {
            fail();
        }

        if (divisionService.getDivisionByName("Correct").isEmpty()) {
            fail();
        }
    }

    @Test
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public void checkEditEntity() {
        Division division = divisionService.getDivisionByName("subHead_1").get();
        division.setName("edited_subHead");
        divisionService.correctDivision(division);

        Division edited = divisionService.getDivisionByName("subHead_1").get();
        Division inserted = divisionService.getDivisionByName("edited_subHead").get();

        boolean dateCondition = edited.getDateTill().isBefore(inserted.getCreated())
                || edited.getDateTill().equals(inserted.getCreated());
        boolean parentCondition = (edited.getParentDivision() == null
                && inserted.getParentDivision() == null) ||
                edited.getParentDivision().getId().equals(inserted.getParentDivision().getId());

        assert dateCondition && parentCondition;
    }

    @Test
    public void checkDeleteEntity() {
        divisionService.removeDivisionById(1L);
        divisionService.removeDivisionById(5L);
        divisionService.removeDivisionById(3L);

        Optional<Division> optDivision1 = divisionService.getDivisionById(1L);
        Optional<Division> optDivision2 = divisionService.getDivisionById(5L);
        Optional<Division> optDivision3 = divisionService.getDivisionById(3L);

        if (optDivision1.isEmpty() || optDivision3.isEmpty()) {
            fail();
        }

        if (optDivision2.isPresent()) {
            fail();
        }
    }
}
