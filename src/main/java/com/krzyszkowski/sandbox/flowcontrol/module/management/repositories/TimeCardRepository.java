package com.krzyszkowski.sandbox.flowcontrol.module.management.repositories;

import com.krzyszkowski.sandbox.flowcontrol.module.management.model.TimeCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TimeCardRepository extends JpaRepository<TimeCard, Long> {

    Page<TimeCard> findByEmployeeId(Long employeeId, Pageable pageable);

    List<TimeCard> findByEmployeeIdAndDateBetween(Long employeeId, Date from, Date to);
}
