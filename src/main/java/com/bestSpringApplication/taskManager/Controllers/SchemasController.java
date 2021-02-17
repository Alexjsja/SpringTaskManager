package com.bestSpringApplication.taskManager.Controllers;


import com.bestSpringApplication.taskManager.handlers.exceptions.ContentNotFoundException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalFileFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.IllegalXmlFormatException;
import com.bestSpringApplication.taskManager.handlers.exceptions.ServerException;
import com.bestSpringApplication.taskManager.models.study.interfaces.StudySchema;
import com.bestSpringApplication.taskManager.models.study.interfaces.Task;
import com.bestSpringApplication.taskManager.servises.MasterSchemasService;
import com.bestSpringApplication.taskManager.servises.StudentSchemasService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jdom2.JDOMException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/schemas")
@CrossOrigin
@Slf4j
@RequiredArgsConstructor
public class SchemasController {

    private final String MASTER_SCHEMAS_MAPPING=                "/master";
    private final String MASTER_FILES_MAPPING =                 "/master/files";
    private final String MASTER_FILES_ADD_MAPPING =             "/master/files/add";
    private final String MASTER_SCHEMA_BY_KEY_MAPPING =         "/master/{schemaKey}";
    private final String ADD_MASTER_SCHEMA_TO_STUDENT_MAPPING = "/master/{schemaKey}/addTo/{studentId}";


    @NonNull private final MasterSchemasService masterSchemasService;
    @NonNull private final StudentSchemasService studentSchemasService;

    private static final Set<String> confirmedFileTypes =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "xml","mrp","txt")));

    @GetMapping(MASTER_SCHEMA_BY_KEY_MAPPING)
    public StudySchema masterSchemaByKey(@PathVariable String schemaKey){
        return masterSchemasService.schemaByKey(schemaKey)
                .orElseThrow(()->
                        new ContentNotFoundException(MASTER_SCHEMA_BY_KEY_MAPPING,"Курс не найден"));
    }


    // TODO: 2/15/2021
    @GetMapping(ADD_MASTER_SCHEMA_TO_STUDENT_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public void addSchemaToStudent(@PathVariable String schemaKey, @PathVariable String studentId){
//        StudySchema masterSchema = getMasterSchemaById(schemaKey);
//
//        if(!userService.existsUserById(studentId))throw new ContentNotFoundException("Студент не найден");
    }

    @PostMapping(MASTER_FILES_ADD_MAPPING)
    @ResponseStatus(HttpStatus.OK)
    public void newSchema(@RequestParam("file") MultipartFile file){
        try {
            String[] fileNameAndType = Objects.requireNonNull(file.getOriginalFilename()).split("\\.", 2);
            log.trace("Receive file:{}",file.getOriginalFilename());
            if (confirmedFileTypes.contains(fileNameAndType[1])){
                masterSchemasService.putAndSaveFile(file);
            }else {
                log.warn("unsupported file type sent,file:{}",file.getOriginalFilename());
                throw new IllegalFileFormatException(MASTER_FILES_ADD_MAPPING,
                        String.format("файл с расширением %s не поддерживается",fileNameAndType[1]));
            }
        }catch (JDOMException ex){
            log.error("error with XML parse:{} file:{}",ex.getLocalizedMessage(),file.getOriginalFilename());
            throw new IllegalXmlFormatException(MASTER_FILES_ADD_MAPPING,
                    "загрузка файла не удалась,проверьте структуру своего XML файла");
        } catch (IOException e) {
            log.error("unknown io exception = {}",e.getMessage());
            throw new ServerException(MASTER_FILES_ADD_MAPPING,
                    "Ошибка при загрузке файла,пожалуйста,повторите позже");
        }
    }

    @GetMapping(MASTER_SCHEMAS_MAPPING)
    public List<Task> masterSchemasOverview(){
        return masterSchemasService.schemasRootTasks();
    }

    @GetMapping(MASTER_FILES_MAPPING)
    public List<String> schemasFileList() {
        return masterSchemasService.schemasFileList();
    }
}

