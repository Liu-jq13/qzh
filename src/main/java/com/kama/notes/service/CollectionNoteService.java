package com.kama.notes.service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Transactional
public interface CollectionNoteService {
    /**
     * 查询用户收藏的笔记 Id
     *
     * @param userId 用户 ID
     * @param noteIds 查询的笔记 ID 列表范围
     * @return 返回 noteIds 中被用户收藏的笔记 ID
     */
    Set<Integer> findUserCollectedNoteIds(Long userId, List<Integer> noteIds);
}
