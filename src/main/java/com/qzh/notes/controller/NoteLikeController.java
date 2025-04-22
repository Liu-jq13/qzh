package com.qzh.notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qzh.notes.model.base.ApiResponse;
import com.qzh.notes.model.base.EmptyVO;
import com.qzh.notes.service.NoteLikeService;

@RestController
@RequestMapping("/api")
public class NoteLikeController {
    @Autowired
    private NoteLikeService noteLikeService;

    @PostMapping("/like/note/{noteId}")
    public ApiResponse<EmptyVO> likeNote(@PathVariable Integer noteId) {
        return noteLikeService.likeNote(noteId);
    }

    @DeleteMapping("/like/note/{noteId}")
    public ApiResponse<EmptyVO> unlikeNote(@PathVariable Integer noteId) {
        return noteLikeService.unlikeNote(noteId);
    }
}
