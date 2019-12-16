package org.smartregister.chw.fp.interactor;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.anc.contract.BaseAncHomeVisitContract;
import org.smartregister.chw.anc.domain.Visit;
import org.smartregister.chw.anc.interactor.BaseAncHomeVisitInteractor;
import org.smartregister.chw.anc.model.BaseAncHomeVisitAction;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.clientandeventmodel.Event;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class BaseFpFollowUpVisitInteractor extends BaseAncHomeVisitInteractor {

    private String motherID;
    private String parentVisitID;

    @Override
    public void submitVisit(boolean editMode, String memberID, Map<String, BaseAncHomeVisitAction> map, BaseAncHomeVisitContract.InteractorCallBack callBack) {
        motherID = memberID;
        super.submitVisit(editMode, memberID, map, callBack);
    }


    @Override
    protected String getParentVisitEventID(Visit visit, String parentEventType) {
        if (StringUtils.isBlank(parentEventType))
            parentVisitID = visit.getVisitId();

        return visit.getVisitId().equalsIgnoreCase(parentVisitID) ? null : parentVisitID;
    }

    protected void prepareEvent(Event baseEvent) {
        if (baseEvent != null) {
            // add anc date obs and last
            List<Object> list = new ArrayList<>();
            list.add(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));

        }
    }

    @Override
    protected void prepareSubEvent(Event baseEvent) {
        if (baseEvent != null) {
            List<Object> mother_id = new ArrayList<>();
            mother_id.add(motherID);
        }
    }

    @Override
    protected String getEncounterType() {
        return FamilyPlanningConstants.EVENT_TYPE.FP_FOLLOW_UP_VISIT;
    }

    @Override
    protected String getTableName() {
        return FamilyPlanningConstants.DBConstants.FAMILY_PLANNING_TABLE;
    }


}
