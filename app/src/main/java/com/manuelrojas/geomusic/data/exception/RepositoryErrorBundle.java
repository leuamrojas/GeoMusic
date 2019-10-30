package com.manuelrojas.geomusic.data.exception;

import com.manuelrojas.geomusic.domain.exception.ErrorBundle;

/**
 * Wrapper around Exceptions used to manage errors in the repository.
 */
class RepositoryErrorBundle implements ErrorBundle {

    private final Exception exception;

    RepositoryErrorBundle(Exception exception) {
        this.exception = exception;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getErrorMessage() {
        String message = "";
        if (this.exception != null) {
            message = this.exception.getMessage();
        }
        return message;
    }
}