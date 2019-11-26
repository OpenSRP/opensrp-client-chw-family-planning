package org.smartregister.chw.familyPlanning.interactor;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.familyPlanning.contract.BaseFamilyPlanningRegisterContract;

public class BaseFamilyPlanningRegisterInteractor implements BaseFamilyPlanningRegisterContract.Interactor {
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
//        implement
    }

    @Override
    public void getNextUniqueId(Triple<String, String, String> triple, BaseFamilyPlanningRegisterContract.InteractorCallBack callBack) {
//        implement
    }

    @Override
    public void saveRegistration(BaseFamilyPlanningRegisterContract.InteractorCallBack callBack) {
//        implement
    }

    @Override
    public void removeFamilyFromRegister(String closeFormJsonString, String providerId) {
//        implement
    }
}
