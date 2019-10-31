package com.krzyszkowski.sandbox.flowcontrol.module.management.services;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.TimeCard;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.TimeReport;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDisplayDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.dto.TimeCardDto;
import com.krzyszkowski.sandbox.flowcontrol.module.management.model.mapping.TimeCardMapper;
import com.krzyszkowski.sandbox.flowcontrol.module.management.repositories.EmployeeRepository;
import com.krzyszkowski.sandbox.flowcontrol.module.management.repositories.TimeCardRepository;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.dto.NotificationDto;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.model.notifications.Notifications;
import com.krzyszkowski.sandbox.flowcontrol.module.notifications.services.NotificationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class TimeCardServiceImpl implements TimeCardService {

    private final EmployeeRepository employeeRepository;
    private final TimeCardRepository timeCardRepository;
    private final EmployeeService employeeService;
    private final NotificationService notificationService;
    private final TimeCardMapper timeCardMapper;

    public TimeCardServiceImpl(EmployeeRepository employeeRepository,
                               TimeCardRepository timeCardRepository,
                               EmployeeService employeeService,
                               NotificationService notificationService,
                               TimeCardMapper timeCardMapper) {
        this.employeeRepository = employeeRepository;
        this.timeCardRepository = timeCardRepository;
        this.employeeService = employeeService;
        this.notificationService = notificationService;
        this.timeCardMapper = timeCardMapper;
    }

    @Override
    @Transactional
    public Page<TimeCardDisplayDto> getPagedTimeCards(String username, int page, int size) {
        var self = employeeService.getEmployee(username);

        return timeCardRepository.findByEmployeeId(self.getId(), PageRequest.of(page,
                                                                                size,
                                                                                Sort.by(List.of(Sort.Order.desc("date")))))
                                 .map(timeCardMapper::toDisplayDto);
    }

    @Override
    @Transactional
    public Page<TimeCardDisplayDto> getPagedTimeCards(Long employeeId, int page, int size) {
        var self = employeeRepository.findById(employeeId).orElseThrow();

        return timeCardRepository.findByEmployeeId(self.getId(), PageRequest.of(page,
                                                                                size,
                                                                                Sort.by(List.of(Sort.Order.desc("date")))))
                                 .map(timeCardMapper::toDisplayDto);
    }

    @Override
    @Transactional
    public void addTimeCard(TimeCardDto timeCardDto, String username) {
        var self = employeeService.getEmployee(username);

        var timeCard = timeCardMapper.fromDto(timeCardDto);
        timeCard.setEmployee(self);
        timeCardRepository.save(timeCard);

        notificationService.addNotifications(List.of(
                new NotificationDto(self.getManager(), String.format(Notifications.Time.NEW_TIME_CARD,
                                                                     self.getUser().getUsername()))));
    }

    @Override
    @Transactional
    public void deleteTimeCard(Long id) {
        timeCardRepository.deleteById(id);
    }

    @Override
    @Transactional
    public TimeReport getReport(Map<String, String> reportData) {
        var employee = employeeRepository.findById(Long.valueOf(reportData.get("id"))).orElseThrow();

        var format = new SimpleDateFormat("yyyy-MM-dd");
        Date from, to;
        try {
            from = format.parse(reportData.get("from"));
            to = format.parse(reportData.get("to"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        var time = timeCardRepository.findByEmployeeIdAndDateBetween(employee.getId(), from, to);

        var base = Float.parseFloat(reportData.get("base"));
        var hours = getEmployeeHours(time);
        var overtime = getEmployeeOvertime(time);
        var overtimeRatio = Float.parseFloat(reportData.get("overtime"));
        var weekends = getEmployeeWeekends(time);
        var weekendsRatio = Float.parseFloat(reportData.get("weekend"));
        var holidays = getEmployeeHolidays(time);
        var holidaysRatio = Float.parseFloat(reportData.get("holiday"));

        var salary = calculateSalary(base,
                                     hours,
                                     overtime,
                                     overtimeRatio,
                                     weekends,
                                     weekendsRatio,
                                     holidays,
                                     holidaysRatio);

        var period = reportData.get("from").replace("-", "/")
                     + " - "
                     + reportData.get("to").replace("-", "/");

        return new TimeReport(employee.getId(),
                              employee.getName(),
                              employee.getSurname(),
                              period,
                              base,
                              hours,
                              overtime,
                              overtimeRatio,
                              weekends,
                              weekendsRatio,
                              holidays,
                              holidaysRatio,
                              salary);
    }

    private int getEmployeeHours(List<TimeCard> time) {
        return time.stream()
                   .mapToInt(t -> t.isWeekend() || t.isHoliday() ? 0 : t.getHours() - t.getOvertime())
                   .sum();
    }

    private int getEmployeeOvertime(List<TimeCard> time) {
        return time.stream()
                   .mapToInt(t -> t.isWeekend() || t.isHoliday() ? 0 : t.getOvertime())
                   .sum();
    }

    private int getEmployeeWeekends(List<TimeCard> time) {
        return time.stream()
                   .mapToInt(t -> t.isWeekend() ? t.getHours() : 0)
                   .sum();
    }

    private int getEmployeeHolidays(List<TimeCard> time) {
        return time.stream()
                   .mapToInt(t -> t.isHoliday() ? t.getHours() : 0)
                   .sum();
    }

    private float calculateSalary(float base,
                                  int hours,
                                  int overtime,
                                  float overtimeRatio,
                                  int weekends,
                                  float weekendsRatio,
                                  int holidays,
                                  float holidaysRatio) {

        return hours * base
               + overtime * overtimeRatio * base
               + weekends * weekendsRatio * base
               + holidays * holidaysRatio * base;
    }
}
