package de.techfak.gse.lwalkenhorst.client;

import com.android.volley.VolleyError;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import de.techfak.se.multiplayer.server.response_body.ResponseObject;

public class RequestError {

    private final static ObjectMapper JSON_PARSER = new ObjectMapper();

    private final int statusCode;
    private ResponseObject responseObject;

    public RequestError(final VolleyError error) {
        if (error.networkResponse == null) {
            this.statusCode = 500;
            this.responseObject = new ResponseObject(false, "No Connection!");
            return;
        }
        this.statusCode = error.networkResponse.statusCode;

        final String data = new String(error.networkResponse.data);
        try {
            responseObject = JSON_PARSER.readValue(data, ResponseObject.class);
        } catch (IOException e) {
            responseObject = ResponseObject.failure(data);
        }
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return responseObject.getMessage();
    }
}
