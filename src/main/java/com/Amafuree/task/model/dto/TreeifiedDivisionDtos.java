package com.Amafuree.task.model.dto;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.util.DivisionConverter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public final class TreeifiedDivisionDtos implements Serializable {

    static final long serialVersionUID = 1L;
    private Member rootElement;
    private List<Division> passedList;
    private final int size;

    public TreeifiedDivisionDtos(List<Division> divisionList) {
        this.passedList = divisionList;
        for (Division division : divisionList) {
            if (division.getParentDivision() == null) {
                rootElement = new Member(division);
                break;
            }
        }
        this.size = divisionList.size();
    }

    public String getHierarchicName(String name) {
        return rootElement.seekForDivision(name);
    }

    public int size() {
        return this.size;
    }

    public List<Division> getPassedList() {
        return this.passedList;
    }

    private class Member {

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
                    .filter(division -> passedList.contains(division))
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
                    if (seekResult.contains(name)) {
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
