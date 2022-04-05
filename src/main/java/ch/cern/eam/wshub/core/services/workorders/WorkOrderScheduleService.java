package ch.cern.eam.wshub.core.services.workorders;

import java.math.BigInteger;

import ch.cern.eam.wshub.core.annotations.Operation;
import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.interceptors.LogDataReferenceType;
import ch.cern.eam.wshub.core.services.INFOR_OPERATION;
import ch.cern.eam.wshub.core.services.workorders.entities.WorkOrderSchedule;
import ch.cern.eam.wshub.core.tools.InforException;

public interface WorkOrderScheduleService {

    @Operation(logOperation = INFOR_OPERATION.WORKORDER_SCHEDULE_CREATE, logDataReference1 = LogDataReferenceType.RESULT)
    String createWorkOrderSchedule(InforContext context,  WorkOrderSchedule workOrderSchedule) throws InforException;

    @Operation(logOperation = INFOR_OPERATION.WORKORDER_SCHEDULE_UPDATE, logDataReference1 = LogDataReferenceType.RESULT)
    String updateWorkOrderSchedule(InforContext context,  WorkOrderSchedule workOrderSchedule) throws InforException;

    @Operation(logOperation = INFOR_OPERATION.WORKORDER_SCHEDULE_DELETE, logDataReference1 = LogDataReferenceType.RESULT)
    String deleteWorkOrderSchedule(InforContext context,  String workOrderNumber, BigInteger activityCode, String workOrderScheduleCode) throws InforException;
}
