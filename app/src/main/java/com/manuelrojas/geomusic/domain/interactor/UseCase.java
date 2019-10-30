package com.manuelrojas.geomusic.domain.interactor;

import com.manuelrojas.geomusic.domain.executor.PostExecutionThread;
import com.manuelrojas.geomusic.domain.executor.ThreadExecutor;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<T, Params> {

    private final ThreadExecutor threadExecutor;
    private final PostExecutionThread postExecutionThread;
    private final CompositeDisposable disposables;

    UseCase(ThreadExecutor threadExecutor, PostExecutionThread postExecutionThread) {
        this.threadExecutor = threadExecutor;
        this.postExecutionThread = postExecutionThread;
        this.disposables = new CompositeDisposable();
    }

    /**
     * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
     */
    abstract Observable<T> buildUseCaseObservable(Params params);

    /**
     * Executes the current use case.
     *
     * @param observer {@link DisposableObserver} which will be listening to the observable build
     * by {@link #buildUseCaseObservable(Params)} ()} method.
     * @param params Parameters (Optional) used to build/execute this use case.
     */
    public void execute(DisposableObserver<T> observer, Params params) {
//        Preconditions.checkNotNull(observer);
        if (observer != null) {
            final Observable<T> observable = this.buildUseCaseObservable(params)
                    .subscribeOn(Schedulers.from(threadExecutor))
                    .observeOn(postExecutionThread.getScheduler());
            addDisposable(observable.subscribeWith(observer));
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    public void dispose() {
        if (!disposables.isDisposed()) {
            disposables.dispose();
        }
    }

    /**
     * Dispose from current {@link CompositeDisposable}.
     */
    private void addDisposable(Disposable disposable) {
//        Preconditions.checkNotNull(disposable);
//        Preconditions.checkNotNull(disposables);
        if (disposable!=null && disposables!=null) {
            disposables.add(disposable);
        }
    }

}
