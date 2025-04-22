package com.kama.notes.service.impl;

import com.kama.notes.mapper.NoteMapper;
import com.kama.notes.mapper.UserMapper;
import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.entity.Note;
import com.kama.notes.model.entity.User;
import com.kama.notes.service.SearchService;
import com.kama.notes.utils.ApiResponseUtil;
import com.kama.notes.utils.SearchUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private static final String NOTE_SEARCH_CACHE_KEY = "search:note:%s:%d:%d";
    private static final String USER_SEARCH_CACHE_KEY = "search:user:%s:%d:%d";
    private static final String NOTE_TAG_SEARCH_CACHE_KEY = "search:note:tag:%s:%s:%d:%d";
    private static final long CACHE_EXPIRE_TIME = 30; // 分钟

    @Override
    public ApiResponse<List<Note>> searchNotes(String keyword, int page, int pageSize) {
        try {
            String cacheKey = String.format(NOTE_SEARCH_CACHE_KEY, keyword, page, pageSize);
            
            // 尝试从缓存获取
            List<Note> cachedResult = (List<Note>) redisTemplate.opsForValue().get(cacheKey);
            if (cachedResult != null) {
                return ApiResponseUtil.success("搜索成功", cachedResult);
            }

            // 处理关键词
            keyword = SearchUtils.preprocessKeyword(keyword);
            
            // 计算偏移量
            int offset = (page - 1) * pageSize;
            
            // 执行搜索
            List<Note> notes = noteMapper.searchNotes(keyword, pageSize, offset);
            
            // 存入缓存
            redisTemplate.opsForValue().set(cacheKey, notes, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
            
            return ApiResponseUtil.success("搜索成功", notes);
        } catch (Exception e) {
            log.error("搜索笔记失败", e);
            return ApiResponseUtil.error("搜索失败");
        }
    }

    @Override
    public ApiResponse<List<User>> searchUsers(String keyword, int page, int pageSize) {
        try {
            String cacheKey = String.format(USER_SEARCH_CACHE_KEY, keyword, page, pageSize);
            
            // 尝试从缓存获取
            List<User> cachedResult = (List<User>) redisTemplate.opsForValue().get(cacheKey);
            if (cachedResult != null) {
                return ApiResponseUtil.success("搜索成功", cachedResult);
            }

            // 计算偏移量
            int offset = (page - 1) * pageSize;
            
            // 执行搜索
            List<User> users = userMapper.searchUsers(keyword, pageSize, offset);
            
            // 存入缓存
            redisTemplate.opsForValue().set(cacheKey, users, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
            
            return ApiResponseUtil.success("搜索成功", users);
        } catch (Exception e) {
            log.error("搜索用户失败", e);
            return ApiResponseUtil.error("搜索失败");
        }
    }

    @Override
    public ApiResponse<List<Note>> searchNotesByTag(String keyword, String tag, int page, int pageSize) {
        try {
            String cacheKey = String.format(NOTE_TAG_SEARCH_CACHE_KEY, keyword, tag, page, pageSize);
            
            // 尝试从缓存获取
            List<Note> cachedResult = (List<Note>) redisTemplate.opsForValue().get(cacheKey);
            if (cachedResult != null) {
                return ApiResponseUtil.success("搜索成功", cachedResult);
            }

            // 处理关键词
            keyword = SearchUtils.preprocessKeyword(keyword);
            
            // 计算偏移量
            int offset = (page - 1) * pageSize;
            
            // 执行搜索
            List<Note> notes = noteMapper.searchNotesByTag(keyword, tag, pageSize, offset);
            
            // 存入缓存
            redisTemplate.opsForValue().set(cacheKey, notes, CACHE_EXPIRE_TIME, TimeUnit.MINUTES);
            
            return ApiResponseUtil.success("搜索成功", notes);
        } catch (Exception e) {
            log.error("搜索笔记失败", e);
            return ApiResponseUtil.error("搜索失败");
        }
    }
} 