package com.sample;

import javax.inject.Inject;

public class AppConfig {

    private boolean bypassEnabled;

    public boolean isBypassEnabled() {
        return bypassEnabled;
    }

    public void setBypassEnabled(boolean bypassEnabled) {
        this.bypassEnabled = bypassEnabled;
    }

    public void flip(){
        bypassEnabled = !bypassEnabled;
    }
}
