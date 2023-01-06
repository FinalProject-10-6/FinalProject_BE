//package com.ggt.finalproject.exception;
//
//
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class ExceptionHandling {
//
//    @ExceptionHandler({RestApiException.class})
//    protected Object handleCustomException(RestApiException e) {
//        return GlobalResDto.fail(e.getErrorCode());
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
//        String errorMessage = e.getBindingResult()
//                .getAllErrors()
//                .get(0)
//                .getDefaultMessage();
//
//        return GlobalResDto.fail(errorMessage);
//    }
//
//}
