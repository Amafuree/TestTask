package com.Amafuree.task.model.dto;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.util.DivisionConverter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public final class TreeifiedDivisionDtos implements Serializable {

    private Member rootElement;

    public TreeifiedDivisionDtos(List<Division> divisionList) {
        for (Division division : divisionList) {
            if (division.getParentDivision() == null) {
                rootElement = new Member(division);
                break;
            }
        }
    }

    public String getHierarchicName(String name) {
        return rootElement.seekForDivision(name);
    }

    private static class Member {

        private final DivisionDto storedDivision;
        private final String name;
        private final List<Member> children;
        private DivisionDto parentDivision;

        public Member(Division division) {
            this.storedDivision = DivisionConverter.convert(division);
            this.name = storedDivision.getName();
            this.children = treeifyList(division.getChildDivisions());
        }

        private List<Member> treeifyList(List<Division> divisionList) {
            return divisionList.stream()
                    .map(Member::new)
                    .map(member -> setParent(member, storedDivision))
                    .collect(Collectors.toList());
        }

        private Member setParent(Member member, DivisionDto dto) {
            member.parentDivision = dto;
            return member;
        }

        private String seekForDivision(String name) {
            String seekResult = "";
            if (!name.equals(this.name)) {
                for (Member member : children) {
                    seekResult = member.seekForDivision(name);
                    if (seekResult.equals(name)) {
                        break;
                    }
                }
            } else {
                return this.name;
            }
            return seekResult.equals("") || seekResult.contains("Not found!") ?
                    "Not found!" : this.name + "/" + seekResult;
        }
    }
}
