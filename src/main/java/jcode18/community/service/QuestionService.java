package jcode18.community.service;

import jcode18.community.dto.PageDTO;
import jcode18.community.dto.QuestionDTO;
import jcode18.community.dto.QuestionQueryDTO;
import jcode18.community.exception.CustomizeErrorCode;
import jcode18.community.exception.CustomizeException;
import jcode18.community.mapper.QuestionExtMapper;
import jcode18.community.mapper.QuestionMapper;
import jcode18.community.mapper.UserMapper;
import jcode18.community.model.Question;
import jcode18.community.model.QuestionExample;
import jcode18.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PageDTO list(String search,Integer page, Integer size) {
        if(StringUtils.isNotBlank(search)){
            String[] tags=StringUtils.split(search," ");
            search=Arrays.stream(tags).collect(Collectors.joining("|"));
        }


        Integer totalPage;
        PageDTO pageDTO=new PageDTO();
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        if(search=="")
            search=null;
        questionQueryDTO.setSearch(search);
        Integer totalCount = questionExtMapper.countBySearch(questionQueryDTO);
        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size +1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        pageDTO.setNeed(totalPage,page);

        Integer offset=size*(page-1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        questionQueryDTO.setPage(offset);
        questionQueryDTO.setSize(size);
        List<Question> questions=questionExtMapper.selectBySearch(questionQueryDTO);
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question:questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setData(questionDTOList);

        return pageDTO;
    }

    public PageDTO listByUserId(Long userId, Integer page, Integer size) {
        Integer totalPage;
        PageDTO pageDTO=new PageDTO();
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalCount = (int) questionMapper.countByExample(example);

        if(totalCount%size==0){
            totalPage=totalCount/size;
        }else {
            totalPage=totalCount/size +1;
        }

        if(page<1){
            page=1;
        }
        if(page>totalPage){
            page=totalPage;
        }
        pageDTO.setNeed(totalPage,page);
        Integer offset=size*(page-1);
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorEqualTo(userId);
        List<Question> questions=questionMapper.selectByExampleWithRowbounds(example1,new RowBounds(offset,size));
        List<QuestionDTO> questionDTOList=new ArrayList<>();
        for (Question question:questions) {
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        pageDTO.setData(questionDTOList);

        return pageDTO;
    }

    public QuestionDTO getById(Long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if(question==null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
            questionMapper.insert(question);
        }else {
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setTag(question.getTag());
            updateQuestion.setDescription(question.getDescription());
            QuestionExample example = new QuestionExample();
            example.createCriteria().andIdEqualTo(question.getId());
            int update=questionMapper.updateByExampleSelective(updateQuestion, example);
            if(update!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(Long id) {

        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO questionDTO) {
        if(StringUtils.isBlank(questionDTO.getTag())){
            return new ArrayList<>();
        }
        String[] tags=StringUtils.split(questionDTO.getTag(),",");
        String regexpTag=Arrays.stream(tags).collect(Collectors.joining("|"));
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setTag(regexpTag);
        List<Question> questions=questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS=questions.stream().map(q->{
            QuestionDTO questionDTO1 = new QuestionDTO();
            BeanUtils.copyProperties(q,questionDTO1);
            return questionDTO1;}).collect(Collectors.toList());
        return questionDTOS;
    }
}
