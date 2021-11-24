package de.techfak.gse.lwalkenhorst.client;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.URI;

import de.techfak.se.multiplayer.game.GameStatus;
import de.techfak.se.multiplayer.server.request_body.RegisterBody;
import de.techfak.se.multiplayer.server.request_body.StatusBody;

public class Client {

    private final URL URL;
    private final RequestQueue queue;

    public Client(final String url, Context context) {
        this.URL = new URL(URI.create(url));
        queue = Volley.newRequestQueue(context);
    }

    public void connect(SuccessCallback successCallback, ErrorCallback errorCallback) {
        StringRequest request = new RequestBuilder()
                .method(Request.Method.GET)
                .url(URL)
                .onSuccess(successCallback)
                .onError(errorCallback)
                .build();

        queue.add(request);
    }

    public void register(final String name, SuccessCallback successCallback, ErrorCallback errorCallback) {
        StringRequest request = new RequestBuilder()
                .method(Request.Method.POST)
                .url(URL.append("/api/game/players"))
                .onSuccess(successCallback)
                .onError(errorCallback)
                .contentType("application/json")
                .body(new RegisterBody(name))
                .build();

        queue.add(request);
    }

    public void getPlayers(final String name, SuccessCallback successCallback) {
        StringRequest request = new RequestBuilder()
                .method(Request.Method.GET)
                .url(URL.append("/api/game/players"))
                .onSuccess(successCallback)
                .addParam("name", name)
                .build();

        queue.add(request);
    }

    public void getStatus(final String name, SuccessCallback successCallback) {
        StringRequest request = new RequestBuilder()
                .method(Request.Method.GET)
                .url(URL.append("/api/game/status"))
                .onSuccess(successCallback)
                .addParam("name", name)
                .build();

        queue.add(request);
    }

    public void updateStatus(final GameStatus status, final String name, ErrorCallback errorCallback) {
        StringRequest request = new RequestBuilder()
                .method(Request.Method.POST)
                .url(URL.append("/api/game/status"))
                .onError(errorCallback)
                .contentType("application/json")
                .body(new StatusBody(status, name))
                .build();

        queue.add(request);
    }

    public void getBoard(final String name, SuccessCallback successCallback) {
        StringRequest request = new RequestBuilder()
                .method(Request.Method.GET)
                .url(URL.append("/api/game/board"))
                .onSuccess(successCallback)
                .addParam("name", name)
                .build();

        queue.add(request);
    }

}
