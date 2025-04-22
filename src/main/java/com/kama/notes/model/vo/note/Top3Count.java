package com.kama.notes.model.vo.note;

import lombok.Data;

@Data
public class Top3Count {
    private Integer lastMonthTop3Count;
    private Integer thisMonthTop3Count;
}
