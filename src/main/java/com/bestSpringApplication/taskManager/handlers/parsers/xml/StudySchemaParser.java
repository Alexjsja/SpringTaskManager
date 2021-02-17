package com.bestSpringApplication.taskManager.handlers.parsers.xml;

import com.bestSpringApplication.taskManager.handlers.TasksHandler;
import com.bestSpringApplication.taskManager.models.study.implementations.DependencyImpl;
import com.bestSpringApplication.taskManager.models.study.implementations.StudySchemaImpl;
import com.bestSpringApplication.taskManager.models.study.interfaces.Dependency;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class StudySchemaParser {

    @NonNull private final TaskParser taskParser;

    public StudySchema parseSchemaXml(Document mainDocument) throws JDOMException {
        Element rootElement = mainDocument.getRootElement();
        StudySchemaImpl studySchema = new StudySchemaImpl();

        log.trace("Start parse root element:\n{}",rootElement.getContent());

        Element fieldListElem = Optional.ofNullable(rootElement.getChild("task-field-list"))
                .orElseThrow(()-> new JDOMException("fieldListElem is empty!"));
        Element dependencyListElem = Optional.ofNullable(rootElement.getChild("task-dependency-list"))
                .orElseThrow(()-> new JDOMException("dependencyListElement is empty!"));
        Element taskElem = Optional.ofNullable(rootElement.getChild("task"))
                .orElseThrow(()-> new JDOMException("taskElement is empty!"));

        Map<String, String> fieldsMap = taskParser.fieldToMap(fieldListElem, "field", "no", "name");
        List<Dependency> taskDependenciesList = parseDependenciesList(dependencyListElem);
        List<Task> tasksList = taskParser.parseFromXml(taskElem);
        TasksHandler.addTaskFields(tasksList,fieldsMap);
        Map<String, Task> completedTasksMap = new HashMap<>();

        completedTasksMap.put("root",tasksList.remove(0)); // experimental

        tasksList.forEach(task -> completedTasksMap.put(task.getId(),task));

        studySchema.setDependencies(taskDependenciesList);
        studySchema.setTasksMap(completedTasksMap);

        log.trace("Return study schema = {}",studySchema);

        return studySchema;
    }

    private List<Dependency> parseDependenciesList(Element dependencyListElem) {
        log.trace("Received dependencies list xml element = {}",dependencyListElem.getContent());
        List<Element> DependencyElements = dependencyListElem.getChildren("task-dependency");
        return DependencyElements.stream().map(DependencyChild ->{
            String parent = DependencyChild.getChildText("task-predecessor-id");
            String child = DependencyChild.getChildText("task-successor-id");
            return new DependencyImpl(parent,child);
        }).collect(Collectors.toList());
    }

}
