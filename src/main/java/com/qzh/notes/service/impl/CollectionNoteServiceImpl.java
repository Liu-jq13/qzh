package com.qzh.notes.service.impl;

import com.qzh.notes.mapper.CollectionNoteMapper;
import com.qzh.notes.service.CollectionNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CollectionNoteServiceImpl implements CollectionNoteService {

    @Autowired
    private CollectionNoteMapper collectionNoteMapper;

    @Override
    public Set<Integer> findUserCollectedNoteIds(Long userId, List<Integer> noteIds) {
        List<Integer> userCollectedNoteIds
                = collectionNoteMapper.findUserCollectedNoteIds(userId, noteIds);
        return new HashSet<>(userCollectedNoteIds);
    }
}
