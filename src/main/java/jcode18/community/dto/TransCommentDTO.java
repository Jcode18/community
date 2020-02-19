package jcode18.community.dto;

import jcode18.community.model.User;
import lombok.Data;

@Data
public class TransCommentDTO {
    private Long id;
    private Long parentId;
    private Integer type;
    private Long commentator;
    private Long gmtCreate;
    private Long gmtModified;
    private Long likeCount;
    private String content;
    private User user;
    private Integer commentCount;
}
