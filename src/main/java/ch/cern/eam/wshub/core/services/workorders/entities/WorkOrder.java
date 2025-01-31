package ch.cern.eam.wshub.core.services.workorders.entities;

import ch.cern.eam.wshub.core.adapters.BigDecimalAdapter;
import ch.cern.eam.wshub.core.adapters.BigIntegerAdapter;
import ch.cern.eam.wshub.core.adapters.BooleanAdapter;
import ch.cern.eam.wshub.core.adapters.DateAdapter;
import ch.cern.eam.wshub.core.annotations.BooleanType;
import ch.cern.eam.wshub.core.annotations.InforField;
import ch.cern.eam.wshub.core.services.entities.CustomField;
import ch.cern.eam.wshub.core.services.entities.UserDefinedFields;
import ch.cern.eam.wshub.core.services.userdefinedscreens.UserDefinedListHelpable;
import ch.cern.eam.wshub.core.services.userdefinedscreens.entities.UDLValue;
import ch.cern.eam.wshub.core.services.userdefinedscreens.entities.xmlhashmap.XmlHashMapAdapter;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@XmlAccessorType(XmlAccessType.PROPERTY)
@Entity
@Table(name = "R5EVENTS")
public class WorkOrder implements Serializable, UserDefinedListHelpable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2035481937868647264L;
	@Column(name = "EVT_CODE")
	@Id
	@InforField(xpath = "WORKORDERID/JOBNUM")
	private String number;
	@Column(name = "EVT_DESC")
	@InforField(xpath = "WORKORDERID/DESCRIPTION")
	private String description;
	@Column(name = "EVT_ORG")
	@InforField(xpath = "WORKORDERID/ORGANIZATIONID/ORGANIZATIONCODE")
	private String organization;
	@Transient
	@InforField(xpath = "CLASSID/CLASSCODE", nullifyParentLevel = 1)
	private String classCode;
	@Transient
	@InforField(xpath = "CLASSID/DESCRIPTION", readOnly = true)
	private String classDesc;
	@Transient
	@InforField(xpath = "CLASSID/ORGANIZATIONID/ORGANIZATIONCODE")
	private String classOrganization;
	@Transient
	@InforField(xpath = "recordid")
	private BigInteger updateCount;
	@Transient
	private String eventType;
	//
	@Column(name = "EVT_STATUS")
	@InforField(xpath = "STATUS/STATUSCODE")
	private String statusCode;
	@Transient
	@InforField(xpath = "STATUS/DESCRIPTION", readOnly = true)
	private String statusDesc;
	//
	@Transient
	@InforField(xpath = "TYPE/TYPECODE")
	private String typeCode;
	@Transient
	@InforField(xpath = "TYPE/DESCRIPTION", readOnly = true)
	private String typeDesc;
	//
	@Column(name = "EVT_MRC")
	@InforField(xpath = "DEPARTMENTID/DEPARTMENTCODE")
	private String departmentCode;
	@Transient
	@InforField(xpath = "DEPARTMENTID/DESCRIPTION", readOnly = true)
	private String departmentDesc;
	@InforField(xpath = "DEPARTMENTID/ORGANIZATIONID/ORGANIZATIONCODE")
	private String departmentOrganization;
	//
	@Column(name = "EVT_OBJECT")
	@InforField(xpath = "EQUIPMENTID/EQUIPMENTCODE")
	private String equipmentCode;
	@Transient
	@InforField(xpath = "EQUIPMENTID/DESCRIPTION", readOnly = true)
	private String equipmentDesc;
	@Column(name = "EVT_OBJECT_ORG")
	@InforField(xpath = "EQUIPMENTID/ORGANIZATIONID/ORGANIZATIONCODE")
	private String equipmentOrganization;
	//
	@Transient
	@InforField(xpath = "PROJECTID/PROJECTCODE")
	private String projectCode;
	@Transient
	@InforField(xpath = "PROJECTID/DESCRIPTION", readOnly = true)
	private String projectDesc;
	@Transient
	@InforField(xpath = "PROJBUD")
	private String projectBudget;
	//
	@Transient
	@InforField(xpath = "LOCATIONID/LOCATIONCODE")
	private String locationCode;
	@Transient
	@InforField(xpath = "LOCATIONID/DESCRIPTION", readOnly = true)
	private String locationDesc;
	@Transient
	@InforField(xpath = "LOCATIONID/ORGANIZATIONID/ORGANIZATIONCODE", nullifyParentLevel = 2)
	private String locationOrganization;
	//
	@Transient
	@InforField(xpath = "PRIORITY/PRIORITYCODE")
	private String priorityCode;
	@Transient
	@InforField(xpath = "PRIORITY/DESCRIPTION", readOnly = true)
	private String priorityDesc;
	//
	@Transient
	@InforField(xpath = "USERDEFINEDAREA")
	private CustomField[] customFields;
	//
	@Transient
	@InforField(xpath = "REPORTED")
	private Date reportedDate;
	@Transient
	@InforField(xpath = "REQUESTEDEND")
	private Date requestedEndDate;
	@Transient
	@InforField(xpath = "REQUESTEDSTART")
	private Date requestedStartDate;
	@Transient
	@InforField(xpath = "TARGETDATE")
	private Date scheduledStartDate; // target date
	@Transient
	@InforField(xpath = "SCHEDEND")
	private Date scheduledEndDate;
	@Transient
	@InforField(xpath = "STARTDATE")
	private Date startDate;
	@Transient
	@InforField(xpath = "COMPLETEDDATE")
	private Date completedDate;
	@Transient
	@InforField(xpath = "DUEDATE")
	private Date dueDate;
	@Transient
	@InforField(xpath = "CREATEDDATE")
	private Date createdDate;
	@Column(name = "EVT_CREATEDBY")
	@InforField(xpath = "CREATEDBY/USERCODE")
	private String createdBy;
	@Transient
	@InforField(xpath = "CREATEDBY/DESCRIPTION", readOnly = true)
	private String createdByDesc;

	@Transient
	@InforField(xpath = "PROBLEMCODEID/PROBLEMCODE")
	private String problemCode;
	@Transient
	@InforField(xpath = "FAILURECODEID/FAILURECODE")
	private String failureCode;
	@Transient
	@InforField(xpath = "CAUSECODEID/CAUSECODE")
	private String causeCode;
	@Transient
	@InforField(xpath = "ACTIONCODEID/ACTIONCODE")
	private String actionCode;
	@Transient
	@InforField(xpath = "COSTCODEID/COSTCODE")
	private String costCode;
	@Transient
	@InforField(xpath = "COSTCODEID/DESCRIPTION", readOnly = true)
	private String costCodeDesc;

	@Transient
	@InforField(xpath = "SCHEDGROUP")
	private String assignedBy; // schedgroup
	@Transient
	@InforField(xpath = "REQUESTEDBY/PERSONCODE")
	private String reportedBy; // requested by
	@Transient
	@InforField(xpath = "REQUESTEDBY/DESCRIPTION", readOnly = true)
	private String reportedByDesc;
	@Transient
	@InforField(xpath = "ASSIGNEDTO/PERSONCODE")
	private String assignedTo;
	@Transient
	@InforField(xpath = "ASSIGNEDTO/DESCRIPTION", readOnly = true)
	private String assignedToDesc;
	@Transient
	@InforField(xpath = "STANDARDWO/STDWOCODE")
	private String standardWO;
	@Transient
	@InforField(xpath = "STANDARDWO/DESCRIPTION", readOnly = true)
	private String standardWODesc;
	@Transient
	@InforField(xpath = "PARENTWO/JOBNUM")
	private String parentWO;
	@Transient
	@InforField(xpath = "PARENTWO/DESCRIPTION", readOnly = true)
	private String parentWODesc;
	@Transient
	@InforField(xpath = "ROUTE/ROUTECODE")
	private String route;
	@Transient
	@InforField(xpath = "ROUTE/ROUTEREVISION")
	private BigInteger routeRevision;
	@Transient
	private String comment;
	@Transient
	@InforField(xpath = "TARGETVALUE")
	private BigDecimal targetValue;
	@Transient
	@InforField(xpath = "DOWNTIMEHOURS")
	private BigDecimal downtimeHours;
	@Transient
	@InforField(xpath = "UserDefinedFields")
	private UserDefinedFields userDefinedFields;

	@Transient
	private Boolean confirmedIncompleteChecklist = false;

	@Column(name = "EVT_ORIGWO")
	private String origWO;

	@Transient
	private String copyFrom;

	@InforField(xpath = "BILLABLE", booleanType = BooleanType.PLUS_MINUS)
	private Boolean billable;

	@InforField(xpath = "DEPEND", booleanType = BooleanType.TRUE_FALSE)
	private Boolean depend;

	@InforField(xpath = "EVTISSTYPE")
	private String issType;

	@InforField(xpath = "WARRANTY", booleanType = BooleanType.TRUE_FALSE)
	private Boolean warranty;

	@InforField(xpath = "VENDER")
	private String vendor;

	@Transient
	private HashMap<String, ArrayList<UDLValue>> userDefinedList;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getClassCode() {
		return classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public String getClassDesc() {
		return classDesc;
	}

	public void setClassDesc(String classDesc) {
		this.classDesc = classDesc;
	}

	public BigInteger getUpdateCount() {
		return updateCount;
	}

	public void setUpdateCount(BigInteger updateCount) {
		this.updateCount = updateCount;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentDesc() {
		return departmentDesc;
	}

	public void setDepartmentDesc(String departmentDesc) {
		this.departmentDesc = departmentDesc;
	}

	public String getEquipmentDesc() {
		return equipmentDesc;
	}

	public void setEquipmentDesc(String equipmentDesc) {
		this.equipmentDesc = equipmentDesc;
	}

	public String getPriorityCode() {
		return priorityCode;
	}

	public void setPriorityCode(String priorityCode) {
		this.priorityCode = priorityCode;
	}

	public String getPriorityDesc() {
		return priorityDesc;
	}

	public void setPriorityDesc(String priorityDesc) {
		this.priorityDesc = priorityDesc;
	}

	@JsonProperty("customField")
	@XmlElementWrapper(name = "customFields")
	@XmlElement(name = "customField")
	public CustomField[] getCustomFields() {
		return customFields;
	}

	public void setCustomFields(CustomField[] customFields) {
		this.customFields = customFields;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getReportedDate() {
		return reportedDate;
	}

	public void setReportedDate(Date reportedDate) {
		this.reportedDate = reportedDate;
	}
	@XmlJavaTypeAdapter(DateAdapter.class)

	public Date getRequestedEndDate() {
		return requestedEndDate;
	}

	public void setRequestedEndDate(Date requestedEndDate) {
		this.requestedEndDate = requestedEndDate;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getRequestedStartDate() {
		return requestedStartDate;
	}

	public void setRequestedStartDate(Date requestedStartDate) {
		this.requestedStartDate = requestedStartDate;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)

	public Date getScheduledStartDate() {
		return scheduledStartDate;
	}

	public void setScheduledStartDate(Date scheduledStartDate) {
		this.scheduledStartDate = scheduledStartDate;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)

	public Date getScheduledEndDate() {
		return scheduledEndDate;
	}

	public void setScheduledEndDate(Date scheduledEndDate) {
		this.scheduledEndDate = scheduledEndDate;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)

	public Date getCompletedDate() {
		return completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public String getProblemCode() {
		return problemCode;
	}

	public void setProblemCode(String problemCode) {
		this.problemCode = problemCode;
	}

	public String getFailureCode() {
		return failureCode;
	}

	public void setFailureCode(String failureCode) {
		this.failureCode = failureCode;
	}

	public String getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(String causeCode) {
		this.causeCode = causeCode;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(String assignedBy) {
		this.assignedBy = assignedBy;
	}

	public String getReportedBy() {
		return reportedBy;
	}

	public void setReportedBy(String reportedBy) {
		this.reportedBy = reportedBy;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public String getStandardWO() {
		return standardWO;
	}

	public void setStandardWO(String standardWO) {
		this.standardWO = standardWO;
	}

	public String getStandardWODesc() {
		return standardWODesc;
	}

	public void setStandardWODesc(String standardWODesc) {
		this.standardWODesc = standardWODesc;
	}

	public String getParentWO() {
		return parentWO;
	}

	public void setParentWO(String parentWO) {
		this.parentWO = parentWO;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationDesc() {
		return locationDesc;
	}

	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}

	public String getCostCode() {
		return costCode;
	}

	public void setCostCode(String costCode) {
		this.costCode = costCode;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getEquipmentCode() {
		return equipmentCode;
	}

	public void setEquipmentCode(String equipmentCode) {
		this.equipmentCode = equipmentCode;
	}

	public UserDefinedFields getUserDefinedFields() {
		return userDefinedFields;
	}

	public void setUserDefinedFields(UserDefinedFields userDefinedFields) {
		this.userDefinedFields = userDefinedFields;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@XmlJavaTypeAdapter(DateAdapter.class)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedByDesc() {
		return createdByDesc;
	}

	public void setCreatedByDesc(String createdByDesc) {
		this.createdByDesc = createdByDesc;
	}

	public String getOrigWO() {
		return origWO;
	}

	public void setOrigWO(String origWO) {
		this.origWO = origWO;
	}

	public String getProjectBudget() {
		return projectBudget;
	}

	public void setProjectBudget(String projectBudget) {
		this.projectBudget = projectBudget;
	}

	public String getReportedByDesc() {
		return reportedByDesc;
	}

	public void setReportedByDesc(String reportedByDesc) {
		this.reportedByDesc = reportedByDesc;
	}

	public String getAssignedToDesc() {
		return assignedToDesc;
	}

	public void setAssignedToDesc(String assignedToDesc) {
		this.assignedToDesc = assignedToDesc;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getCostCodeDesc() {
		return costCodeDesc;
	}

	public void setCostCodeDesc(String costCodeDesc) {
		this.costCodeDesc = costCodeDesc;
	}

	public String getParentWODesc() {
		return parentWODesc;
	}

	public void setParentWODesc(String parentWODesc) {
		this.parentWODesc = parentWODesc;
	}

	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	public BigDecimal getTargetValue() { return targetValue; }

	public void setTargetValue(BigDecimal targetValue) { this.targetValue = targetValue; }

	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	public BigDecimal getDowntimeHours() { return downtimeHours; }

	public void setDowntimeHours(BigDecimal downtimeHours) { this.downtimeHours = downtimeHours; }

	@XmlJavaTypeAdapter(BigIntegerAdapter.class)
	public BigInteger getRouteRevision() {
		return routeRevision;
	}

	public void setRouteRevision(BigInteger routeRevision) {
		this.routeRevision = routeRevision;
	}

	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean isConfirmedIncompleteChecklist() {
		return confirmedIncompleteChecklist;
	}

	public void setConfirmedIncompleteChecklist(Boolean confirmedIncompleteChecklist) {
		this.confirmedIncompleteChecklist = confirmedIncompleteChecklist;
	}

	@Override
	public String getCopyFrom() {
		return copyFrom;
	}

	public void setCopyFrom(String copyFrom) {
		this.copyFrom = copyFrom;
	}

	@Override
	public HashMap<String, ArrayList<UDLValue>> getUserDefinedList() {
		return userDefinedList;
	}

	@Override
	public void setUserDefinedList(HashMap<String, ArrayList<UDLValue>> userDefinedList) {
		this.userDefinedList = userDefinedList;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getEquipmentOrganization() {
		return equipmentOrganization;
	}

	public void setEquipmentOrganization(String equipmentOrganization) {
		this.equipmentOrganization = equipmentOrganization;
	}

	public String getClassOrganization() {
		return classOrganization;
	}

	public void setClassOrganization(String classOrganization) {
		this.classOrganization = classOrganization;
	}

	public String getDepartmentOrganization() {
		return departmentOrganization;
	}

	public void setDepartmentOrganization(String departmentOrganization) {
		this.departmentOrganization = departmentOrganization;
	}

	public String getLocationOrganization() {
		return locationOrganization;
	}

	public void setLocationOrganization(String locationOrganization) {
		this.locationOrganization = locationOrganization;
	}

	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getBillable() {
		return billable;
	}

	public void setBillable(final Boolean billable) {
		this.billable = billable;
	}

	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public Boolean getDepend() {
		return depend;
	}

	public void setDepend(final Boolean depend) {
		this.depend = depend;
	}

	public String getIssType() {
		return issType;
	}

	public void setIssType(final String issType) {
		this.issType = issType;
	}

	public Boolean getWarranty() {
		return warranty;
	}

	public void setWarranty(final Boolean warranty) {
		this.warranty = warranty;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	@Override
	public String toString() {
		return "WorkOrder [" + (number != null ? "number=" + number + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (classCode != null ? "classCode=" + classCode + ", " : "")
				+ (classDesc != null ? "classDesc=" + classDesc + ", " : "")
				+ (updateCount != null ? "updateCount=" + updateCount + ", " : "")
				+ (eventType != null ? "eventType=" + eventType + ", " : "")
				+ (statusCode != null ? "statusCode=" + statusCode + ", " : "")
				+ (statusDesc != null ? "statusDesc=" + statusDesc + ", " : "")
				+ (typeCode != null ? "typeCode=" + typeCode + ", " : "")
				+ (typeDesc != null ? "typeDesc=" + typeDesc + ", " : "")
				+ (departmentCode != null ? "departmentCode=" + departmentCode + ", " : "")
				+ (departmentDesc != null ? "departmentDesc=" + departmentDesc + ", " : "")
				+ (equipmentCode != null ? "equipmentCode=" + equipmentCode + ", " : "")
				+ (equipmentDesc != null ? "equipmentDesc=" + equipmentDesc + ", " : "")
				+ (projectCode != null ? "projectCode=" + projectCode + ", " : "")
				+ (projectDesc != null ? "projectDesc=" + projectDesc + ", " : "")
				+ (projectBudget != null ? "projectBudget=" + projectBudget + ", " : "")
				+ (locationCode != null ? "locationCode=" + locationCode + ", " : "")
				+ (locationDesc != null ? "locationDesc=" + locationDesc + ", " : "")
				+ (priorityCode != null ? "priorityCode=" + priorityCode + ", " : "")
				+ (priorityDesc != null ? "priorityDesc=" + priorityDesc + ", " : "")
				+ (customFields != null ? "customFields=" + Arrays.toString(customFields) + ", " : "")
				+ (reportedDate != null ? "reportedDate=" + reportedDate + ", " : "")
				+ (requestedEndDate != null ? "requestedEndDate=" + requestedEndDate + ", " : "")
				+ (requestedStartDate != null ? "requestedStartDate=" + requestedStartDate + ", " : "")
				+ (scheduledStartDate != null ? "scheduledStartDate=" + scheduledStartDate + ", " : "")
				+ (scheduledEndDate != null ? "scheduledEndDate=" + scheduledEndDate + ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (completedDate != null ? "completedDate=" + completedDate + ", " : "")
				+ (dueDate != null ? "dueDate=" + dueDate + ", " : "")
				+ (createdDate != null ? "createdDate=" + createdDate + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdByDesc != null ? "createdByDesc=" + createdByDesc + "," : "")
				+ (problemCode != null ? "problemCode=" + problemCode + ", " : "")
				+ (failureCode != null ? "failureCode=" + failureCode + ", " : "")
				+ (causeCode != null ? "causeCode=" + causeCode + ", " : "")
				+ (actionCode != null ? "actionCode=" + actionCode + ", " : "")
				+ (costCode != null ? "costCode=" + costCode + ", " : "")
				+ (costCodeDesc != null ? "costCodeDesc=" + costCodeDesc + ", " : "")
				+ (assignedBy != null ? "assignedBy=" + assignedBy + ", " : "")
				+ (reportedBy != null ? "reportedBy=" + reportedBy + ", " : "")
				+ (reportedByDesc != null ? "reportedByDesc=" + reportedByDesc + ", " : "")
				+ (assignedTo != null ? "assignedTo=" + assignedTo + ", " : "")
				+ (assignedToDesc != null ? "assignedToDesc=" + assignedToDesc + ", " : "")
				+ (standardWO != null ? "standardWO=" + standardWO + ", " : "")
				+ (parentWO != null ? "parentWO=" + parentWO + ", " : "")
				+ (parentWODesc != null ? "parentWODesc=" + parentWODesc + ", " : "")
				+ (route != null ? "route=" + route + ", " : "") + (comment != null ? "comment=" + comment + ", " : "")
				+ (targetValue != null ? "targetValue=" + targetValue + ", " : "")
				+ (downtimeHours != null ? "downtimeHours=" + downtimeHours + ", " : "")
				+ (userDefinedFields != null ? "userDefinedFields=" + userDefinedFields + ", " : "")
				+ (origWO != null ? "origWO=" + origWO + ", ": "")
				+ (copyFrom != null ? "copyFrom=" + copyFrom + ", " : "")
				+ (userDefinedList != null ? "userDefinedList=" + userDefinedList : "")
				+ (organization != null ? "organization=" + organization + ", " : "")
				+ (equipmentOrganization != null ? "equipmentOrganization=" + equipmentOrganization + ", " : "")
				+ (departmentOrganization != null ? "departmentOrganization=" + departmentOrganization + ", " : "")
				+ (classOrganization != null ? "classOrganization=" + classOrganization + ", " : "")
//				+ (locationOrganization != null ? "locationOrganization" + locationOrganization + "," : "")
				+ "]";
	}
}
