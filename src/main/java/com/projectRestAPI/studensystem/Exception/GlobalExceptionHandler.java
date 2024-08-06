package com.projectRestAPI.studensystem.Exception;
import com.projectRestAPI.studensystem.dto.response.ResponseObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(value = RuntimeException.class)
//    ResponseEntity<ResponseObject> handlingRuntimeException(RuntimeException exception){
//        ResponseObject responseObject = ResponseObject.builder()
//                .errCode(400)
//                .message(exception.getMessage())
//                .status("Error")
//                .build();
//        return ResponseEntity.badRequest().body(responseObject);
//    }
    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    ResponseEntity<ResponseObject> handlingMethodArgument(HttpRequestMethodNotSupportedException exception){
        ErrorCode errorCode = ErrorCode.METHOD_NOT_SUPPORTED;
        ResponseObject responseObject = ResponseObject.builder()
                .errCode(405)
                .message("Sai Method")
                .status("Error")
                .build();
        return ResponseEntity.status(errorCode.getStatusCode()).body(responseObject);
    }


    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResponseObject> handlingAppException(AppException exception){
        ErrorCode errorCode = exception.getErrorCode();
        ResponseObject responseObject = ResponseObject.builder()
                .errCode(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
        return ResponseEntity.badRequest().body(responseObject);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ResponseObject> handlingAccessDeniedException(AccessDeniedException accessDeniedException){
        ErrorCode errorCode = ErrorCode.UNAUTHORIZED;
        return ResponseEntity.status(errorCode.getStatusCode()).body(
                ResponseObject.builder()
                        .errCode(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

//    @ExceptionHandler(value = MethodArgumentNotValidException.class)
//    ResponseEntity<ResponseObject> handlingValidation(MethodArgumentNotValidException exception){
//        String enumKey = exception.getFieldError().getDefaultMessage();
//
//        ErrorCode errorCode = ErrorCode.INVALID_KEY;
//        try {
//            errorCode =ErrorCode.valueOf(enumKey);
//        }catch (Exception e){
//
//        }
//        ResponseObject responseObject = ResponseObject.builder()
//                .errCode(errorCode.getCode())
//                .message(errorCode.getMessage())
//                .build();
//        return ResponseEntity.badRequest().body(responseObject);
//    }

}
