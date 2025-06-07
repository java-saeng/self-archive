package com.practice.self_archive.virtual_thread;

import java.util.List;
import java.util.stream.IntStream;

public class SynchronizedPinningExample {

    public static void main(String[] args) {
        List<Thread> threads = IntStream.range(0, 10)
            .mapToObj(i -> Thread.ofVirtual().unstarted(() -> {
                if (i == 0) {
                    System.out.println(Thread.currentThread());
                }
                synchronized (SynchronizedPinningExample.class) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                    }
                }
                if (i == 0) {
                    System.out.println(Thread.currentThread());
                }
            })).toList();

        threads.forEach(Thread::start);

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        });
    }
}
