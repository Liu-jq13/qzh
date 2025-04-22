package com.kama.notes.mapper;

import com.kama.notes.model.entity.NoteComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoteCommentMapper {
    
    /**
     * 插入评论
     */
    void insert(NoteComment comment);

    /**
     * 更新评论
     */
    void update(NoteComment comment);

    /**
     * 根据ID查询评论
     */
    NoteComment findById(@Param("id") Integer id);

    /**
     * 查询笔记的评论列表
     */
    List<NoteComment> findByNoteId(@Param("noteId") Integer noteId);
} 