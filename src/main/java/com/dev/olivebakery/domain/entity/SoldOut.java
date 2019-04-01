package com.dev.olivebakery.domain.entity;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Table(name = "sold_out_tbl")
public class SoldOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDate date;

    @OneToOne
    @JoinColumn(name = "bread_id")
    private Bread bread;
}
