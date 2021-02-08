package com.bestSpringApplication.taskManager.models.study.implementations;

import com.bestSpringApplication.taskManager.handlers.GradeToDbConverter;
import com.bestSpringApplication.taskManager.models.idRelation.IdRelation;
import com.bestSpringApplication.taskManager.models.study.enums.Grade;
import com.bestSpringApplication.taskManager.models.study.interfaces.UserTaskRelation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "user_task_relation")
public class UserTaskRelationImpl implements UserTaskRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Integer db_id;
    @OneToOne(fetch = FetchType.EAGER)
    private IdRelation userRelation;
    @OneToOne(fetch = FetchType.EAGER)
    private IdRelation taskRelation;
    private boolean isFinished;
    private boolean finishConfirmed;
    @Convert(converter = GradeToDbConverter.class)
    private Grade grade;

    public UserTaskRelationImpl(){}
    
    public UserTaskRelationImpl(IdRelation userRelation,
                                IdRelation taskRelation,
                                boolean isFinished,
                                boolean finishConfirmed,
                                Grade grade) {
        this.userRelation = userRelation;
        this.taskRelation = taskRelation;
        this.isFinished = isFinished;
        this.finishConfirmed = finishConfirmed;
        this.grade = grade;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public String getTaskId() {
        return null;
    }

    @Override
    public String getSchemeId() {
        return null;
    }

    public Integer getDb_id() {
        return db_id;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public boolean isFinishConfirmed() {
        return finishConfirmed;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setDb_id(Integer bd_id) {
        this.db_id = bd_id;
    }

    public void setUserRelation(IdRelation userRelation) {
        this.userRelation = userRelation;
    }

    public void setTaskRelation(IdRelation taskRelation) {
        this.taskRelation = taskRelation;
    }

    public void setIsFinished(boolean userIsFinishTask) {
        this.isFinished = userIsFinishTask;
    }

    public void setFinishConfirmed(boolean confirmTask) {
        this.finishConfirmed = confirmTask;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
}
