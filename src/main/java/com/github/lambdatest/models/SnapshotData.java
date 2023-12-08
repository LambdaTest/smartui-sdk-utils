package com.github.lambdatest.models;

public class SnapshotData {
    private Snapshot snapshot;
    private String testType;

    // Getters and Setters
    public Snapshot getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(Snapshot snapshot) {
        this.snapshot = snapshot;
    }

    public String getTestType() {
        return testType;
    }

    public void setTestType(String testType) {
        this.testType = testType;
    }
}
