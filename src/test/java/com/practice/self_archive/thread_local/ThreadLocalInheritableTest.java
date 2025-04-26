package com.practice.self_archive.thread_local;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("ThreadLocal 상속 테스트")
class ThreadLocalInheritableTest {

    @Nested
    @DisplayName("일반 ThreadLocal 테스트")
    class RegularThreadLocalTests {

        @Test
        @DisplayName("일반 ThreadLocal은 일반 스레드에 상속되지 않아야 한다")
        void regularThreadLocalShouldNotBeInheritedByRegularThread() throws Exception {
            // Given: 부모 스레드에 ThreadLocal 값 설정
            final ThreadLocal<String> threadLocal = new ThreadLocal<>();
            threadLocal.set("부모 값");

            // When: 자식 일반 스레드에서 ThreadLocal 값 조회
            final String[] result = new String[1];
            Thread childThread = new Thread(() -> {
                result[0] = threadLocal.get();
            });
            childThread.start();
            childThread.join();

            // Then: ThreadLocal 값이 상속되지 않아야 함
            assertNull(result[0]);
        }

        @Test
        @DisplayName("일반 ThreadLocal은 가상 스레드에 상속되지 않아야 한다")
        void regularThreadLocalShouldNotBeInheritedByVirtualThread() throws Exception {
            // Given: 부모 스레드에 ThreadLocal 값 설정
            final ThreadLocal<String> threadLocal = new ThreadLocal<>();
            threadLocal.set("부모 값");

            // When: 자식 가상 스레드에서 ThreadLocal 값 조회
            final String[] result = new String[1];
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                executor.submit(() -> {
                    result[0] = threadLocal.get();
                    return null;
                }).get();
            }

