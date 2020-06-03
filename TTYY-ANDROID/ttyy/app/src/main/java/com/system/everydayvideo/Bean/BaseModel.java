package com.system.everydayvideo.Bean;

import java.io.Serializable;

public abstract class BaseModel implements Serializable {
    private long timeMillis;

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }
}
