package com.musicplaylist.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final JobLauncher jobLauncher;
    private final Job importSongsJob;

    public BatchController(
            JobLauncher jobLauncher,
            Job importSongsJob) {
        this.jobLauncher = jobLauncher;
        this.importSongsJob = importSongsJob;
    }

    @PostMapping("/import-songs")
    public ResponseEntity<String> importSongs() throws Exception {

        JobParameters parameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();

        JobExecution execution = jobLauncher.run(
                importSongsJob, parameters);

        return ResponseEntity.ok(
                "Import started. Status: " + execution.getStatus());
    }
}