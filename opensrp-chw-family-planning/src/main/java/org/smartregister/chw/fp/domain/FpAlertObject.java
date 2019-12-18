package org.smartregister.chw.fp.domain;

public class FpAlertObject {
    private String fpMethod;
    private Integer fpPillCycles;
    private String fpStartDate;

    public FpAlertObject() {
    }

    public String getFpMethod() {
        return fpMethod;
    }

    public void setFpMethod(String fpMethod) {
        this.fpMethod = fpMethod;
    }

    public Integer getFpPillCycles() {
        return fpPillCycles;
    }

    public void setFpPillCycles(Integer fpPillCycles) {
        this.fpPillCycles = fpPillCycles;
    }

    public String getFpStartDate() {
        return fpStartDate;
    }

    public void setFpStartDate(String fpStartDate) {
        this.fpStartDate = fpStartDate;
    }
}

