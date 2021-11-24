package de.techfak.gse.lwalkenhorst.client;

import com.android.volley.VolleyError;

public interface ConnectCallback {

    void onSuccess(String response);

    void onError(VolleyError error);
}
