package com.bycoders.challenge.application.dto;

import java.math.BigDecimal;

public record CNABRecordDto(
        Integer type,
        String date,
        BigDecimal value,
        String cpf,
        String cardNumber,
        String hour,
        String storeRepresentativeName,
        String storeName

) {
}
