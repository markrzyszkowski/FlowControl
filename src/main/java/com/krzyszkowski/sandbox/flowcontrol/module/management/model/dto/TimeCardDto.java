package com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TimeCardDto {

    private Long id;

    @NotNull(message = "{message.date.null}")
    @Past(message = "{message.date.past}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @Min(value = 1, message = "{message.hours.minimum}")
    @Max(value = 8, message = "{message.hours.maximum}")
    private int hours;

    private int overtime;

    private boolean holiday;
}
