package com.Amafuree.task.init;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.service.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class OnStartupDataInit {

    private final DivisionService divisionService;

    @Autowired
    public OnStartupDataInit(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @PostConstruct
    public void onStartupInit() {
        Division rootDiv = new Division("head", LocalDateTime.parse("2020-12-20T00:00:00"),
                LocalDateTime.parse("2040-12-20T00:00:00"));
        rootDiv.setIsSystem(true);

        Division subRootDiv1 = new Division("subHead1", LocalDateTime.parse("2020-12-20T00:00:00"),
                LocalDateTime.parse("2030-12-20T00:00:00"));
        subRootDiv1.setParentDivision(rootDiv);

        Division subRootDiv2 = new Division("subHead2", LocalDateTime.parse("2030-12-20T00:00:00"),
                LocalDateTime.parse("2040-12-20T00:00:00"));
        subRootDiv2.setParentDivision(rootDiv);

        Division bottom = new Division("bottom", LocalDateTime.parse("2030-12-20T00:00:00"),
                LocalDateTime.parse("2040-12-20T00:00:00"));
        bottom.setParentDivision(subRootDiv1);

        divisionService.addDivisions(rootDiv, subRootDiv1, subRootDiv2, bottom);
    }
}
