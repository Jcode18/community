package jcode18.community.controller;

import jcode18.community.dto.CommentDTO;
import jcode18.community.dto.ResultDTO;
import jcode18.community.dto.TransCommentDTO;
import jcode18.community.enums.CommentTypeEnum;
import jcode18.community.exception.CustomizeErrorCode;
import jcode18.community.model.Comment;
import jcode18.community.model.User;
import jcode18.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user=(User) request.getSession().getAttribute("user");
        if(user==null){
            return ResultDTO.errorOf(CustomizeErrorCode.NOT_LOGIN);
        }
        if(commentDTO==null|| StringUtils.isBlank(commentDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setContent(commentDTO.getContent());
        comment.setType(commentDTO.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(System.currentTimeMillis());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0L);
        commentService.insert(comment,user);
        return ResultDTO.okOf(null);
    }

    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id")Long id){
        List<TransCommentDTO> commentDTOS=commentService.listByTargetId(id,CommentTypeEnum.COMMENT);
        
        return ResultDTO.okOf(commentDTOS);
    }
}
