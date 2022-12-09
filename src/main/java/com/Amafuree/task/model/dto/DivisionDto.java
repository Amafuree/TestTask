package com.Amafuree.task.model.dto;


import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class DivisionDto {

    private Long id;
    private String name;
    //private DivisionDto parentDivision;
    //private List<DivisionDto> childDivisions;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTill;
    private Boolean isSystem;
    private LocalDateTime created;
    private LocalDateTime edited;

//    public DivisionDto getParentDivision() {
//        return parentDivision == null ? null : parentDivision;
//    }

//    public List<DivisionDto> getChildDivisions() {
//        return childDivisions == null || childDivisions.size() == 0 ?
//                Collections.emptyList() : childDivisions;
//    }

    @Override
    public String toString() {
        return "Division: " + this.name +
                "\n\t-Id: " + this.id.toString() +
                // "\n\t-Parent:" + (this.parentDivision == null
                // ? "absent" : this.parentDivision.getName()) +
                // "\n\t-Child amount: " + getChildDivisions().size() +
                "\n\t-Date from: " + this.dateFrom.toString() +
                "\n\t-Date till: " + this.dateTill.toString() +
                "\n\t-Created: " + this.created.toString() +
                "\n\t-Last modified: " + this.edited.toString() +
                "\n\t-Is system: " + this.isSystem.toString();
    }
}
