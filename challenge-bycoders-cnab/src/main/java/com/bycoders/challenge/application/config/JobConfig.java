package com.bycoders.challenge.application.config;

import com.bycoders.challenge.application.dto.CNABRecordDto;
import com.bycoders.challenge.application.job.ItemProcessorCNAB;
import com.bycoders.challenge.domain.entity.CNABRecord;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class JobConfig {
    private static final String INSERT_CNAB_RECORD = """
                INSERT INTO cnab_record (
                id, type, date, value, cpf, card_number,
                hour, store_representative_name, store_name
                ) VALUES (
                :id, :type, :date, :value, :cpf, :cardNumber,
                :hour, :storeRepresentativeName, :storeName
                )
            """;

    private final PlatformTransactionManager platformTransactionManager;
    private final JobRepository jobRepository;

    public JobConfig(PlatformTransactionManager platformTransactionManager, JobRepository jobRepository) {
        this.platformTransactionManager = platformTransactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    public ItemProcessor<CNABRecordDto, CNABRecord> processor() {
        return new ItemProcessorCNAB();
    }

    @Bean
    public Job job(Step step) {
        return new JobBuilder("cnab-job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public FlatFileItemReader<CNABRecordDto> reader() {
        return new FlatFileItemReaderBuilder<CNABRecordDto>()
                .name("cnab-reader")
                .resource(new FileSystemResource("/Users/victor.e.trd/Documents/pessoal/desafio-dev/CNAB.txt"))
                .fixedLength()
                .columns(
                        new Range(1, 1), new Range(2, 9), new Range(10, 19),
                        new Range(20, 30), new Range(31, 42), new Range(43, 48),
                        new Range(49, 62), new Range(63, 80)
                )
                .names("type", "date", "value", "cpf", "cardNumber", "hour",
                        "storeRepresentativeName", "storeName")
                .targetType(CNABRecordDto.class)
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<CNABRecord> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<CNABRecord>()
                .dataSource(dataSource)
                .sql(INSERT_CNAB_RECORD)
                .beanMapped()
                .build();
    }

    @Bean
    public Step step(ItemReader<CNABRecordDto> reader,
              ItemWriter<CNABRecord> writer) {
        return new StepBuilder("cnab-step", jobRepository)
                .<CNABRecordDto, CNABRecord>chunk(100, platformTransactionManager)
                .reader(reader)
                .processor(processor())
                .writer(writer)
                .build();
    }
}
