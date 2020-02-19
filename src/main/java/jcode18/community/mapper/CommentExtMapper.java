package jcode18.community.mapper;

import jcode18.community.model.Comment;
import jcode18.community.model.CommentExample;
import jcode18.community.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}