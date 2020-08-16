package com.github.pedrobacchini.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class InvoiceDTO {

    private Long number;
    private LocalDate date;
    private BigDecimal value;
    private Long takerId;
    private Long providerId;
}
