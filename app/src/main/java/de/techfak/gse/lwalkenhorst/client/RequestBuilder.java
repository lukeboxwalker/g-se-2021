package de.techfak.gse.lwalkenhorst.client;

import com.android.volley.toolbox.StringRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RequestBuilder {

    private final static ObjectMapper JSON_PARSER = new ObjectMapper();

    private URL url;
    private int method;
    private SuccessCallback successCallback= (response) -> {};
    private ErrorCallback errorCallback = (error) -> {};
    private String body;
    private String contentType = "text/plain";
    private final Map<String, String> params = new HashMap<>();

    public RequestBuilder url(final URL url) {
        this.url = url;
        return this;
    }

    public RequestBuilder method(final int method) {
        this.method = method;
        return this;
    }

    public RequestBuilder onSuccess(final SuccessCallback callback) {
        this.successCallback = callback;
        return this;
    }

    public RequestBuilder onError(final ErrorCallback callback) {
        this.errorCallback = callback;
        return this;
    }

    public RequestBuilder contentType(final String contentType) {
        this.contentType = contentType;
        return this;
    }

    public RequestBuilder body(final String body) {
        this.body = body;
        return this;
    }

    public RequestBuilder body(final Object body) {
        try {
            this.body = JSON_PARSER.writeValueAsString(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public RequestBuilder addParam(final String key, final String value) {
        this.params.put(key, value);
        return this;
    }

    public StringRequest build() {
        assert url != null;

        StringBuilder stringBuilder = new StringBuilder();
        for (Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, String> entry = iterator.next();
            stringBuilder.append(entry.getKey()).append("=").append(entry.getValue());
            if (iterator.hasNext()) {
                stringBuilder.append("&");
            }
        }
        String queryParams = stringBuilder.toString();
        String finalUrl = queryParams.isEmpty() ? url.toURI().toString() : url.toURI().toString() + "?" + queryParams;
        return new StringRequest(method, finalUrl, successCallback::onSuccess,
                error -> errorCallback.onError(new RequestError(error))) {
            @Override
            public String getBodyContentType() {
                return contentType + "; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                return body == null ? null : body.getBytes(StandardCharsets.UTF_8);
            }

        };
    }
}
