package nl.gerimedica.assignment.resource;

import jakarta.validation.ConstraintViolationException;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;

import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;

import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


@Order(1)
@Component
public class ErrorHandler implements ErrorWebExceptionHandler {

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        HttpStatus status =
               ex instanceof ConstraintViolationException || ex instanceof IllegalArgumentException
                        ? HttpStatus.BAD_REQUEST
                        : HttpStatus.INTERNAL_SERVER_ERROR;

        ServerHttpResponse response = exchange.getResponse();


        return response.setStatusCode(status) ? response.setComplete() : Mono.error(ex);
    }
}

