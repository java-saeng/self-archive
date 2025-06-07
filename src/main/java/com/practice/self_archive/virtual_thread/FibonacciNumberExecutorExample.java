package com.practice.self_archive.virtual_thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FibonacciNumberExecutorExample {

    private static final Map<Integer, Long> cache = new ConcurrentHashMap<>();

    static {
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public static void main(String[] args) {
        try (var pool = Executors.newFixedThreadPool(100)) {
            Future<Long> future = pool.submit(() -> getFibonacci(20, pool));
            Long l = future.get();
            System.out.println("Fibonacci number is: " + l);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static long getFibonacci(int i, ExecutorService pool) {
        if (cache.containsKey(i)) {
            return cache.get(i);
        }

        Future<Long> future1 = pool.submit(() -> getFibonacci(i - 1, pool));
        Future<Long> future2 = pool.submit(() -> getFibonacci(i - 2, pool));

        try {
            long l1 = future1.get();
            long l2 = future2.get();
            long result = l1 + l2;
            cache.put(i, result);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
