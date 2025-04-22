package com.kama.notes.model.enums.questionList;

import lombok.Getter;

@Getter
public enum QuestionListType {
    COMMON(1, "普通题单"),
    TRAINING_CAMP(2, "训练营题单");
    private final Integer type;
    private final String desc;
    QuestionListType(Integer type, String desc) {
        this.type = type;
        this.desc = desc;
    }
}
