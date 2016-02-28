package org.servicestation.model;

public enum Status {
    INIT("init"),
    ACCEPTED("accepted"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private String status;

    Status(String status) {
        this.status = status;
    }

}
