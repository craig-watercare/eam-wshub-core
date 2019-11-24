package ch.cern.eam.wshub.core.services.material.impl;


import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.services.material.PartService;
import ch.cern.eam.wshub.core.services.material.entities.Part;
import ch.cern.eam.wshub.core.tools.ApplicationData;
import ch.cern.eam.wshub.core.annotations.BooleanType;
import ch.cern.eam.wshub.core.tools.InforException;
import ch.cern.eam.wshub.core.tools.Tools;
import net.datastream.schemas.mp_entities.part_001.UserDefinedFields;
import net.datastream.schemas.mp_fields.*;
import net.datastream.schemas.mp_functions.SessionType;
import net.datastream.schemas.mp_functions.mp0240_001.MP0240_AddPart_001;
import net.datastream.schemas.mp_functions.mp0241_001.MP0241_GetPart_001;
import net.datastream.schemas.mp_functions.mp0242_001.MP0242_SyncPart_001;
import net.datastream.schemas.mp_functions.mp0243_001.MP0243_DeletePart_001;
import net.datastream.schemas.mp_functions.mp2072_001.ChangePartNumber;
import net.datastream.schemas.mp_functions.mp2072_001.MP2072_ChangePartNumber_001;
import net.datastream.schemas.mp_results.mp0240_001.MP0240_AddPart_001_Result;
import net.datastream.schemas.mp_results.mp0241_001.MP0241_GetPart_001_Result;
import net.datastream.schemas.mp_results.mp0242_001.MP0242_SyncPart_001_Result;
import net.datastream.wsdls.inforws.InforWebServicesPT;
import javax.xml.ws.Holder;

public class PartServiceImpl implements PartService {

	private Tools tools;
	private InforWebServicesPT inforws;
	private ApplicationData applicationData;

	public PartServiceImpl(ApplicationData applicationData, Tools tools, InforWebServicesPT inforWebServicesToolkitClient) {
		this.applicationData = applicationData;
		this.tools = tools;
		this.inforws = inforWebServicesToolkitClient;
	}

	public Part readPart(InforContext context, String partCode) throws InforException {
		return tools.getInforFieldTools().transformInforObject(new Part(), readPartInfor(context, partCode));
	}

	private net.datastream.schemas.mp_entities.part_001.Part readPartInfor(InforContext context, String partCode) throws InforException {
		MP0241_GetPart_001 getPart = new MP0241_GetPart_001();
		getPart.setPARTID(new PARTID_Type());
		getPart.getPARTID().setORGANIZATIONID(tools.getOrganization(context));
		getPart.getPARTID().setPARTCODE(partCode);

		MP0241_GetPart_001_Result getPartResult = null;

		if (context.getCredentials() != null) {
			getPartResult = inforws.getPartOp(getPart, tools.getOrganizationCode(context),
					tools.createSecurityHeader(context), "TERMINATE", null,
					null, tools.getTenant(context));

		} else {
			getPartResult = inforws.getPartOp(getPart, tools.getOrganizationCode(context), null, null,
					new Holder<SessionType>(tools.createInforSession(context)), null, tools.getTenant(context));
		}

		return getPartResult.getResultData().getPart();
	}

	public String createPart(InforContext context, Part partParam) throws InforException {
		net.datastream.schemas.mp_entities.part_001.Part inforPart = new net.datastream.schemas.mp_entities.part_001.Part();
		//
		//
		//
		if (partParam.getCustomFields() != null && partParam.getCustomFields().length > 0) {
			if (partParam.getClassCode() != null && !partParam.getClassCode().trim().equals("")) {
				inforPart.setUSERDEFINEDAREA(
						tools.getCustomFieldsTools().getInforCustomFields(context, "PART", partParam.getClassCode()));
			} else {
				inforPart.setUSERDEFINEDAREA(tools.getCustomFieldsTools().getInforCustomFields(context, "PART", "*"));
			}
		}

		// POPULATE ALL OTHER FIELDS
		tools.getInforFieldTools().transformWSHubObject(inforPart, partParam, context);

		MP0240_AddPart_001 addPart = new MP0240_AddPart_001();
		addPart.setPart(inforPart);

		MP0240_AddPart_001_Result result = null;

		if (context.getCredentials() != null) {
			result = inforws.addPartOp(addPart, tools.getOrganizationCode(context),
					tools.createSecurityHeader(context), "TERMINATE", null,
					null, tools.getTenant(context));
		} else {
			result = inforws.addPartOp(addPart, tools.getOrganizationCode(context), null, null,
					new Holder<SessionType>(tools.createInforSession(context)), null, tools.getTenant(context));
		}

		return result.getPARTID().getPARTCODE();
	}

