package com.devsu.bancarioapi.models;

import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import com.devsu.bancarioapi.enums.MovementType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "movements")
public class Movement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movement_id", nullable = false)
    private Long movementId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(name = "movement_type", nullable = false)
    MovementType movementType;

    @Column(name = "initial_balance", nullable = false)
    private Double initialBalance;

    @Column(name = "value", nullable = false)
    private Double value;

    @Column(name = "balance", nullable = false)
    private Double balance;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Timestamp date;

}
