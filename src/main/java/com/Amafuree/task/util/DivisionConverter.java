package com.Amafuree.task.util;

import com.Amafuree.task.model.Division;
import com.Amafuree.task.model.dto.DivisionDto;

public class DivisionConverter {

    public static DivisionDto convert(Division division) {
        return new DivisionDto(division.getId(), division.getName(),
                division.getDateFrom(), division.getDateTill(), division.getIsSystem(),
                division.getCreated(), division.getEdited());
    }
}
