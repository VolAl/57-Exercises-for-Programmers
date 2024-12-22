package com.url_shortener.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ShortUrlStats implements Serializable {

    private int timesUrlHasBeenAccessed;
    private LocalDateTime lastAccessDateTime;

    public ShortUrlStats(int timesUrlHasBeenAccessed, LocalDateTime lastAccessDateTime) {
        this.timesUrlHasBeenAccessed = timesUrlHasBeenAccessed;
        this.lastAccessDateTime = lastAccessDateTime;
    }

    public int getTimesUrlHasBeenAccessed() {
        return timesUrlHasBeenAccessed;
    }

    public void setTimesUrlHasBeenAccessed(int timesUrlHasBeenAccessed) {
        this.timesUrlHasBeenAccessed = timesUrlHasBeenAccessed;
    }

    public LocalDateTime getLastAccessDateTime() {
        return lastAccessDateTime;
    }

    public void setLastAccessDateTime(LocalDateTime lastAccessDateTime) {
        this.lastAccessDateTime = lastAccessDateTime;
    }
}
