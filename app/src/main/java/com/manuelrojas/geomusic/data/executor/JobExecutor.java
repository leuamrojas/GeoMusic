package com.manuelrojas.geomusic.data.executor;

import androidx.annotation.NonNull;

import com.manuelrojas.geomusic.domain.executor.ThreadExecutor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class JobExecutor implements ThreadExecutor {
    private final ThreadPoolExecutor threadPoolExecutor;

    @Inject
    JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), new JobThreadFactory());
    }

    @Override
    public void execute(Runnable runnable) {
        this.threadPoolExecutor.execute(runnable);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override public Thread newThread(@NonNull Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
