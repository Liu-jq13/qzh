package com.kama.notes.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class NoteComment {
    private Integer id;
    private Integer noteId;
    private Long userId;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private Boolean isDeleted;
} 