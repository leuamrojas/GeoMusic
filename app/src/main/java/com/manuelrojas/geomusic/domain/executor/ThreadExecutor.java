package com.manuelrojas.geomusic.domain.executor;

import com.manuelrojas.geomusic.domain.interactor.UseCase;

import java.util.concurrent.Executor;


/**
 * Executor implementation can be based on different frameworks or techniques of asynchronous
 * execution, but every implementation will execute the
 * {@link UseCase out of the UI thread.
 */
public interface ThreadExecutor extends Executor {
}
