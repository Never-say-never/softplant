package com.softwareplant.api.module.report.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "report",
    indexes = { @Index(name = "hash_idx",  columnList="hash", unique = true) })
public class SwapiReport {

    @Id
    @JsonIgnore
    @GeneratedValue
    private Long reportId;
    private String hash;
    @Column(columnDefinition = "TEXT")
    private String report;
}
