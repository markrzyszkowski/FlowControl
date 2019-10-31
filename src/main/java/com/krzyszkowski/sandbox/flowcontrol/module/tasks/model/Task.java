package com.krzyszkowski.sandbox.flowcontrol.module.tasks.model;

import com.krzyszkowski.sandbox.flowcontrol.core.model.User;
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
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private TaskState state;

    @OneToOne
    @JoinColumn(name = "createdBy", nullable = false)
    private User createdBy;

    @OneToOne
    @JoinColumn(name = "assignedUserId")
    private User assignedUser;

    @Column(nullable = false)
    @Temporal(value = TemporalType.DATE)
    private Date addedDate;

    @Temporal(value = TemporalType.DATE)
    private Date dueDate;

    private String description;

    private String link;
}
