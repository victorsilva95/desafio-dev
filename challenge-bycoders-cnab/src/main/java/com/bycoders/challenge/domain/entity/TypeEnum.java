package com.bycoders.challenge.domain.entity;

import java.math.BigDecimal;
import java.util.Arrays;

public enum TypeEnum {
    DEBITO(1) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE;
        }
    }, BOLETO(2) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE.negate();
        }
    }, FINANCIAMENTO(3) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE.negate();
        }
    },
    CREDITO(4) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE;
        }
    }, RECEBIMENTO_EMPRESTIMO(5) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE;
        }
    },
    VENDAS(6) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE;
        }
    }, RECEBIMENTO_TED(7) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE;
        }
    }, RECEBIMENTO_DOC(8) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE;
        }
    },
    ALUGUEL(9) {
        @Override
        public BigDecimal getSignal() {
            return BigDecimal.ONE.negate();
        }
    };

    private final int value;

    TypeEnum(int value) {
        this.value = value;
    }

    public static TypeEnum getByValue(int value) {
        return Arrays.stream(values())
                .filter(typeEnum -> typeEnum.value == value)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Type %s is invalid", value)));
    }

    public abstract BigDecimal getSignal();
}
