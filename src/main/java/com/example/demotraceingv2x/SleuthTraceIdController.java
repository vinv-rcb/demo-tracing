package com.example.demotraceingv2x;

import brave.Span;
import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

@RestController
@Slf4j
@RequiredArgsConstructor
public class SleuthTraceIdController {

    private final Tracer tracer;
    private final SleuthService sleuthService;
    private final Executor executor;


    @GetMapping("/traceid")
    public String getSleuthTraceId() throws ExecutionException, InterruptedException {
        Span span = tracer.currentSpan();
        if (span != null) {
            log.info("Trace ID {}", span.context().traceIdString());
            log.info("Span ID {}", span.context().spanIdString());
        }

        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CompletableFuture<Void> task = CompletableFuture.runAsync(() -> {
                log.info("Start to sleep for 2 seconds");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("Wake up! and finish your job!");
            }, executor);
            tasks.add(task);
        }
        for (CompletableFuture<Void> task : tasks) {
            task.get();
        }

        return "Hello from Sleuth";
    }

    @GetMapping("/async")
    public String helloSleuthAsync() throws InterruptedException {
        log.info("Before Async Method Call");
        sleuthService.asyncMethod();
        log.info("After Async Method Call");

        return "success";
    }

    @GetMapping("/new-span")
    public String helloSleuthNewSpan() {
        log.info("New Span");
        sleuthService.doSomeWorkNewSpan();
        return "success";
    }
}
