package com.msvc.user.user_service.model;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class APIResponse {
    private String message;
    private boolean success;
    private HttpStatus status;
}
