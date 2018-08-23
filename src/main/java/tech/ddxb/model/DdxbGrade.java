package tech.ddxb.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Timestamp;

@JsonInclude(JsonInclude.Include.NON_NULL) // 不序列化空值，既是json中不显示值为空的键值对
public class DdxbGrade implements Serializable {
    private Long id;
    private Long stuId;
    private String objectName;//科目
    private Double score;//分数
    private int questionTotal;//总题数
    private int questionUnfinshed;//未完成题数
    private int questionError;//错题数
    private String correctRate;//正确率
    private String finishedRate;//完成率
    private Timestamp createTime;
    private Timestamp updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public int getQuestionTotal() {
        return questionTotal;
    }

    public void setQuestionTotal(int questionTotal) {
        this.questionTotal = questionTotal;
    }

    public int getQuestionUnfinshed() {
        return questionUnfinshed;
    }

    public void setQuestionUnfinshed(int questionUnfinshed) {
        this.questionUnfinshed = questionUnfinshed;
    }

    public int getQuestionError() {
        return questionError;
    }

    public void setQuestionError(int questionError) {
        this.questionError = questionError;
    }

    public String getCorrectRate() {
        return correctRate;
    }

    public void setCorrectRate(String correctRate) {
        this.correctRate = correctRate;
    }

    public String getFinishedRate() {
        return finishedRate;
    }

    public void setFinishedRate(String finishedRate) {
        this.finishedRate = finishedRate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
}
