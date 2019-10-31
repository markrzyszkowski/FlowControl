package com.krzyszkowski.sandbox.flowcontrol.module.management.services;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.TimeReport;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDto;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface TimeCardService {

    Page<TimeCardDisplayDto> getPagedTimeCards(String username, int page, int size);

    Page<TimeCardDisplayDto> getPagedTimeCards(Long employeeId, int page, int size);

    void addTimeCard(TimeCardDto timeCardDto, String username);

    void deleteTimeCard(Long id);

    TimeReport getReport(Map<String, String> reportData);
}
