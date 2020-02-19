package jcode18.community.service;

import jcode18.community.dto.TransCommentDTO;
import jcode18.community.enums.CommentTypeEnum;
import jcode18.community.enums.NotificationEnum;
import jcode18.community.enums.NotificationStatusEnum;
import jcode18.community.exception.CustomizeErrorCode;
import jcode18.community.exception.CustomizeException;
import jcode18.community.mapper.*;
import jcode18.community.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private CommentExtMapper commentExtMapper;
    @Autowired
    private NotificationMapper notificationMapper;

    @Transactional
    public void insert(Comment comment,User commentator) {
        if(comment.getParentId()==null||comment.getParentId()==0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }

        if(comment.getType()==null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TARGET_NOT_FOUND);
        }

        if(comment.getType()==CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment==null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            Question question = questionMapper.selectByPrimaryKey(dbComment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }

            commentMapper.insert(comment);
            //增加评论数
            Comment parentComment=new Comment();
            parentComment.setId(comment.getParentId());
            parentComment.setCommentCount(1);
            commentExtMapper.incCommentCount(parentComment);
            createNotify(comment,dbComment.getCommentator(), commentator.getName(), question.getTitle(), NotificationEnum.REPLY_COMMENT, question.getId());
        }else{
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question==null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            comment.setCommentCount(0);
            commentMapper.insert(comment);
            question.setCommentCount(1);
            questionExtMapper.incCommentCount(question);

            createNotify(comment,question.getCreator(),commentator.getName(),question.getTitle(), NotificationEnum.REPLY_QUESTION, question.getId());
        }
    }

    private void createNotify(Comment comment, Long receiver, String notifierName, String outerTitle, NotificationEnum notificationTyep, Long outerId) {
        if(receiver==comment.getCommentator()){
            return;
        }
        Notification notification = new Notification();
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setType(notificationTyep.getType());
        notification.setOuterid(outerId);
        notification.setNotifier(comment.getCommentator());
        notification.setStatus(NotificationStatusEnum.UNREAD.getStatus());
        notification.setReceiver(receiver);
        notification.setNotifierName(notifierName);
        notification.setOuterTitle(outerTitle);
        notificationMapper.insert(notification);
    }

    public List<TransCommentDTO> listByTargetId(Long id, CommentTypeEnum type) {
        CommentExample example = new CommentExample();
        example.createCriteria().andParentIdEqualTo(id).andTypeEqualTo(type.getType());
        example.setOrderByClause("gmt_create desc");
        List<Comment> comments=commentMapper.selectByExample(example);

        if(comments.size()==0){
            return new ArrayList<>();
        }
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());
        List<Long> userIds=new ArrayList<>();
        userIds.addAll(commentators);
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        List<User> users=userMapper.selectByExample(userExample);

        Map<Long,User> userMap=users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));

        List<TransCommentDTO> commentDTOS=comments.stream().map(comment -> {
            TransCommentDTO commentDTO=new TransCommentDTO();
            BeanUtils.copyProperties(comment,commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;

    }
}
