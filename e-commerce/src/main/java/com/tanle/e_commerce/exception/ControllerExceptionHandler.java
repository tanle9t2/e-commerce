package com.tanle.e_commerce.exception;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundExeption exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Resource not found")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleResourceExisted(ResourceExistedException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Resource exist")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.CONFLICT.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Bad request")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUnauthorized(UnauthorizedException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Bad request")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBadCredentials(BadCredentialsException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Bad credentials")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    ResponseEntity<ExceptionResponse> handleKafkaAsycnException(KafkaAsycnException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Resource not found")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.NOT_FOUND.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    ResponseEntity<ExceptionResponse> handleElasticSearchException(ElasticsearchException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title(exception.getMessage())
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorization(AccessDeniedException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("You do not have permission to access this resource")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionResponse> handleExpirationJwt(ExpiredJwtException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Your token expired")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.UNAUTHORIZED.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleDeleteException(ResourceDeleteException exception) {
        ExceptionResponse response = ExceptionResponse.builder()
                .type("/exception/" + exception.getClass().getSimpleName())
                .title("Delete resource fail")
                .detail(exception.getMessage())
                .timeStamp(System.currentTimeMillis())
                .status(HttpStatus.BAD_REQUEST.value())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> globalExceptionHandler(Exception ex) {
        ExceptionResponse message = ExceptionResponse.builder()
                .type("Excetion")
                .title("Internal server error")
                .detail(ex.getMessage())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .timeStamp(System.currentTimeMillis())
                .build();

        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
