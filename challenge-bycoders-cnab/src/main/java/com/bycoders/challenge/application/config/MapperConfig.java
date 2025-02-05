package com.bycoders.challenge.application.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

import static com.bycoders.challenge.commons.Constants.DATE_TIME_FORMAT_PATTERN;
import static com.bycoders.challenge.commons.Constants.TIME_ZONE_OFFSET;

@Configuration
public class MapperConfig {
    private static final int MIN_NANO_OF_SECOND = 0;
    private static final int MAX_NANO_OF_SECOND = 9;

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                .setSerializationInclusion(JsonInclude.Include.ALWAYS)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(getDateTimeModule());
    }

    private JavaTimeModule getDateTimeModule() {
        var formatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN))
                .appendFraction(ChronoField.NANO_OF_SECOND, MIN_NANO_OF_SECOND, MAX_NANO_OF_SECOND, true)
                .appendOptional(DateTimeFormatter.ofPattern(TIME_ZONE_OFFSET))
                .toFormatter();

        var timeFormatter = new DateTimeFormatterBuilder()
                .appendOptional(DateTimeFormatter.ISO_LOCAL_TIME)
                .toFormatter();

        var dateTimeModule = new JavaTimeModule();
        dateTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
        dateTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));
        dateTimeModule.addSerializer(ZonedDateTime.class, new ZonedDateTimeSerializer(formatter));
        dateTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        dateTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        dateTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        dateTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        return dateTimeModule;
    }
}
