package com.quantumdataengines.app.config.checkermanagement;

public enum MakerCheckerStatus {
    PENDING("P","Pending Entity, ready to be verified"),
    APPROVED("A"," Approved Entity"),
    REJECTED("R", "Rejected Entity"),
    INVALID("I","Invalid Entity");

    private final String status;
    private final String desceription;

    MakerCheckerStatus(String status, String desceription) {
        this.status = status;
        this.desceription = desceription;
    }

    public String getStatus() {
        return status;
    }

    public String getDesceription() {
        return desceription;
    }
}
