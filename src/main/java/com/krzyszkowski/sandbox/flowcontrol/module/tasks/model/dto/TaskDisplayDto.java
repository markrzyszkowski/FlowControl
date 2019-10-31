package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model.dto;

import lombok.Value;

import java.util.Date;

@Value
public class TaskDisplayDto {

    private Long id;

    private String name;

    private String state;

    private String author;

    private String assignee;

    private Date created;

    private Date due;

    private String description;

    private String link;
}
