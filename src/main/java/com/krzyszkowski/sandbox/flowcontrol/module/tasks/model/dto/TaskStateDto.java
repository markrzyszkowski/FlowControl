package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto;

import com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.validation.ValidState;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskStateDto {

    private Long id;

    @NotEmpty(message = "{message.task.state.empty}")
    @ValidState
    private String state;
}
