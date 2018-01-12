package com.ifuture.demo.service.dto;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/11.
 */
public class ScoreDTO implements Serializable{
    private String stuName;
    private String courseName;
    private Long score;

    public ScoreDTO() {
        super();
    }

    public ScoreDTO(String stuName, String courseName, Long score) {
        super();
        this.stuName = stuName;
        this.courseName = courseName;
        this.score = score;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
