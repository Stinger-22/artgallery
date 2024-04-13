package com.imagems.util;

import com.imagems.exception.BadRequestException;
import com.imagems.exception.NotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class RequestErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        String requestUrl = response.request().url();
        Response.Body responseBody = response.body();
        int status = response.status();
        switch (status) {
            case 400:
                return new BadRequestException("Bad Request");
            case 404:
                return new NotFoundException("Not found");
            default:
                return new RuntimeException("Error code " + status + " is not handled");
        }
    }
}
