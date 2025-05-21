package hse.kpo.antiplagiarism.filestorage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Generic API response DTO.
 *
 * @param <T> Type of data in the response
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
    private String status;
    private String message;
    private T data;

    /**
     * Create a success response with data.
     *
     * @param data    The data to include in the response
     * @param message Success message
     * @param <T>     Type of data
     * @return ApiResponse instance
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status("success")
                .message(message)
                .data(data)
                .build();
    }

    /**
     * Create a success response without data.
     *
     * @param message Success message
     * @return ApiResponse instance
     */
    public static ApiResponse<Void> success(String message) {
        return ApiResponse.<Void>builder()
                .status("success")
                .message(message)
                .build();
    }

    /**
     * Create an error response.
     *
     * @param message Error message
     * @return ApiResponse instance
     */
    public static ApiResponse<Void> error(String message) {
        return ApiResponse.<Void>builder()
                .status("error")
                .message(message)
                .build();
    }
}