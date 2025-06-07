package com.practice.self_archive.virtual_thread;

import java.time.Duration;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.IntStream;

public class VirtualThreadBenchmarkTest {

    public static void main(String[] args) {
        benchmark("Virtual Threads", Executors.newVirtualThreadPerTaskExecutor());
        benchmark("Fixed ThreadPool (100)", Executors.newFixedThreadPool(100));
        benchmark("Fixed ThreadPool (500)", Executors.newFixedThreadPool(500));
        benchmark("Fixed ThreadPool (1000)", Executors.newFixedThreadPool(1000));
    }

    static void benchmark(String title, ExecutorService executor) {
        long start = System.currentTimeMillis();
        AtomicLong completedTasks = new AtomicLong();

        try (executor){
            IntStream.range(0, 10000).forEach(i -> executor.submit(() -> {
                try {
                    Thread.sleep(Duration.ofMillis(500));
                    completedTasks.incrementAndGet();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }));
        }

        long end = System.currentTimeMillis();
        long interval = end - start;

        double throughput = (double) completedTasks.get() / interval * 1000;
        System.out.println(title + " Time: " + interval + "ms, Throughput: " + throughput + "/s");
    }
}
