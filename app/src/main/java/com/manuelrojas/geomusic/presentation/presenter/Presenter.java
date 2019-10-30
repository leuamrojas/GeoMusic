package com.manuelrojas.geomusic.presentation.presenter;

import com.manuelrojas.geomusic.presentation.view.LoadDataView;

public interface Presenter <T extends LoadDataView> {

    void attachView(T view);

    void detachView();
}
