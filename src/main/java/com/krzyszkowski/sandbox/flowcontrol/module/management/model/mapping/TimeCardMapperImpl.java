package com.krzyszkowski.sandbox.flowcontrol.module.management.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.core.util.DateUtil;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.TimeCard;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class TimeCardMapperImpl implements TimeCardMapper {

    @Override
    public TimeCard fromDto(TimeCardDto timeCardDto) {
        var date = LocalDate.ofInstant(timeCardDto.getDate().toInstant(), ZoneId.systemDefault());

        var timeCard = new TimeCard();
        timeCard.setDate(timeCardDto.getDate());
        timeCard.setHours(timeCardDto.getHours());
        timeCard.setOvertime(timeCardDto.getOvertime());
        timeCard.setWeekend(DateUtil.isWeekend(date.getDayOfWeek()));
        timeCard.setHoliday(timeCardDto.isHoliday());
        return timeCard;
    }

    @Override
    public TimeCardDisplayDto toDisplayDto(TimeCard timeCard) {
        return new TimeCardDisplayDto(timeCard.getId(),
                                      timeCard.getDate(),
                                      timeCard.getHours(),
                                      timeCard.getOvertime(),
                                      timeCard.isWeekend(),
                                      timeCard.isHoliday());
    }
}
