package com.manuelrojas.geomusic.presentation.exception;

import android.content.Context;

import com.manuelrojas.geomusic.R;
import com.manuelrojas.geomusic.data.exception.NetworkConnectionException;
import com.manuelrojas.geomusic.data.exception.TrackNotFoundException;

/**
 * Factory used to create error messages from an Exception as a condition.
 */
public class ErrorMessageFactory {

    private ErrorMessageFactory() {
        //empty
    }

    /**
     * Creates a String representing an error message.
     *
     * @param context Context needed to retrieve string resources.
     * @param exception An exception used as a condition to retrieve the correct error message.
     * @return {@link String} an error message.
     */
    public static String create(Context context, Exception exception) {
        String message = context.getString(R.string.exception_message_generic);

        if (exception instanceof NetworkConnectionException) {
            message = context.getString(R.string.exception_message_no_connection);
        } else if (exception instanceof TrackNotFoundException) {
            message = context.getString(R.string.exception_message_repo_not_found);
        }

        return message;

    }

    public static String create(Context context, String subject) {
//        String message = context.getString(R.string.exception_message_generic);
//
//        if (exception instanceof NetworkConnectionException) {
//            message = context.getString(R.string.exception_message_no_connection);
//        } else if (exception instanceof TrackNotFoundException) {
//            message = context.getString(R.string.exception_message_repo_not_found);
//        }

        return subject + " " + context.getString(R.string.error_message_generic);

    }

}