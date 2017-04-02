package com.sean.web.model.error;

/**
 * @author Sean
 *
 * A dedicated class for the handling of an  {@link ErrorMessage} that will be sent
 * using JSON to the client.
 */
public class ErrorMessage {

    /**
     * The message of the create.
     */
    private final String errorMessage;

    /**
     * The private constructor used to create a new {@link ErrorMessage} with the create message.
     * @param errorMessage The message of the create.
     */
    private ErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Creates a new {@link ErrorMessage} along with the error message.
     * @param message The error.
     * @return A new {@link ErrorMessage}.
     */
    public static ErrorMessage create(final String message) {
        return new ErrorMessage(message);
    }

    /**
     * Gets the message of the error.
     * @return The error message.
     */
    public String getMessage() {
        return errorMessage;
    }

}
