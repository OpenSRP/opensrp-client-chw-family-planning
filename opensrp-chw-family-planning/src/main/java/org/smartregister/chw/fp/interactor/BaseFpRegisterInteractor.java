package org.smartregister.chw.fp.interactor;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.fp.contract.BaseFpRegisterContract;

public class BaseFpRegisterInteractor implements BaseFpRegisterContract.Interactor {
    @Override
    public void onDestroy(boolean isChangingConfiguration) {
//        implement
    }

    @Override
    public void getNextUniqueId(Triple<String, String, String> triple, BaseFpRegisterContract.InteractorCallBack callBack) {
//        implement
    }

    @Override
    public void saveRegistration(BaseFpRegisterContract.InteractorCallBack callBack) {
//        implement
    }

    @Override
    public void removeFamilyFromRegister(String closeFormJsonString, String providerId) {
//        implement
    }
}
