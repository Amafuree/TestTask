package com.Amafuree.task.repository;

import com.Amafuree.task.model.Division;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DivisionRepository extends JpaRepository<Division, Long> {


    @EntityGraph("load_children")
    @Query("from Division a where a.dateFrom <= ?1 and a.dateTill > ?1 and a.created <= ?1")
    List<Division> getDivisionsByDate(LocalDateTime dateTime);

    @EntityGraph("load_children")
    @Query("from Division")
    List<Division> getEagerLoadedDivisions();
}
