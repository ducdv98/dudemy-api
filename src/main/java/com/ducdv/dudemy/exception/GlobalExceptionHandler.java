package com.ducdv.dudemy.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

@Component
@Order(-2)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

        // TODO: Handle different types of exceptions
        /* if (ex instanceof CustomBusinessException) {
            exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
            DataBuffer dataBuffer = getDataBuffer(bufferFactory, new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    ex.getMessage()
            ));
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        }

        if (ex instanceof ValidationException) {
            exchange.getResponse().setStatusCode(HttpStatus.UNPROCESSABLE_ENTITY);
            DataBuffer dataBuffer = getDataBuffer(bufferFactory, new ErrorResponse(
                    HttpStatus.UNPROCESSABLE_ENTITY.value(),
                    ex.getMessage()
            ));
            return exchange.getResponse().writeWith(Mono.just(dataBuffer));
        } */

        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        DataBuffer dataBuffer = getDataBuffer(bufferFactory, new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error"
        ));
        return exchange.getResponse().writeWith(Mono.just(dataBuffer));
    }

    private DataBuffer getDataBuffer(DataBufferFactory bufferFactory, ErrorResponse errorResponse) {
        try {
            return bufferFactory.wrap(objectMapper.writeValueAsBytes(errorResponse));
        } catch (JsonProcessingException e) {
            return bufferFactory.wrap(new byte[0]);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ErrorResponse {
        private int status;
        private String message;
    }
}
