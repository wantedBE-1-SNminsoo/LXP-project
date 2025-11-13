package com.ogirafferes.lxp.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NumberFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNumberFormatException(NumberFormatException e, Model model) {
        model.addAttribute("error", "잘못된 형식의 숫자 입력입니다: " + e.getMessage());
        return "error/400"; // Assuming you have an error/400.html template
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, Model model) {
        if (e.getRequiredType() != null && (e.getRequiredType().equals(Long.class) || e.getRequiredType().equals(Integer.class))) {
            model.addAttribute("error", "요청 파라미터가 올바른 숫자 형식이 아닙니다. (예: " + e.getValue() + ")");
        } else {
            model.addAttribute("error", "잘못된 요청 파라미터 형식입니다: " + e.getMessage());
        }
        return "error/400"; // Assuming you have an error/400.html template
    }

    // You can add more specific exception handlers here
    // For example, a general exception handler for any unhandled exceptions
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGeneralException(Exception e, Model model) {
        model.addAttribute("error", "서버 오류가 발생했습니다: " + e.getMessage());
        // Log the exception for debugging purposes
        e.printStackTrace(); 
        return "error/500"; // Assuming you have an error/500.html template
    }
}
