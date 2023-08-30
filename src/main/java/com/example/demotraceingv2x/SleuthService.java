package com.example.demotraceingv2x;

import brave.Span;
import brave.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SleuthService {

    private final Tracer tracer;

    @Async
    public void asyncMethod() throws InterruptedException {
        log.info("Start Async Method");
        Thread.sleep(1000L);
        log.info("End Async Method");
    }

    public void doSomeWorkNewSpan() {
        log.info("I'm in the original span");
        Span newSpan = tracer.nextSpan().name("newSpan").start();
        try (Tracer.SpanInScope ws = tracer.withSpanInScope(newSpan.start())) {
            Thread.sleep(1000L);
            log.info("I'm in the new span doing some cool work that needs its own span");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            newSpan.finish();
        }

        log.info("I'm in the original span");

    }
}
