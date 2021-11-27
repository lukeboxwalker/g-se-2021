package de.techfak.gse.lwalkenhorst.client;

import java.io.Serializable;

public class OnlineSession implements Serializable {

    private final String name;
    private final String ip;

    public OnlineSession(final String name, final String ip) {
        this.name = name;
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }
}
