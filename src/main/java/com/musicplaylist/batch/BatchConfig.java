package com.musicplaylist.batch;

import com.musicplaylist.model.Song;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

@Configuration
public class BatchConfig {

    @Bean
    public FlatFileItemReader<Song> songReader() {
        return new FlatFileItemReaderBuilder<Song>()
                .name("songReader")
                .resource(new ClassPathResource("data/songs.csv"))
                .delimited()
                .names("title", "artist", "album", "genre", "duration")
                .targetType(Song.class)
                .linesToSkip(1)
                .build();
    }

    @Bean
    public JpaItemWriter<Song> songWriter(EntityManagerFactory entityManagerFactory) {

        return new JpaItemWriterBuilder<Song>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    @Bean
    public Step importSongsStep(
            JobRepository jobRepository,
            PlatformTransactionManager transactionManager,
            FlatFileItemReader<Song> songReader,
            SongItemProcessor songProcessor,
            JpaItemWriter<Song> songWriter) {

        return new StepBuilder("importSongsStep", jobRepository)
                .<Song, Song>chunk(10, transactionManager)
                .reader(songReader)
                .processor(songProcessor)
                .writer(songWriter)
                .build();
    }

    @Bean
    public Job importSongsJob(
            JobRepository jobRepository,
            Step importSongsStep) {

        return new JobBuilder("importSongsJob", jobRepository)
                .start(importSongsStep)
                .build();
    }
}