            // Then: ThreadLocal 값이 상속되지 않아야 함
            assertNull(result[0]);
        }
    }

    @Nested
    @DisplayName("InheritableThreadLocal 테스트")
    class ThreadLocalInheritableTests {

        @Test
        @DisplayName("InheritableThreadLocal은 일반 스레드에 상속되어야 한다")
        void inheritableThreadLocalShouldBeInheritedByRegularThread() throws Exception {
            // Given: 부모 스레드에 InheritableThreadLocal 값 설정
            final ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
            inheritableThreadLocal.set("부모 값");

            // When: 자식 일반 스레드에서 InheritableThreadLocal 값 조회
            final String[] result = new String[1];
            Thread childThread = new Thread(() -> {
                result[0] = inheritableThreadLocal.get();
            });
            childThread.start();
            childThread.join();

            // Then: InheritableThreadLocal 값이 상속되어야 함
            assertEquals("부모 값", result[0]);
        }

        @Test
        @DisplayName("InheritableThreadLocal은 가상 스레드에 상속되어야 한다")
        void inheritableThreadLocalShouldBeInheritedByVirtualThread() throws Exception {
            // Given: 부모 스레드에 InheritableThreadLocal 값 설정
            final ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
            String value = "부모 값";
            inheritableThreadLocal.set(value);

            // When: 자식 가상 스레드에서 InheritableThreadLocal 값 조회
            final String[] result = new String[1];
            try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                executor.submit(() -> {
                    result[0] = inheritableThreadLocal.get();
                    return null;
                }).get();
            }

            // Then: InheritableThreadLocal 값이 상속되어야 함
            assertEquals(value, result[0]);
        }

        @Nested
        @DisplayName("값 수정과 참조 테스트")
        class ValueModificationTests {

            @Test
            @DisplayName("InheritableThreadLocal의 가변 객체는 참조가 공유되어 수정이 반영된다")
            void mutableObjectsInInheritableThreadLocalShouldShareReference() throws Exception {
                // Given: 부모 스레드에 가변 객체(StringBuilder) 설정
                final InheritableThreadLocal<StringBuilder> threadLocalInheritable = new InheritableThreadLocal<>();
                StringBuilder originalValue = new StringBuilder("원본");
                threadLocalInheritable.set(originalValue);

                // When: 일반 스레드에서 상속받은 값 수정
                Thread regularThread = new Thread(() -> {
                    StringBuilder inherited = threadLocalInheritable.get();
                    inherited.append("-일반스레드수정");
                });
                regularThread.start();
                regularThread.join();

                // And: 가상 스레드에서 상속받은 값 수정
                try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
                    executor.submit(() -> {
                        StringBuilder inherited = threadLocalInheritable.get();
                        inherited.append("-가상스레드수정");
                        return null;
                    }).get();
                }

                // Then: 모든 수정이 원본 객체에 반영되어야 함
                assertEquals("원본-일반스레드수정-가상스레드수정", originalValue.toString());
            }

            @Test
            @DisplayName("InheritableThreadLocal에서 자식 스레드가 값을 변경해도 부모 스레드는 영향받지 않는다")
            void childThreadShouldNotAffectParentWhenChangingValue() throws Exception {
                // Given: 부모 스레드에 InheritableThreadLocal 값 설정
                final InheritableThreadLocal<String> threadLocalInheritable = new InheritableThreadLocal<>();
                threadLocalInheritable.set("부모 값");

                // When: 자식 스레드에서 값을 변경
                Thread childThread = new Thread(() -> {
                    String value = threadLocalInheritable.get();
                    assertEquals("부모 값", value);
                    threadLocalInheritable.set("자식 값");
                });
                childThread.start();
                childThread.join();

                // Then: 부모 스레드의 값은 변경되지 않아야 함
                assertEquals("부모 값", threadLocalInheritable.get());
            }
        }

        @Nested
        @DisplayName("중첩 스레드 상속 테스트")
        class NestedThreadInheritanceTests {

            @Test
            @DisplayName("일반 스레드 안의 일반 스레드는 직계 부모의 값을 상속해야 한다")
            void regularThreadShouldInheritFromImmediateRegularParent() throws Exception {
                // Given: 최상위 스레드에 값 설정
                final InheritableThreadLocal<String> threadLocalInheritable = new InheritableThreadLocal<>();
                String highest = "최상위 부모";
                threadLocalInheritable.set(highest);

                // When: 중첩된 일반 스레드 생성 (일반 → 일반)
                final String[] result = new String[1];
                String intermediate = "중간 부모";
                Thread outerThread = new Thread(() -> {
                    // 중간 부모 스레드에서 값 변경
                    threadLocalInheritable.set(intermediate);

                    // 내부 스레드 생성
                    Thread innerThread = new Thread(() -> {
                        result[0] = threadLocalInheritable.get();
                    });
                    innerThread.start();
                    try {
                        innerThread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
                outerThread.start();
                outerThread.join();

                // Then: 내부 스레드는 중간 부모의 값을 상속해야 함
                assertEquals(intermediate, result[0]);
            }

            @Test
            @DisplayName("가상 스레드 안의 가상 스레드는 직계 부모의 값을 상속해야 한다")
            void virtualThreadShouldInheritFromImmediateVirtualParent() throws Exception {
                // Given: 최상위 스레드에 값 설정
                final InheritableThreadLocal<String> threadLocalInheritable = new InheritableThreadLocal<>();
                String highest = "최상위 부모";
                threadLocalInheritable.set(highest);

                // When: 중첩된 가상 스레드 생성 (가상 → 가상)
                final String[] result = new String[1];
                String intermediate = "중간 부모";
                try (ExecutorService outerExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
                    outerExecutor.submit(() -> {
                        // 중간 부모 스레드에서 값 변경
                        threadLocalInheritable.set(intermediate);

                        try (ExecutorService innerExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
                            innerExecutor.submit(() -> {
                                result[0] = threadLocalInheritable.get();
                                return null;
                            }).get();
                        }
                        return null;
                    }).get();
                }

                // Then: 내부 가상 스레드는 중간 부모의 값을 상속해야 함
                assertEquals(intermediate, result[0]);
            }

            @Test
            @DisplayName("일반 스레드 안의 가상 스레드는 일반 스레드의 값을 상속해야 한다")
            void virtualThreadShouldInheritFromRegularParent() throws Exception {
                // Given: 최상위 스레드에 값 설정
                final InheritableThreadLocal<String> threadLocalInheritable = new InheritableThreadLocal<>();
                threadLocalInheritable.set("최상위 부모");

                // When: 일반 스레드 안에 가상 스레드 생성 (일반 → 가상)
                final String[] result = new String[1];
                Thread regularThread = new Thread(() -> {
                    threadLocalInheritable.set("일반 부모");

                    try (ExecutorService innerExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
                        innerExecutor.submit(() -> {
                            result[0] = threadLocalInheritable.get();
                            return null;
                        }).get();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                regularThread.start();
                regularThread.join();

                // Then: 가상 스레드는 일반 부모 스레드의 값을 상속해야 함
                assertEquals("일반 부모", result[0]);
            }

            @Test
            @DisplayName("가상 스레드 안의 일반 스레드는 가상 스레드의 값을 상속해야 한다")
            void regularThreadShouldInheritFromVirtualParent() throws Exception {
                // Given: 최상위 스레드에 값 설정
                final InheritableThreadLocal<String> threadLocalInheritable = new InheritableThreadLocal<>();
                threadLocalInheritable.set("최상위 부모");

                // When: 가상 스레드 안에 일반 스레드 생성 (가상 → 일반)
                final String[] result = new String[1];
                try (ExecutorService outerExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
                    outerExecutor.submit(() -> {
                        threadLocalInheritable.set("가상 부모");

                        Thread innerThread = new Thread(() -> {
                            result[0] = threadLocalInheritable.get();
                        });
                        innerThread.start();
                        innerThread.join();
                        return null;
                    }).get();
                }

                // Then: 일반 스레드는 가상 부모 스레드의 값을 상속해야 함
                assertEquals("가상 부모", result[0]);
            }
        }
    }
}
