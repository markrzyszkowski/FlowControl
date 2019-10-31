package com.krzyszkowski.sandbox.flowcontrol.module.management.model;

import lombok.Value;

@Value
public class TimeReport {

    private Long employeeId;

    private String name;

    private String surname;

    private String period;

    private float basePay;

    private int hours;

    private int overtime;

    private float overtimeRatio;

    private int weekends;

    private float weekendsRatio;

    private int holidays;

    private float holidaysRatio;

    private float calculatedSalary;
}
