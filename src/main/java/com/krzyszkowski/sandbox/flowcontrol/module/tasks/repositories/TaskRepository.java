package com.krzyszkowski.sandbox.flowcontrol.module.tasks.repositories;

import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.Task;
import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.TaskState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    Page<Task> findByAssignedUserId(Long assignedUserId, Pageable pageable);

    Page<Task> findByStateNot(TaskState state, Pageable pageable);

    Page<Task> findByAssignedUserIdAndStateNot(Long assignedUserId, TaskState state, Pageable pageable);
}
