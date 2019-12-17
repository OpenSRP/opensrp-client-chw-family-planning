package org.smartregister.chw.fp.domain;
import java.util.Date;

public class FpAlertObject {
    private String fpMethod;
    private Integer fpPillCycles;
    private Date fpStartDate;

    public FpAlertObject(String fpMethod, Integer fpPillCycles, Date fpStartDate){
        this.fpMethod = fpMethod;
        this.fpPillCycles = fpPillCycles;
        this.fpStartDate = fpStartDate;
    }

    public String getFpMethod() {
        return fpMethod;
    }

    public Integer getFpPillCycles() {
        return fpPillCycles;
    }

    public Date getFpStartDate() {
        return fpStartDate;
    }
    public void setFpMethod(String fpMethod) {
        this.fpMethod = fpMethod;
    }

    public void setFpPillCycles(Integer fpPillCycles) {
        this.fpPillCycles = fpPillCycles;
    }

    public void setFpStartDate(Date fpStartDate) {
        this.fpStartDate = fpStartDate;
    }
}

