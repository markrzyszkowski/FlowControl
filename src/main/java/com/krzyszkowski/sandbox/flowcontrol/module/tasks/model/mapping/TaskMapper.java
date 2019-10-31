package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.Task;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDto;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto, User self);

    TaskDto toDto(Task task, String self);

    TaskDisplayDto toDisplayDto(Task task);

    Task update(Task task, TaskDto taskDto, User self);
}
