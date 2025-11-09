package com.build.currency_converter.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "conversion_history")

public class ConversionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String sourceCurrency;

    @Column(nullable = false)
    private String targetCurrency;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Double convertedAmount;

    @Column(nullable = false)
    private Double exchangeRate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime conversionDate;

    public ConversionHistory(User user, String sourceCurrency, String targetCurrency, Double amount, Double convertedAmount, Double exchangeRate, LocalDateTime conversionDate) {
        this.user = user;
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.amount = amount;
        this.convertedAmount = convertedAmount;
        this.exchangeRate = exchangeRate;
        this.conversionDate = conversionDate;
    }

    public ConversionHistory() {
    }
}
