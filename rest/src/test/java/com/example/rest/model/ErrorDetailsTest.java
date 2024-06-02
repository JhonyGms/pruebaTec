package com.example.rest.model;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;


public class ErrorDetailsTest {


    // Creating an ErrorDetails object with valid statusCode, message, and details
    @Test
    public void test_create_error_details_with_valid_data() {
        // Given
        int statusCode = 200;
        String message = "OK";
        String details = "Request was successful";

        // When
        ErrorDetails errorDetails = new ErrorDetails(statusCode, message, details);

        // Then
        assertNotNull(errorDetails);
        assertEquals(statusCode, errorDetails.getStatusCode());
        assertEquals(message, errorDetails.getMessage());
        assertEquals(details, errorDetails.getDetails());
    }

    // Retrieving statusCode using getStatusCode method
    @Test
    public void test_get_status_code() {
        // Given
        int statusCode = 404;
        ErrorDetails errorDetails = new ErrorDetails(statusCode, "Not Found", "The requested resource was not found");

        // When
        int retrievedStatusCode = errorDetails.getStatusCode();

        // Then
        assertEquals(statusCode, retrievedStatusCode);
    }

    // Creating an ErrorDetails object with a negative statusCode
    @Test
    public void test_create_error_details_with_negative_status_code() {
        // Given
        int statusCode = -1;
        String message = "Error";
        String details = "Negative status code";

        // When
        ErrorDetails errorDetails = new ErrorDetails(statusCode, message, details);

        // Then
        assertNotNull(errorDetails);
        assertEquals(statusCode, errorDetails.getStatusCode());
        assertEquals(message, errorDetails.getMessage());
        assertEquals(details, errorDetails.getDetails());
    }

    // Creating an ErrorDetails object with an empty message
    @Test
    public void test_create_error_details_with_empty_message() {
        // Given
        int statusCode = 500;
        String message = "";
        String details = "Empty message";

        // When
        ErrorDetails errorDetails = new ErrorDetails(statusCode, message, details);

        // Then
        assertNotNull(errorDetails);
        assertEquals(statusCode, errorDetails.getStatusCode());
        assertEquals(message, errorDetails.getMessage());
        assertEquals(details, errorDetails.getDetails());
    }

}