package ch.cern.eam.wshub.core.services.workorders.impl;

import java.math.BigInteger;

import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.services.workorders.WorkOrderScheduleService;
import ch.cern.eam.wshub.core.services.workorders.entities.WorkOrderSchedule;
import ch.cern.eam.wshub.core.tools.ApplicationData;
import ch.cern.eam.wshub.core.tools.InforException;
import ch.cern.eam.wshub.core.tools.Tools;
import net.datastream.schemas.mp_fields.*;
import net.datastream.schemas.mp_functions.mp0057_001.MP0057_AddWorkOrderSchedule_001;
import net.datastream.schemas.mp_results.mp0057_001.MP0057_AddWorkOrderSchedule_001_Result;
import net.datastream.schemas.mp_functions.mp0059_001.MP0059_DeleteWorkOrderSchedule_001;
import net.datastream.schemas.mp_functions.mp7925_001.MP7925_GetWorkOrderSchedule_001;
import net.datastream.schemas.mp_functions.mp0058_001.MP0058_SyncWorkOrderSchedule_001;
import net.datastream.wsdls.inforws.InforWebServicesPT;

public class WorkOrderScheduleServiceImpl implements WorkOrderScheduleService {

    private Tools tools;
    private InforWebServicesPT inforws;
    private ApplicationData applicationData;

    public WorkOrderScheduleServiceImpl(ApplicationData applicationData, Tools tools, InforWebServicesPT inforWebServicesToolkitClient) {
        this.applicationData = applicationData;
        this.tools = tools;
        this.inforws = inforWebServicesToolkitClient;
    }

    public String createWorkOrderSchedule(InforContext inforContext, WorkOrderSchedule workOrderSchedule) throws InforException {
        net.datastream.schemas.mp_entities.workorderschedule_001.WorkOrderSchedule workOrderScheduleInfor = new net.datastream.schemas.mp_entities.workorderschedule_001.WorkOrderSchedule();

        if (workOrderSchedule.getScheduleCode() == null) {
            workOrderSchedule.setScheduleCode("0");
        }

        tools.getInforFieldTools().transformWSHubObject(workOrderScheduleInfor, workOrderSchedule, inforContext);

        MP0057_AddWorkOrderSchedule_001 addWorkOrderSchedule = new MP0057_AddWorkOrderSchedule_001();
        addWorkOrderSchedule.setWorkOrderSchedule(workOrderScheduleInfor);

        MP0057_AddWorkOrderSchedule_001_Result result = tools.performInforOperation(inforContext, inforws::addWorkOrderScheduleOp, addWorkOrderSchedule);
        return result.getResultData().getWOSCHEDULEID().getWOSCHEDULECODE();
    }

    public String updateWorkOrderSchedule(InforContext inforContext,  WorkOrderSchedule scheduleParam) throws InforException {
        // GET CURRENT SCHEDULE
        MP7925_GetWorkOrderSchedule_001 getSchedule = new MP7925_GetWorkOrderSchedule_001();
        getSchedule.setWOSCHEDULEID(createScheduleId(inforContext, scheduleParam.getWorkOrderNumber(), scheduleParam.getActivityCode(), scheduleParam.getScheduleCode()));
      
        net.datastream.schemas.mp_entities.workorderschedule_002.WorkOrderSchedule inforSchedule =
            tools.performInforOperation(inforContext, inforws::getWorkOrderScheduleOp, getSchedule)
                 .getResultData().getWorkOrderSchedule();

        // OVERLAY CHANGES OVER CURRENT SCHEULD
        tools.getInforFieldTools().transformWSHubObject(inforSchedule, scheduleParam, inforContext);

        // MP7925_GetWorkOrderSchedule_001 returns a .workorderschedule_002 we need a .workorderschedule_001
        // ideally MP0058_SyncWorkOrderSchedule_002 would be part of the InforWebServices package
        // TODO: ask Luckz to include this
        net.datastream.schemas.mp_entities.workorderschedule_001.WorkOrderSchedule mappedSchedule = map(inforSchedule);

        // UPDATE SCHEDULE
        MP0058_SyncWorkOrderSchedule_001 updateSchedule = new MP0058_SyncWorkOrderSchedule_001();
        updateSchedule.setWorkOrderSchedule(mappedSchedule);

        tools.performInforOperation(inforContext, inforws::syncWorkOrderScheduleOp, updateSchedule);

        return "Success";
    }

    private net.datastream.schemas.mp_entities.workorderschedule_001.WorkOrderSchedule map(net.datastream.schemas.mp_entities.workorderschedule_002.WorkOrderSchedule source) {
        net.datastream.schemas.mp_entities.workorderschedule_001.WorkOrderSchedule target = new net.datastream.schemas.mp_entities.workorderschedule_001.WorkOrderSchedule();
        
        // TODO: mapp start and end times
        
        target.setDEPARTMENTID(source.getDEPARTMENTID());
        target.setEMPLOYEE(source.getEMPLOYEE());
        target.setENTEREDBY(source.getENTEREDBY());
        target.setFROZEN(source.getFROZEN());
        target.setMAINTENANCEEQUIPMENTID(source.getMAINTENANCEEQUIPMENTID());
        target.setRecordid(source.getRecordid());
        target.setSCHEDULEDDATE(source.getSCHEDULEDDATE());
        target.setSCHEDULEDHOURS(source.getSCHEDULEDHOURS());
        target.setSHIFTID(source.getSHIFTID());
        target.setTRADEID(source.getTRADEID());
        target.setWOSCHEDULECOMMENT(source.getWOSCHEDULECOMMENT());
        target.setWOSCHEDULEID(source.getWOSCHEDULEID());

        return target;
    }

    public String deleteWorkOrderSchedule(InforContext inforContext,  String workOrderNumber, BigInteger activityCode, String workOrderScheduleCode) throws InforException {
        
        MP0059_DeleteWorkOrderSchedule_001 deleteSchedule = new MP0059_DeleteWorkOrderSchedule_001();
        deleteSchedule.setWOSCHEDULEID(createScheduleId(inforContext, workOrderNumber, activityCode, workOrderScheduleCode));

        tools.performInforOperation(inforContext, inforws::deleteWorkOrderScheduleOp, deleteSchedule);

        return "Success";
    }

    private WOSCHEDULEID createScheduleId(InforContext inforContext, String workOrderNumber, BigInteger activityCode, String workOrderScheduleCode){
        WOSCHEDULEID result = new WOSCHEDULEID();
		result.setACTIVITYID(new ACTIVITYID());
		result.getACTIVITYID().setACTIVITYCODE(new ACTIVITYCODE());
		result.getACTIVITYID().getACTIVITYCODE().setValue(activityCode.longValue());
		result.getACTIVITYID().setWORKORDERID(new WOID_Type());
        result.getACTIVITYID().getWORKORDERID().setORGANIZATIONID(tools.getOrganization(inforContext));
        result.getACTIVITYID().getWORKORDERID().setJOBNUM(workOrderNumber);
		result.setWOSCHEDULECODE(workOrderScheduleCode);
        return result;
    }

}
