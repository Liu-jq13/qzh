package com.kama.notes.service.impl;

import com.kama.notes.mapper.QuestionListItemMapper;
import com.kama.notes.mapper.QuestionListMapper;
import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.dto.questionList.CreateQuestionListBody;
import com.kama.notes.model.dto.questionList.UpdateQuestionListBody;
import com.kama.notes.model.entity.QuestionList;
import com.kama.notes.model.vo.questionList.CreateQuestionListVO;
import com.kama.notes.service.QuestionListService;
import com.kama.notes.utils.ApiResponseUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionListServiceImpl implements QuestionListService {

    @Autowired
    private QuestionListMapper questionListMapper;

    @Autowired
    private QuestionListItemMapper questionListItemMapper;

    @Override
    public ApiResponse<QuestionList> getQuestionList(Integer questionListId) {
        return ApiResponseUtil.success("获取题单成功", questionListMapper.findById(questionListId));
    }

    @Override
    public ApiResponse<List<QuestionList>> getQuestionLists() {
        return ApiResponseUtil.success("获取题单成功", questionListMapper.findAll());
    }

    @Override
    public ApiResponse<CreateQuestionListVO> createQuestionList(CreateQuestionListBody body) {

        QuestionList questionList = new QuestionList();
        BeanUtils.copyProperties(body, questionList);

        // 创建题单
        try {
            questionListMapper.insert(questionList);
            CreateQuestionListVO questionListVO = new CreateQuestionListVO();
            questionListVO.setQuestionListId(questionList.getQuestionListId());
            return ApiResponseUtil.success("创建题单成功", questionListVO);
        } catch (Exception e) {
            return ApiResponseUtil.error("创建题单失败");
        }
    }

    @Override
    public ApiResponse<EmptyVO> deleteQuestionList(Integer questionListId) {
        // 删除题单，还需要删除题单对应的题单项目
        QuestionList questionList = questionListMapper.findById(questionListId);

        if (questionList == null) {
            return ApiResponseUtil.error("题单不存在");
        }

        try {
            questionListMapper.deleteById(questionListId);
            // 删除题单对应的所有题单项
            questionListItemMapper.deleteByQuestionListId(questionListId);
            return ApiResponseUtil.success("删除题单成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("删除题单失败");
        }
    }

    @Override
    public ApiResponse<EmptyVO> updateQuestionList(Integer questionListId, UpdateQuestionListBody body) {

        QuestionList questionList = new QuestionList();
        BeanUtils.copyProperties(body, questionList);
        questionList.setQuestionListId(questionListId);

        System.out.println(questionList);

        try {
            questionListMapper.update(questionList);
            return ApiResponseUtil.success("更新题单成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("更新题单失败");
        }
    }
}
