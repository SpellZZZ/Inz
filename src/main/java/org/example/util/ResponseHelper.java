package org.example.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.HashMap;
import java.util.Map;

public class ResponseHelper {

    public static ResponseEntity<Object> createSuccessResponse(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("validate", true);
        response.put("message", message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public static ResponseEntity<Object> createErrorResponse(HttpStatus httpStatus, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("validate", false);
        response.put("message", message);
        return new ResponseEntity<>(response, httpStatus);
    }
}