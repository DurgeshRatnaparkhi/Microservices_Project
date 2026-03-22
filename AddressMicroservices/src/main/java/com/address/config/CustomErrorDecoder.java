package com.address.config;

import com.address.exception.CustomException;
import com.address.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;

import java.io.IOException;
import java.io.InputStream;

public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();

        if (status == 503) {
            return new RuntimeException("Employee service is currently unavailable. Please try again later.");
        } else if (status == 404) {
            return new RuntimeException("Employee not found.");
        }


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

        try (InputStream is = response.body().asInputStream()) {

            ErrorResponse errorResponse = objectMapper.readValue(is, ErrorResponse.class);

            return new CustomException(
                    errorResponse.getMessage()

            );

        } catch (IOException e) {

            throw new CustomException("INTERNAL_SERVER_ERROR");

        }
    }
}