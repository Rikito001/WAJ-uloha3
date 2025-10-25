package sk.ukf.uloha2.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ApiResponse<T> {
    private String message;
    private T data;
    private String datetime;

    public ApiResponse() {
        this.datetime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
        this.datetime = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> error(String message, T data) {
        return new ApiResponse<>(message, data);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}