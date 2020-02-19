package jcode18.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(20001,"你找的问题不在了，换个试试？"),
    TARGET_NOT_FOUND(20002,"未选中任何问题或评论进行回复"),
    NOT_LOGIN(2003,"需要登录，请登录"),
    SYS_ERROR(2004,"服务器冒烟了，稍后试试"),
    TYPE_WRONG(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"评论不存在了"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空"),
    READ_NOTIFICATION_FAIL(2008,"这是读别人信息呢"),
    NOTIFICATION_NOT_FOUND(2009,"回复不存在了"),
    ;
    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }


    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

}
