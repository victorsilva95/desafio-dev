package com.bycoders.challenge.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record CNABRecord(
        String id,
        Integer type,
        LocalDate date,
        BigDecimal value,
        String cpf,
        String cardNumber,
        LocalTime hour,
        String storeRepresentativeName,
        String storeName

) {
}
