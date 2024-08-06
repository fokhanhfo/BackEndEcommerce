package com.projectRestAPI.studensystem.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseObject {
    private String status;
    private String message;
    private int errCode;
    private Object data;
}
