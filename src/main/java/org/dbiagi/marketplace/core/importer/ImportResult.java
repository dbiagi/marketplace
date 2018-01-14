package org.dbiagi.marketplace.core.importer;

import java.util.ArrayList;
import java.util.List;

class ImportResult {
    private int count = 0;
    private long startTime;
    private long endTime;
    private List<String> errors = new ArrayList<>();

    ImportResult() {
    }

    ImportResult(int count, int startTime) {
        this.count = count;
        this.startTime = startTime;
    }

    public void start() {
        startTime = System.currentTimeMillis();
    }

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    public int getCount() {
        return count;
    }

    /**
     * Increase import count
     */
    public void add() {
        this.count++;
    }

    public long getElapsedTime() {
        return endTime - startTime;
    }

    public void addError(String message) {
        errors.add(message);
    }

    public List getErrors() {
        return errors;
    }

    public int errorsCount() {
        return errors.size();
    }
}
