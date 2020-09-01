package com.github.pedrobacchini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InvoiceInput {

    private Long number;
    private LocalDate date;
    private BigDecimal value;
    private Long takerId;
    private Long providerId;
}
