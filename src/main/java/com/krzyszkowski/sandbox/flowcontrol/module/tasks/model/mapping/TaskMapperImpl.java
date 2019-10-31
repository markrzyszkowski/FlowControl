package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.Task;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.TaskState;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDto;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public Task fromDto(TaskDto taskDto, User self) {
        var task = new Task();

        task.setName(taskDto.getName());
        task.setState(taskDto.isAssignToMe() ? TaskState.ASSIGNED : TaskState.NEW);
        task.setCreatedBy(self);
        task.setAssignedUser(taskDto.isAssignToMe() ? self : null);
        task.setAddedDate(new Date());
        task.setDueDate(taskDto.getDueDate());
        task.setDescription(taskDto.getDescription());

        return task;
    }

    @Override
    public TaskDto toDto(Task task, String self) {
        var assignToMe = task.getAssignedUser() != null && task.getAssignedUser().getUsername().equals(self);

        return new TaskDto(task.getId(),
                           task.getName(),
                           task.getDueDate(),
                           task.getDescription(),
                           assignToMe);
    }

    @Override
    public TaskDisplayDto toDisplayDto(Task task) {
        var assignee = task.getAssignedUser() != null ? task.getAssignedUser().getUsername() : null;

        return new TaskDisplayDto(task.getId(),
                                  task.getName(),
                                  task.getState().toString(),
                                  task.getCreatedBy().getUsername(),
                                  assignee,
                                  task.getAddedDate(),
                                  task.getDueDate(),
                                  task.getDescription(),
                                  task.getLink());
    }

    @Override
    public Task update(Task task, TaskDto taskDto, User self) {
        task.setName(taskDto.getName());
        task.setDescription(taskDto.getDescription());
        task.setDueDate(taskDto.getDueDate());

        var assignedUser = task.getAssignedUser();
        if (assignedUser != null) {
            if (assignedUser.equals(self) && !taskDto.isAssignToMe()) {
                task.setAssignedUser(null);
            } else if (taskDto.isAssignToMe()) {
                task.setAssignedUser(self);
            }
        } else if (taskDto.isAssignToMe()) {
            task.setAssignedUser(self);
        }

        if (task.getState() == TaskState.NEW && taskDto.isAssignToMe()) {
            task.setState(TaskState.ASSIGNED);
        } else if (assignedUser != null && assignedUser.equals(self) && !taskDto.isAssignToMe()) {
            task.setState(TaskState.NEW);
        }

        return task;
    }
}
