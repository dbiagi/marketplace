package org.dbiagi.marketplace.core.importer;

public class ImportResult {
    private int count = 0;
    private int time;

    ImportResult() {
    }

    ImportResult(int count, int time) {
        this.count = count;
        this.time = time;
    }

    public int getCount() {
        return count;
    }

    public void addCount() {
        this.count++;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
