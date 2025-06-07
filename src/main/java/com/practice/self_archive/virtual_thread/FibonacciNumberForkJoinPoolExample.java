package com.practice.self_archive.virtual_thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class FibonacciNumberForkJoinPoolExample {

    private static final Map<Integer, Long> cache = new ConcurrentHashMap<>();

    static {
        cache.put(0, 0L);
        cache.put(1, 1L);
    }

    public static void main(String[] args) {
        try (var pool = new ForkJoinPool()) {
            Long result = pool.invoke(new FibonacciTask(20));
            System.out.println("Fibonacci number is: " + result);
        }
    }

    static class FibonacciTask extends RecursiveTask<Long> {
        private final int n;

        public FibonacciTask(int n) {
            this.n = n;
        }

        @Override
        protected Long compute() {
            if (cache.containsKey(n)) {
                return cache.get(n);
            }

            FibonacciTask f1 = new FibonacciTask(n - 1);
            f1.fork();
            FibonacciTask f2 = new FibonacciTask(n - 2);
            long result = f2.compute() + f1.join();
            cache.put(n, result);
            return result;
        }
    }
}
