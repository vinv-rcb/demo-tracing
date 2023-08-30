package com.example.demotraceingv2x;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulingService {

    private final SleuthService sleuthService;


    @Scheduled(fixedDelay = 30000)
    public void scheduledWork() throws InterruptedException {
        log.info("Start some work from the scheduled task");
        sleuthService.asyncMethod();
        log.info("End work from scheduled task");
    }
}
