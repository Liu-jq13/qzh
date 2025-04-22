package com.kama.notes.service.impl;

import com.kama.notes.annotation.NeedLogin;
import com.kama.notes.mapper.QuestionMapper;
import com.kama.notes.model.base.ApiResponse;
import com.kama.notes.model.base.EmptyVO;
import com.kama.notes.model.base.Pagination;
import com.kama.notes.model.dto.note.CreateNoteRequest;
import com.kama.notes.model.dto.note.NoteQueryParams;
import com.kama.notes.model.dto.note.UpdateNoteRequest;
import com.kama.notes.model.entity.Note;
import com.kama.notes.mapper.NoteMapper;
import com.kama.notes.model.entity.Question;
import com.kama.notes.model.entity.User;
import com.kama.notes.model.vo.category.CategoryVO;
import com.kama.notes.model.vo.note.*;
import com.kama.notes.scope.RequestScopeData;
import com.kama.notes.service.*;
import com.kama.notes.utils.ApiResponseUtil;
import com.kama.notes.utils.MarkdownUtil;
import com.kama.notes.utils.PaginationUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteMapper noteMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NoteLikeService noteLikeService;

    @Autowired
    private CollectionNoteService collectionNoteService;

    @Autowired
    private RequestScopeData requestScopeData;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public ApiResponse<List<NoteVO>> getNotes(NoteQueryParams params) {

        // 计算分页参数
        int offset = PaginationUtils.calculateOffset(params.getPage(), params.getPageSize());

        // 查询当前查询条件下的笔记总数
        int total = noteMapper.countNotes(params);

        Pagination pagination = new Pagination(params.getPage(), params.getPageSize(), total);

        // 获取笔记列表
        List<Note> notes = noteMapper.findByQueryParams(params, offset, params.getPageSize());

        // 从 笔记列表 中提取 questionIds 和 authorIds，并去重
        List<Integer> questionIds = notes.stream().map(Note::getQuestionId).distinct().toList();
        List<Long> authorIds = notes.stream().map(Note::getAuthorId).distinct().toList();
        List<Integer> noteIds = notes.stream().map(Note::getNoteId).toList();

        // 笔记的作者信息
        Map<Long, User> userMapByIds = userService.getUserMapByIds(authorIds);
        // 笔记的问题信息
        Map<Integer, Question> questionMapByIds = questionService.getQuestionMapByIds(questionIds);

        // 当前登录用户点赞的笔记列表和收藏的笔记列表
        Set<Integer> userLikedNoteIds;
        Set<Integer> userCollectedNoteIds;

        // 如果是登录状态，则对当前查询的笔记列表进行是否点赞过 / 收藏过的判断
        if (requestScopeData.isLogin() && requestScopeData.getUserId() != null) {
            Long currentUserId = requestScopeData.getUserId();
            userLikedNoteIds = noteLikeService.findUserLikedNoteIds(currentUserId, noteIds);
            userCollectedNoteIds = collectionNoteService.findUserCollectedNoteIds(currentUserId, noteIds);
        } else {  // 未登录状态直接设置为空集合
            userLikedNoteIds = Collections.emptySet();
            userCollectedNoteIds = Collections.emptySet();
        }

        // 用户的点赞信息
        // 用户的收藏信息
        try {
            List<NoteVO> noteVOs = notes.stream().map(note -> {
                NoteVO noteVO = new NoteVO();
                BeanUtils.copyProperties(note, noteVO);

                // 填充作者信息
                User author = userMapByIds.get(note.getAuthorId());
                if (author != null) {
                    NoteVO.SimpleAuthorVO authorVO = new NoteVO.SimpleAuthorVO();
                    BeanUtils.copyProperties(author, authorVO);
                    noteVO.setAuthor(authorVO);
                }

                // 填充问题信息
                Question question = questionMapByIds.get(note.getQuestionId());
                if (question != null) {
                    NoteVO.SimpleQuestionVO questionVO = new NoteVO.SimpleQuestionVO();
                    BeanUtils.copyProperties(question, questionVO);
                    noteVO.setQuestion(questionVO);
                }

                // 填充用户行为信息
                NoteVO.UserActionsVO userActionsVO = new NoteVO.UserActionsVO();
                if (userLikedNoteIds != null && userLikedNoteIds.contains(note.getNoteId())) {
                    userActionsVO.setIsLiked(true);
                }
                if (userCollectedNoteIds != null && userCollectedNoteIds.contains(note.getNoteId())) {
                    userActionsVO.setIsCollected(true);
                }

                // 处理笔记内容折叠内容
                if (MarkdownUtil.needCollapsed(note.getContent())) {
                    noteVO.setNeedCollapsed(true);
                    noteVO.setDisplayContent(MarkdownUtil.extractIntroduction(note.getContent()));
                } else {
                    noteVO.setNeedCollapsed(false);
                }

                noteVO.setUserActions(userActionsVO);
                return noteVO;
            }).toList();

            return ApiResponseUtil.success("获取笔记列表成功", noteVOs, pagination);
        } catch (Exception e) {
            // TODO: 打印日志
            System.out.println(Arrays.toString(e.getStackTrace()));
            return ApiResponseUtil.error("获取笔记列表失败");
        }
    }

    @Override
    @NeedLogin
    public ApiResponse<CreateNoteVO> createNote(CreateNoteRequest request) {
        Long userId = requestScopeData.getUserId();
        Integer questionId = request.getQuestionId();

        // 判断问题指定的问题是否存在
        Question question = questionService.findById(questionId);

        if (question == null) {  // 对应的问题不存在
            return ApiResponseUtil.error("questionId 对应的问题不存在");
        }

        Note note = new Note();
        BeanUtils.copyProperties(request, note);
        note.setAuthorId(userId);

        try {
            noteMapper.insert(note);
            CreateNoteVO createNoteVO = new CreateNoteVO();
            createNoteVO.setNoteId(note.getNoteId());
            return ApiResponseUtil.success("创建笔记成功", createNoteVO);
        } catch (Exception e) {
            return ApiResponseUtil.error("创建笔记失败");
        }
    }

    @Override
    @NeedLogin
    public ApiResponse<EmptyVO> updateNote(Integer noteId, UpdateNoteRequest request) {

        Long userId = requestScopeData.getUserId();

        // 查询笔记
        Note note = noteMapper.findById(noteId);
        if (note == null) {
            return ApiResponseUtil.error("笔记不存在");
        }

        if (!Objects.equals(userId, note.getAuthorId())) {
            return ApiResponseUtil.error("没有权限修改别人的笔记");
        }

        try {
            note.setContent(request.getContent());
            noteMapper.update(note);
            return ApiResponseUtil.success("更新笔记成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("更新笔记失败");
        }
    }

    @Override
    @NeedLogin
    public ApiResponse<EmptyVO> deleteNote(Integer noteId) {

        Long userId = requestScopeData.getUserId();

        Note note = noteMapper.findById(noteId);

        if (note == null) {
            return ApiResponseUtil.error("笔记不存在");
        }

        if (!Objects.equals(userId, note.getAuthorId())) {
            // 没有权限删除别人的笔记
            return ApiResponseUtil.error("没有权限删除别人的笔记");
        }

        try {
            noteMapper.deleteById(noteId);
            return ApiResponseUtil.success("删除笔记成功");
        } catch (Exception e) {
            return ApiResponseUtil.error("删除笔记失败");
        }
    }

    // 下载笔记
    @Override
    @NeedLogin
    public ApiResponse<DownloadNoteVO> downloadNote() {

        Long userId = requestScopeData.getUserId();

        // 获取所有笔记
        List<Note> userNotes = noteMapper.findByAuthorId(userId);

        // 将笔记转为 key = questionId, value = note 的 map 对象
        Map<Integer, Note> questionNoteMap = userNotes.stream()
                .collect(Collectors.toMap(Note::getQuestionId, note -> note));

        if (userNotes.isEmpty()) {
            return ApiResponseUtil.error("不存在任何笔记");
        }

        // 获取分类树
        List<CategoryVO> categoryTree = categoryService.buildCategoryTree();

        // 根据分类树，创建 markdown 文件
        StringBuilder markdownContent = new StringBuilder();

        // 将 note 中的所有 questionId 提取出来
        List<Integer> questionIds = userNotes.stream()
                .map(Note::getQuestionId)
                .toList();

        List<Question> questions = questionMapper.findByIdBatch(questionIds);

        for (CategoryVO categoryVO : categoryTree) {

            boolean hasTopLevelToc = false;

            if (categoryVO.getChildren().isEmpty()) {
                continue;
            }

            for (CategoryVO.ChildrenCategoryVO childrenCategoryVO : categoryVO.getChildren()) {

                boolean hasSubLevelToc = false;
                Integer categoryId = childrenCategoryVO.getCategoryId();

                // 用户在该分类下的笔记对应的所有问题
                List<Question> categoryQuestionList = questions.stream()
                        .filter(question -> question.getCategoryId().equals(categoryId))
                        .toList();

                if (categoryQuestionList.isEmpty()) {
                    continue;
                }

                for (Question question : categoryQuestionList) {

                    if (!hasTopLevelToc) {  // 设置一级标题
                        markdownContent.append("# ").append(categoryVO.getName()).append("\n");
                        hasTopLevelToc = true;
                    }

                    if (!hasSubLevelToc) {  // 设置二级标题
                        markdownContent.append("## ").append(childrenCategoryVO.getName()).append("\n");
                        hasSubLevelToc = true;
                    }

                    markdownContent.append("### [")
                            .append(question.getTitle())
                            .append("]")
                            .append("(https://notes.kamacoder.com/questions/")
                            .append(question.getQuestionId())
                            .append(")\n");

                    Note note = questionNoteMap.get(question.getQuestionId());

                    markdownContent.append(note.getContent()).append("\n");
                }
            }
        }

        // 设置笔记内容
        DownloadNoteVO downloadNoteVO = new DownloadNoteVO();
        downloadNoteVO.setMarkdown(markdownContent.toString());

        return ApiResponseUtil.success("生成笔记成功", downloadNoteVO);
    }

    @Override
    public ApiResponse<List<NoteRankListItem>> submitNoteRank() {
        return ApiResponseUtil.success("获取笔记排行榜成功", noteMapper.submitNoteRank());
    }

    @Override
    @NeedLogin
    public ApiResponse<List<NoteHeatMapItem>> submitNoteHeatMap() {
        Long userId = requestScopeData.getUserId();
        return ApiResponseUtil.success("获取笔记热力图成功", noteMapper.submitNoteHeatMap(userId));
    }

    @Override
    @NeedLogin
    public ApiResponse<Top3Count> submitNoteTop3Count() {

        Long userId = requestScopeData.getUserId();

        Top3Count top3Count = noteMapper.submitNoteTop3Count(userId);

        return ApiResponseUtil.success("获取笔记top3成功", top3Count);
    }
}
