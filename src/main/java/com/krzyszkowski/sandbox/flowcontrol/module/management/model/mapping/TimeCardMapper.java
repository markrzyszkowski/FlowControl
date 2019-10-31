package com.krzyszkowski.sandbox.flowcontrol.module.management.model.mapping;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.TimeCard;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDto;

public interface TimeCardMapper {

    TimeCard fromDto(TimeCardDto timeCardDto);

    TimeCardDisplayDto toDisplayDto(TimeCard timeCard);
}
