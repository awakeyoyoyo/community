package com.awakeyo.community.pojo;

public class Reply {
    private Long id;

    private Long commentId;

    private String formUid;

    private String toUid;

    private Long gmtCreate;

    private String content;

    private Integer replyLike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getFormUid() {
        return formUid;
    }

    public void setFormUid(String formUid) {
        this.formUid = formUid == null ? null : formUid.trim();
    }

    public String getToUid() {
        return toUid;
    }

    public void setToUid(String toUid) {
        this.toUid = toUid == null ? null : toUid.trim();
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getReplyLike() {
        return replyLike;
    }

    public void setReplyLike(Integer replyLike) {
        this.replyLike = replyLike;
    }
}