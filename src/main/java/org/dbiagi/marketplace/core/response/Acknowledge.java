package org.dbiagi.marketplace.core.response;

public class Acknowledge {
    private boolean acknowledge;

    public Acknowledge() {
    }

    public Acknowledge(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }

    public boolean isAcknowledge() {
        return acknowledge;
    }

    public void setAcknowledge(boolean acknowledge) {
        this.acknowledge = acknowledge;
    }
}
