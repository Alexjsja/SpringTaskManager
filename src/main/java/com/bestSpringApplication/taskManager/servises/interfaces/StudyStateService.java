package com.bestSpringApplication.taskManager.servises.interfaces;

import com.bestSpringApplication.taskManager.models.classes.StudySchema;
import com.bestSpringApplication.taskManager.models.classes.StudyTask;
import com.bestSpringApplication.taskManager.models.entities.UserTaskState;
import com.bestSpringApplication.taskManager.models.enums.Status;

import java.util.List;

public interface StudyStateService {

    void prepareSchema(StudySchema schema, String userId);

    void prepareTask(StudySchema schema, StudyTask task, String userId);

    void openTask(String schemaId, String userId, String taskId);

    void setStatusForUserTask(String schemaId, String userId, String taskId,Status status);

    void setPercentCompleteForUserTask(String schemaId, String userId, String taskId, double percent);

    void setStatusAndPercentCompleteForUserTask(String schemaId, String userId, String taskId, Status status, double percent);

    boolean schemaOfUserExists(String schemaId, String userId);

    boolean schemaOfGroupExists(String groupId, String schemaId);

    boolean taskExists(String schemaId, String userId, String taskId);

    boolean taskFinished(String schemaId,String userId,String taskId);

    boolean taskContainsStatus(String schemaId, String userId, String taskId, Status status);

    boolean taskInWork(String schemaId, String userId, String taskId);

    List<String> getCompletedTasksIdOfSchemaForUser(String schemaId, String userId);

    List<String> getOpenedSchemasIdOfUser(String userId);

    List<String> getOpenedTasksIdBySchemaOfUser(String userId, String schemaId);

    List<String> getUsersIdBySchemaId(String id);

    List<UserTaskState> getUnconfirmedTasks();

    List<UserTaskState> getAllStateBySchemaId(String schemaId);

    List<UserTaskState> getSchemaStateByUserId(String userId, String schemaId);

    List<UserTaskState> getTaskStateInSchema(String schemaId, String taskId);

    List<UserTaskState> getAllUserStates(String userId);

    List<UserTaskState> getAllUserStatesByStatus(String userId,Status status);

    List<UserTaskState> getAllUserStatesBySchemaAndStatus(String userId, String schemaId, Status status);
}
