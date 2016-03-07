package org.servicestation.model;

public enum Status {
    INIT("init"),
    ACCEPTED("accepted"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private String status;

    public String getStatus() {
        return status;
    }

    Status(String status) {
        this.status = status;
    }
}
