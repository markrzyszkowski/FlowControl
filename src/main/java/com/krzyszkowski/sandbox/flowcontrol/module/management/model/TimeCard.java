package com.krzyszkowski.sandbox.flowcontrol.module.management.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
@Entity
@Table(name = "time_cards")
public class TimeCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "employeeId", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date date;

    @Column(nullable = false)
    private int hours;

    private int overtime;

    @Column(nullable = false)
    private boolean weekend;

    @Column(nullable = false)
    private boolean holiday;
}
