package com.krzyszkowski.sandbox.flowcontrol.module.tasks.services;

import com.krzyszkowski.sandbox.flowcontrol.core.repositories.UserRepository;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDto;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.notifications.Notifications;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.services.NotificationService;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.TaskState;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto.TaskStateDto;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.mapping.TaskMapper;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.repositories.TaskRepository;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final NotificationService notificationService;
    private final TaskMapper taskMapper;

    public TaskServiceImpl(TaskRepository taskRepository,
                           UserRepository userRepository,
                           StorageService storageService,
                           NotificationService notificationService,
                           TaskMapper taskMapper) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.notificationService = notificationService;
        this.taskMapper = taskMapper;
    }

    @Override
    @Transactional
    public TaskDto getTask(Long id, String self) {
        return taskRepository.findById(id)
                             .map(document -> taskMapper.toDto(document, self))
                             .orElseThrow();
    }

    @Override
    @Transactional
    public Resource getTaskFile(String filename) {
        return storageService.loadAsResource(filename);
    }

    @Override
    @Transactional
    public TaskStateDto getTaskState(Long id) {
        return taskRepository.findById(id)
                             .map(document -> new TaskStateDto(id, document.getState().name()))
                             .orElseThrow();
    }

    @Override
    @Transactional
    public void updateTaskState(TaskStateDto taskStateDto, String username) {
        var task = taskRepository.findById(taskStateDto.getId()).orElseThrow();
        if (taskStateDto.getState().equals(TaskState.NEW.name())) {
            task.setAssignedUser(null);

            notificationService.addNotifications(userRepository.findAll()
                                                               .stream()
                                                               .map(user -> new NotificationDto(user,
                                                                                                Notifications.Tasks.TASK_UNASSIGNED))
                                                               .collect(Collectors.toList()));
        }
        task.setState(TaskState.valueOf(taskStateDto.getState()));
        task.setAssignedUser(userRepository.findByUsername(username).orElseThrow());
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public Page<TaskDisplayDto> getPagedTasks(String username,
                                              int page,
                                              int size,
                                              boolean completed,
                                              boolean assignedToSelf) {
        var selfId = userRepository.findByUsername(username).orElseThrow().getId();
        var pageRequest = PageRequest.of(page, size, Sort.by(List.of(Sort.Order.desc("state"),
                                                                     Sort.Order.asc("dueDate"),
                                                                     Sort.Order.asc("name"))));

        if (completed && assignedToSelf) {
            return taskRepository.findByAssignedUserId(selfId, pageRequest).map(taskMapper::toDisplayDto);
        }
        if (completed) {
            return taskRepository.findAll(pageRequest).map(taskMapper::toDisplayDto);
        }
        if (assignedToSelf) {
            return taskRepository.findByAssignedUserIdAndStateNot(selfId, TaskState.COMPLETED, pageRequest)
                                 .map(taskMapper::toDisplayDto);
        }
        return taskRepository.findByStateNot(TaskState.COMPLETED, pageRequest).map(taskMapper::toDisplayDto);
    }

    @Override
    @Transactional
    public void addTask(TaskDto taskDto, String username) {
        var self = userRepository.findByUsername(username).orElseThrow();

        taskRepository.save(taskMapper.fromDto(taskDto, self));

        if (!taskDto.isAssignToMe()) {
            notificationService.addNotifications(userRepository.findAll()
                                                               .stream()
                                                               .map(user -> new NotificationDto(user,
                                                                                                Notifications.Tasks.NEW_TASK))
                                                               .collect(Collectors.toList()));
        }
    }

    @Override
    @Transactional
    public void addTaskFile(MultipartFile file, Long id, String name) {
        name += getExtension(file.getOriginalFilename());
        var document = taskRepository.findById(id).orElseThrow();
        if (document.getLink() != null) {
            storageService.delete(document.getLink());
        }
        document.setLink(name);
        storageService.store(file, name);
        taskRepository.save(document);
    }

    @Override
    @Transactional
    public void updateTask(TaskDto taskDto, String username) {
        var self = userRepository.findByUsername(username).orElseThrow();

        var document = taskRepository.findById(taskDto.getId()).orElseThrow();

        taskRepository.save(taskMapper.update(document, taskDto, self));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        var document = taskRepository.findById(id).orElseThrow();
        if (document.getLink() != null) {
            storageService.delete(document.getLink());
        }
        taskRepository.deleteById(id);
    }

    private static String getExtension(String filename) {
        return Optional.ofNullable(filename)
                       .filter(f -> f.contains("."))
                       .map(f -> f.substring(filename.lastIndexOf(".")))
                       .orElse("");
    }
}
