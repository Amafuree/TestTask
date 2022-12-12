package com.Amafuree.task.tree;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.model.dto.TreeifiedDivisionDtos;
import com.Amafuree.task.service.DivisionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TreeTest {

    private final DivisionService divisionService;

    @Autowired
    public TreeTest(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @Test
    public void treeifyingTest() {
        List<Division> divisions = divisionService.getEagerLoadedDivisions();
        TreeifiedDivisionDtos tree = new TreeifiedDivisionDtos(divisions);

        String expectedNames = """
                HEAD
                HEAD/subHead_1
                HEAD/subHead_2
                HEAD/subHead_3
                HEAD/created_after
                HEAD/subHead_1/sub_1_child
                HEAD/subHead_2/sub_2_child_1
                HEAD/subHead_2/sub_2_child_2
                HEAD/subHead_2/sub_2_child_1/child_1_child""";

        String hierarchicNames = tree.getHierarchicName("HEAD") + "\n"
                + tree.getHierarchicName("subHead_1") + "\n"
                + tree.getHierarchicName("subHead_2") + "\n"
                + tree.getHierarchicName("subHead_3") + "\n"
                + tree.getHierarchicName("created_after") + "\n"
                + tree.getHierarchicName("sub_1_child") + "\n"
                + tree.getHierarchicName("sub_2_child_1") + "\n"
                + tree.getHierarchicName("sub_2_child_2") + "\n"
                + tree.getHierarchicName("child_1_child");

        assertEquals(expectedNames, hierarchicNames);
    }
}
