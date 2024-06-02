package com.example.rest.exception;

import com.example.rest.model.ErrorDetails;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GlobalExceptionHandlerTest {


    // handleSaldoInsuficienteException returns BAD_REQUEST status and correct error details
    @Test
    public void test_handle_saldo_insuficiente_exception_returns_bad_request() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        SaldoInsuficienteException exception = new SaldoInsuficienteException("Saldo insuficiente");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("details");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleSaldoInsuficienteException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Saldo insuficiente", response.getBody().getMessage());
        assertEquals("details", response.getBody().getDetails());
    }

    // handleCuentaNoEncontradaException returns NOT_FOUND status and correct error details
    @Test
    public void test_handle_cuenta_no_encontrada_exception_returns_not_found() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        CuentaNoEncontradaException exception = new CuentaNoEncontradaException("Cuenta no encontrada");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("details");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleCuentaNoEncontradaException(exception, request);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Cuenta no encontrada", response.getBody().getMessage());
        assertEquals("details", response.getBody().getDetails());
    }

    // handleGlobalException returns INTERNAL_SERVER_ERROR status and correct error details
    @Test
    public void test_handle_global_exception_returns_internal_server_error() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception exception = new Exception("Internal error");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("details");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleGlobalException(exception, request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Internal error", response.getBody().getMessage());
        assertEquals("details", response.getBody().getDetails());
    }

    // ErrorDetails object is correctly populated with statusCode, message, and details
    @Test
    public void test_error_details_object_is_correctly_populated() {
        // Given
        int statusCode = 400;
        String message = "Error message";
        String details = "Error details";

        // When
        ErrorDetails errorDetails = new ErrorDetails(statusCode, message, details);

        // Then
        assertEquals(statusCode, errorDetails.getStatusCode());
        assertEquals(message, errorDetails.getMessage());
        assertEquals(details, errorDetails.getDetails());
    }


    // Null message in SaldoInsuficienteException is handled gracefully
    @Test
    public void test_null_message_in_saldo_insuficiente_exception_is_handled_gracefully() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        SaldoInsuficienteException exception = new SaldoInsuficienteException(null);
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("details");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleSaldoInsuficienteException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getMessage());
        assertEquals("details", response.getBody().getDetails());
    }

    // Null message in CuentaNoEncontradaException is handled gracefully
    @Test
    public void test_null_message_in_cuenta_no_encontrada_exception_is_handled_gracefully() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        CuentaNoEncontradaException exception = new CuentaNoEncontradaException(null);
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("details");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleCuentaNoEncontradaException(exception, request);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody().getMessage());
        assertEquals("details", response.getBody().getDetails());
    }

    // Null message in generic Exception is handled gracefully
    @Test
    public void test_null_message_in_generic_exception_is_handled_gracefully() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception exception = new Exception((String) null);
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("details");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleGlobalException(exception, request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody().getMessage());
        assertEquals("details", response.getBody().getDetails());
    }

    // Empty details in WebRequest are handled correctly
    @Test
    public void test_empty_details_in_web_request_are_handled_correctly() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        SaldoInsuficienteException exception = new SaldoInsuficienteException("Saldo insuficiente");

        WebRequest request = mock(WebRequest.class);

        when(request.getDescription(false)).thenReturn("");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleSaldoInsuficienteException(exception, request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertEquals("Saldo insuficiente", response.getBody().getMessage());

        assertEquals("", response.getBody().getDetails());
    }

    // Unexpected exception types are handled by handleGlobalException
    @Test
    public void test_unexpected_exception_types_are_handled_by_handle_global_exception() {
        // Given
        GlobalExceptionHandler handler = new GlobalExceptionHandler();

        RuntimeException exception = new RuntimeException("Unexpected error");

        WebRequest request = mock(WebRequest.class);

        when(request.getDescription(false)).thenReturn("details");

        // When
        ResponseEntity<ErrorDetails> response = handler.handleGlobalException(exception, request);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        assertEquals("Unexpected error", response.getBody().getMessage());

        assertEquals("details", response.getBody().getDetails());
    }

}

