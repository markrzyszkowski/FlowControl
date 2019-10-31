package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {

    private Long id;

    @NotEmpty(message = "{message.task.name.empty}")
    private String name;

    @Future(message = "{message.date.future}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dueDate;

    private String description;

    private boolean assignToMe;
}
