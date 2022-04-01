package ch.cern.eam.wshub.core.services.workorders.entities;

import ch.cern.eam.wshub.core.annotations.InforField;

import java.util.Date;
import java.math.BigDecimal;

public class WorkOrderSchedule {

    // WOSCHEDULEID set manually in service
    
    // WOSCHEDULEID/ACTIVITYID/WORKORDERID/JOBNUM
    private String workOrderNumber;
    
    // WOSCHEDULEID/ACTIVITYID/ACTIVITYCODE"
    private String activityCode;

    @InforField(xpath = "SCHEDULEDDATE")
	private Date scheduledDate;

    @InforField(xpath = "SCHEDULEDHOURS")
	private double scheduledHours; 

    @InforField(xpath = "STARTTIME")
	private BigDecimal startTime; 

    @InforField(xpath = "ENDTIME")
	private BigDecimal endTime; 

    @InforField(xpath = "TRADEID/TRADECODE")
    private String tradeCode; 

    @InforField(xpath = "DEPARTMENTID/DEPARTMENTCODE")
    private String departmentCode; 

    @InforField(xpath = "SHIFTID/SHIFTCODE")
    private String shiftCode; 

    @InforField(xpath = "EMPLOYEE/PERSONCODE")
    private String employeeCode; 

    @InforField(xpath = "MAINTENANCEEQUIPMENTID/EQUIPMENTCODE")
    private String equipmentCode; 

    @InforField(xpath = "ENTEREDBY")
    private String enteredBy; 
    
    @InforField(xpath = "FROZEN")
    private Boolean frozen;

    @InforField(xpath = "WOSCHEDULECOMMENT")
    private String comment;

    public String getWorkOrderNumber() {
        return workOrderNumber;
    }

    public void setWorkOrderNumber(String workOrderNumber) {
        this.workOrderNumber = workOrderNumber;
    }

    public String getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(String activityCode) {
        this.activityCode = activityCode;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public double getScheduledHours() {
        return scheduledHours;
    }

    public void setScheduledHours(double scheduledHours) {
        this.scheduledHours = scheduledHours;
    }

    public BigDecimal getStartTime() {
        return startTime;
    }

    public void setStartTime(BigDecimal startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getEndTime() {
        return endTime;
    }

    public void setEndTime(BigDecimal endTime) {
        this.endTime = endTime;
    }

    public String getTradeCode() {
        return tradeCode;
    }

    public void setTradeCode(String tradeCode) {
        this.tradeCode = tradeCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEnteredBy() {
        return enteredBy;
    }

    public void setEnteredBy(String enteredBy) {
        this.enteredBy = enteredBy;
    }

    public Boolean getFrozenCode() {
        return frozen;
    }

    public void setFrozen(Boolean frozen) {
        this.frozen = frozen;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
