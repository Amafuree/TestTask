package com.Amafuree.task.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@NamedEntityGraph(name = "load_children",
        attributeNodes = @NamedAttributeNode("childDivisions"))
public class Division {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Division parentDivision;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parentDivision")
    private List<Division> childDivisions;

    @Column(name = "dt_from")
    private LocalDateTime dateFrom;

    @Column(name = "dt_till")
    private LocalDateTime dateTill;

    @Column(name = "is_system")
    private Boolean isSystem = Boolean.FALSE;

    @Column(name = "creation_date")
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "correction_date")
    private LocalDateTime edited;

    public Division(String name, LocalDateTime dateFrom, LocalDateTime dateTill) {
        this.name = name;
        this.dateFrom = dateFrom;
        this.dateTill = dateTill;
    }

    public Division getParentDivision() {
        return parentDivision == null ? null : parentDivision;
    }

    public List<Division> getChildDivisions() {
        return childDivisions == null || childDivisions.size() == 0 ?
                Collections.emptyList() : childDivisions;
    }

    @Override
    public String toString() {
        return "Division: " + this.name +
                "\n\t-Id: " + (this.id == null ? "null" : this.id.toString()) +
                "\n\t-Parent:" + (this.parentDivision == null
                ? "absent" : this.parentDivision.getName()) +
                "\n\t-Child amount: " + (childDivisions.isEmpty() ?
                0 : childDivisions.size()) +
                "\n\t-Date from: " + this.dateFrom.toString() +
                "\n\t-Date till: " + (this.dateTill == null ?
                "not specified" : this.dateTill.toString()) +
                "\n\t-Created: " + this.created.toString() +
                "\n\t-Last modified: " + (this.edited == null ? "Not modified"
                : this.edited.toString()) +
                "\n\t-Is system: " + this.isSystem.toString();
    }

}
