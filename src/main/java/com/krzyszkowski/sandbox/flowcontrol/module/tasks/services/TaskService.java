package com.krzyszkowski.sandbox.flowcontrol.module.tasks.services;

import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskStateDto;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface TaskService {

    TaskDto getTask(Long id, String self);

    Resource getTaskFile(String filename);

    TaskStateDto getTaskState(Long id);

    void updateTaskState(TaskStateDto taskStateDto, String username);

    Page<TaskDisplayDto> getPagedTasks(String username, int page, int size, boolean completed, boolean assignedToSelf);

    void addTask(TaskDto taskDto, String username);

    void addTaskFile(MultipartFile file, Long id, String name);

    void updateTask(TaskDto taskDto, String username);

    void deleteTask(Long id);
}
