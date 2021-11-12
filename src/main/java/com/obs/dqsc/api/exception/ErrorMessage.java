package com.obs.dqsc.api.exception;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode

/**
 * @author  Othman CHAHBOUNE
 * @since 11-10-2021
 */
public class ErrorMessage {
    private ErrorCode errorCode;
    private int statusCode;
    private Date timestamp;
    private String message;
    private String description;
}