	public String updatePart(InforContext context, Part partParam) throws InforException {

		if (partParam.getNewCode() != null && !partParam.getNewCode().trim().equals("")) {

			MP2072_ChangePartNumber_001 changePartNumber = new MP2072_ChangePartNumber_001();
			changePartNumber.setChangePartNumber(new ChangePartNumber());

			changePartNumber.getChangePartNumber().setOLDPARTID(new PARTID_Type());
			changePartNumber.getChangePartNumber().getOLDPARTID().setORGANIZATIONID(tools.getOrganization(context));
			changePartNumber.getChangePartNumber().getOLDPARTID().setPARTCODE(partParam.getCode());

			changePartNumber.getChangePartNumber().setNEWPARTID(new PARTID_Type());
			changePartNumber.getChangePartNumber().getNEWPARTID().setORGANIZATIONID(tools.getOrganization(context));
			changePartNumber.getChangePartNumber().getNEWPARTID().setPARTCODE(partParam.getNewCode());

			if (context.getCredentials() != null) {
				inforws.changePartNumberOp(changePartNumber, tools.getOrganizationCode(context),
						tools.createSecurityHeader(context), "TERMINATE",
						null, null, tools.getTenant(context));
			} else {
				inforws.changePartNumberOp(changePartNumber, tools.getOrganizationCode(context), null, null,
						new Holder<SessionType>(tools.createInforSession(context)), null,
						tools.getTenant(context));
			}

			partParam.setCode(partParam.getNewCode());

		}

		net.datastream.schemas.mp_entities.part_001.Part inforPart = readPartInfor(context, partParam.getCode());

		if (partParam.getClassCode() != null && (inforPart.getCLASSID() == null
				|| !partParam.getClassCode().toUpperCase().equals(inforPart.getCLASSID().getCLASSCODE()))) {
			inforPart.setUSERDEFINEDAREA(
					tools.getCustomFieldsTools().getInforCustomFields(context, "PART", partParam.getClassCode().toUpperCase()));
		}

		// SET ALL PROPERTIES
		tools.getInforFieldTools().transformWSHubObject(inforPart, partParam, context);
		//
		// UPDATE PART
		//
		MP0242_SyncPart_001 syncPart = new MP0242_SyncPart_001();
		syncPart.setPart(inforPart);

		MP0242_SyncPart_001_Result result = null;

		if (context.getCredentials() != null) {
			result = inforws.syncPartOp(syncPart, tools.getOrganizationCode(context),
					tools.createSecurityHeader(context), "TERMINATE",
					null, null, tools.getTenant(context));
		} else {
			result = inforws.syncPartOp(syncPart, tools.getOrganizationCode(context), null, null,
					new Holder<SessionType>(tools.createInforSession(context)), null,
					tools.getTenant(context));
		}

		return result.getResultData().getPart().getPARTID().getPARTCODE();

	}

	public String deletePart(InforContext context, String partCode) throws InforException {
		MP0243_DeletePart_001 deletePart = new MP0243_DeletePart_001();
		deletePart.setPARTID(new PARTID_Type());
		deletePart.getPARTID().setORGANIZATIONID(tools.getOrganization(context));
		deletePart.getPARTID().setPARTCODE(partCode);

		if (context.getCredentials() != null) {
			inforws.deletePartOp(deletePart, tools.getOrganizationCode(context),
					tools.createSecurityHeader(context), "TERMINATE", null,
					null, tools.getTenant(context));
		} else {
			inforws.deletePartOp(deletePart, tools.getOrganizationCode(context), null, null,
					new Holder<SessionType>(tools.createInforSession(context)), null, tools.getTenant(context));
		}
		return partCode;
	}

}
