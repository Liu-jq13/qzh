package com.kama.notes.service.impl;

import com.kama.notes.mapper.NoteMapper;
import com.kama.notes.mapper.QuestionListItemMapper;
import com.kama.notes.mapper.QuestionListMapper;
import com.kama.notes.mapper.UserMapper;
import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.base.Pagination;
import com.kama.notes.model.dto.questionListItem.CreateQuestionListItemBody;
import com.kama.notes.model.dto.questionListItem.QuestionListItemQueryParams;
import com.kama.notes.model.dto.questionListItem.SortQuestionListItemBody;
import com.kama.notes.model.entity.QuestionList;
import com.kama.notes.model.entity.QuestionListItem;
import com.kama.notes.model.vo.questionListItem.CreateQuestionListItemVO;
import com.kama.notes.model.vo.questionListItem.QuestionListItemUserVO;
import com.kama.notes.model.vo.questionListItem.QuestionListItemVO;
import com.kama.notes.scope.RequestScopeData;
import com.kama.notes.service.QuestionListItemService;
import com.kama.notes.utils.ApiResponseUtil;
import com.kama.notes.utils.PaginationUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class QuestionListItemServiceImpl implements QuestionListItemService {

    @Autowired
    private QuestionListItemMapper questionListItemMapper;

    @Autowired
    private QuestionListMapper questionListMapper;

    @Autowired
    private RequestScopeData requestScopeData;

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public ApiResponse<List<QuestionListItemUserVO>> userGetQuestionListItems(QuestionListItemQueryParams queryParams) {
        // 需要获取题单信息
        int offset = PaginationUtils.calculateOffset(queryParams.getPage(), queryParams.getPageSize());

        int total = questionListItemMapper.countByQuestionListId(queryParams.getQuestionListId());

        Pagination pagination = new Pagination(queryParams.getPage(), queryParams.getPageSize(), total);

        // 获取题单
        Integer questionListId = queryParams.getQuestionListId();

        QuestionList questionList = questionListMapper.findById(questionListId);

        // 获取题单项列表
        List<QuestionListItemVO> questionListItems =
                questionListItemMapper.findByQuestionListIdPage(
                        queryParams.getQuestionListId(),
                        queryParams.getPageSize(),
                        offset
                );

        // 获取这些题单的所有的题目 ID
        List<Integer> questionIds = questionListItems.stream()
                .map(questionListItemVO -> questionListItemVO.getQuestion().getQuestionId())
                .toList();

        final Set<Integer> userFinishedQuestionIds;

        // 如果是登录状态，则筛选出当前题单中的用户完成的题目 ID
        if (requestScopeData.isLogin()) {
            userFinishedQuestionIds =
                    noteMapper.filterFinishedQuestionIdsByUser(requestScopeData.getUserId(), questionIds);
        } else {
            userFinishedQuestionIds = Collections.emptySet();
        }

        // 将 QuestionListItemVO 映射为带用户状态的 QuestionListItemUserVO
        List<QuestionListItemUserVO> list = questionListItems.stream().map(questionListItemVO -> {

            QuestionListItemUserVO questionListItemUserVO = new QuestionListItemUserVO();
            BeanUtils.copyProperties(questionListItemVO, questionListItemUserVO);

            QuestionListItemUserVO.UserQuestionStatus userQuestionStatus =
                    new QuestionListItemUserVO.UserQuestionStatus();

            if (requestScopeData.isLogin()) {  // 当前是登录状态
                userQuestionStatus.setFinished(userFinishedQuestionIds.contains(questionListItemVO.getQuestion().getQuestionId()));
            } else {
                userQuestionStatus.setFinished(false);
            }

            questionListItemUserVO.setUserQuestionStatus(userQuestionStatus);

            return questionListItemUserVO;
        }).toList();

        return ApiResponseUtil.success("获取用户题单项列表成功", list, pagination);
    }

    @Override
    public ApiResponse<List<QuestionListItemVO>> getQuestionListItems(Integer questionListId) {

        List<QuestionListItemVO> byQuestionListId = questionListItemMapper.findByQuestionListId(questionListId);

        return ApiResponseUtil.success("获取题单项列表成功", byQuestionListId);
    }

    @Override
    public ApiResponse<CreateQuestionListItemVO> createQuestionListItem(CreateQuestionListItemBody body) {

        QuestionListItem questionListItem = new QuestionListItem();
        BeanUtils.copyProperties(body, questionListItem);

        try {
            // 生成题单项的 rank
            int rank = questionListItemMapper.nextRank(body.getQuestionListId());
            questionListItem.setRank(rank);

            questionListItemMapper.insert(questionListItem);
            CreateQuestionListItemVO createQuestionListItemVO = new CreateQuestionListItemVO();
            createQuestionListItemVO.setRank(questionListItem.getRank());
            return ApiResponseUtil.success("创建题单项成功", createQuestionListItemVO);
        } catch (Exception e) {
            return ApiResponseUtil.error("创建题单项失败");
        }
    }

    @Override
    public ApiResponse<EmptyVO> deleteQuestionListItem(Integer questionListId, Integer questionId) {
        try {
            questionListItemMapper.deleteByQuestionListIdAndQuestionId(questionListId, questionId);
            return ApiResponseUtil.success("删除题单项成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("删除题单项失败");
        }
    }

    @Override
    public ApiResponse<EmptyVO> sortQuestionListItem(SortQuestionListItemBody body) {
        // TODO: 待优化
        List<Integer> questionIds = body.getQuestionIds();
        Integer questionListId = body.getQuestionListId();

        try {
            for (int i = 0; i < questionIds.size(); i++) {
                QuestionListItem questionListItem = new QuestionListItem();
                questionListItem.setQuestionId(questionIds.get(i));
                questionListItem.setQuestionListId(questionListId);
                questionListItem.setRank(i + 1);
                questionListItemMapper.updateQuestionRank(questionListItem);
            }
            return ApiResponseUtil.success("题单项排序成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("题单项排序失败");
        }
    }
}
