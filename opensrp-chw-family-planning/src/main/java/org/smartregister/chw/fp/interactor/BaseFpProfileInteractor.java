package org.smartregister.chw.fp.interactor;

import android.support.annotation.VisibleForTesting;

import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.util.AppExecutors;
import org.smartregister.domain.AlertStatus;

import java.util.Date;

public class BaseFpProfileInteractor implements BaseFpProfileContract.Interactor {
    protected AppExecutors appExecutors;

    @VisibleForTesting
    BaseFpProfileInteractor(AppExecutors appExecutors) {
        this.appExecutors = appExecutors;
    }

    public BaseFpProfileInteractor() {
        this(new AppExecutors());
    }

    @Override
    public void refreshProfileView(FpMemberObject fpMemberObject, boolean isForEdit, BaseFpProfileContract.InteractorCallback callback) {
        Runnable runnable = () -> appExecutors.mainThread().execute(() -> callback.refreshProfileTopSection(fpMemberObject));
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void updateProfileFpStatusInfo(FpMemberObject fpMemberObject, BaseFpProfileContract.InteractorCallback callback) {
        Runnable runnable = () -> appExecutors.mainThread().execute(() -> {
            callback.refreshFamilyStatus(AlertStatus.normal);
            callback.refreshUpComingServicesStatus("Family Planning Followup Visit", AlertStatus.normal, new Date());
            callback.refreshMedicalHistory(new Date());
        });
        appExecutors.diskIO().execute(runnable);
    }
}
