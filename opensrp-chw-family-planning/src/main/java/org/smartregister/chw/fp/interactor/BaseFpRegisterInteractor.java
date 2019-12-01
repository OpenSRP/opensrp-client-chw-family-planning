package org.smartregister.chw.fp.interactor;

import android.support.annotation.VisibleForTesting;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.fp.contract.BaseFpRegisterContract;
import org.smartregister.chw.fp.util.AppExecutors;
import org.smartregister.chw.fp.util.FpUtil;

public class BaseFpRegisterInteractor implements BaseFpRegisterContract.Interactor {

    private AppExecutors appExecutors;

    @VisibleForTesting
    BaseFpRegisterInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public BaseFpRegisterInteractor() {
        this(new AppExecutors());
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
//        implement
    }

    @Override
    public void getNextUniqueId(Triple<String, String, String> triple, BaseFpRegisterContract.InteractorCallBack callBack) {
//        implement
    }

    @Override
    public void saveRegistration(final String jsonString, final BaseFpRegisterContract.InteractorCallBack callBack) {

        Runnable runnable = () -> {
            try {
                FpUtil.saveFormEvent(jsonString);
            } catch (Exception e) {
                e.printStackTrace();
            }

            appExecutors.mainThread().execute(() -> callBack.onRegistrationSaved());
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void removeFamilyFromRegister(String closeFormJsonString, String providerId) {
//        implement
    }
}
