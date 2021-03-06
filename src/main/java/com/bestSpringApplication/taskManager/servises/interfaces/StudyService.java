package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.classes.StudyTask;
import com.bestSpringApplication.taskManager.models.entities.User;

import java.util.List;

public interface StudyService {
    void setSchemaToUser(String userId,String schemaId);

    void setSchemaToGroup(String groupId,String schemaId);

    void reopenTask(String schemaId, String userId, String taskId);

    boolean canStartTask(String schemaId, String userId, String taskId);

    void forceStartTask(String schemaId, String userId, String taskId);

    void startTaskWithValidation(String schemaId, String userId, String taskId);

    List<User> getCandidatesForSchema(String schemaId);

    List<StudyTask> getAvailableToStartUserTasks(String userId);

    List<StudyTask> getAvailableToStartUserTasks(String userId, String schemaId);

    List<StudyTask> getUserSchemasRootTasks(String userId);

    List<StudyTask> getOpenedUserTasks(String userId, String schemaId);

    List<StudyTask> getOpenedUserTasks(String userId);
}
