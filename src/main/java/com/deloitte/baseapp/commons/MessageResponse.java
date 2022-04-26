package com.deloitte.baseapp.commons;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse<T> {

    private String message;
    private int code;
    private T data;
    private boolean success;

    /**
     * Success response
     *
     * @param data
     * @param message
     */
    public MessageResponse(T data, String message) {
        this.data = data;
        this.message = message;
        this.code = 200;
        this.success = true;
    }

    /**
     * Success response
     *
     * @param data
     */
    public MessageResponse(T data) {
        this.data = data;
        this.code = 200;
        this.success = true;
    }

    /**
     * Error response
     *
     * @param message
     */
    public MessageResponse(String message) {
        this.message = message;
        this.code = 400;
        this.success = false;
    }

    /**
     *
     * @param message
     * @param code
     * @return
     */
    public static MessageResponse ErrorWithCode(String message, int code) {
        MessageResponse messageResponse = new MessageResponse();
        messageResponse.setMessage(message);
        messageResponse.setSuccess(false);
        messageResponse.setCode(code);
        return messageResponse;
    }
}
