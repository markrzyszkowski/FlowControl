package com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto;

import lombok.Value;

import java.util.Date;

@Value
public class TimeCardDisplayDto {

    private Long id;

    private Date date;

    private int hours;

    private int overtime;

    private boolean weekend;

    private boolean holiday;
}
