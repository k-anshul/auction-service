package com.RillAuction.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class ExceptionHandlers {
    @ExceptionHandler
    //todo :: add proper validation msg
    public ResponseEntity<Object> handle(ValidationException ex) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode json = mapper.createObjectNode();
        json.put("error", ex.toString());
        return ResponseEntity.badRequest()
                .body(json);
    }
}