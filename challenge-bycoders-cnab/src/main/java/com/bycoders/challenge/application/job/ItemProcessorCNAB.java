package com.bycoders.challenge.application.job;

import com.bycoders.challenge.application.dto.CNABRecordDto;
import com.bycoders.challenge.commons.LocalDateUtils;
import com.bycoders.challenge.commons.LocalTimeUtils;
import com.bycoders.challenge.domain.entity.CNABRecord;
import com.bycoders.challenge.domain.entity.TypeEnum;
import io.azam.ulidj.ULID;
import org.springframework.batch.item.ItemProcessor;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ItemProcessorCNAB implements ItemProcessor<CNABRecordDto, CNABRecord> {


    @Override
    public CNABRecord process(CNABRecordDto item) {
        return new CNABRecord(
                ULID.random(),
                item.type(), LocalDateUtils.getByString(item.date()),
                normalizeValue(item.type(), item.value()),
                item.cpf(), item.cardNumber(), LocalTimeUtils.getByString(item.hour()),
                item.storeRepresentativeName(), item.storeName()
        );
    }

    private BigDecimal normalizeValue(Integer type, BigDecimal value) {
        var typeEnum = TypeEnum.getByValue(type);
        return value.divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN)
                .multiply(typeEnum.getSignal());
    }
}
