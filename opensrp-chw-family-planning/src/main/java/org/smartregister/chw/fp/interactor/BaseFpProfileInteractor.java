package org.smartregister.chw.fp.interactor;

import android.support.annotation.VisibleForTesting;

import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.MemberObject;
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
    public void refreshProfileView(MemberObject memberObject, boolean isForEdit, BaseFpProfileContract.InteractorCallback callback) {
        Runnable runnable = () -> appExecutors.mainThread().execute(() -> callback.refreshProfileTopSection(memberObject));
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void resetProfileInfo(BaseFpProfileContract.InteractorCallback callback) {
        Runnable runnable = () -> appExecutors.mainThread().execute(() -> {
            callback.refreshFamilyStatus(AlertStatus.normal);
            callback.refreshLastVisit(new Date());
            callback.refreshUpComingServicesStatus("Family Planning Followup Visit", AlertStatus.normal, new Date());
        });
        appExecutors.diskIO().execute(runnable);
    }
}
