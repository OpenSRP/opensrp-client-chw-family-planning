package org.smartregister.chw.fp.presenter;

import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.domain.AlertStatus;
import org.smartregister.view.contract.BaseProfileContract;

import java.lang.ref.WeakReference;
import java.util.Date;

public class BaseFpProfilePresenter implements BaseProfileContract, BaseFpProfileContract.Presenter, BaseFpProfileContract.InteractorCallback {
    private WeakReference<BaseFpProfileContract.View> view;
    private FpMemberObject fpMemberObject;
    private BaseFpProfileContract.Interactor interactor;

    public BaseFpProfilePresenter(BaseFpProfileContract.View view, BaseFpProfileContract.Interactor interactor, FpMemberObject fpMemberObject) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
        this.fpMemberObject = fpMemberObject;
    }

    @Override
    public BaseFpProfileContract.View getView() {
        if (view != null && view.get() != null)
            return view.get();

        return null;
    }

    @Override
    public void refreshProfileData() {
        if (getView() != null) {
            getView().showProgressBar(true);
        }
        interactor.refreshProfileView(fpMemberObject, false, this);
    }

    @Override
    public void refreshProfileFpStatusInfo() {
        interactor.updateProfileFpStatusInfo(fpMemberObject, this);
    }

    @Override
    public void refreshMedicalHistory(Date lastVisitDate) {
        if (getView() != null) {
            getView().updateHasMedicalHistory(lastVisitDate);
        }
    }

    @Override
    public void refreshProfileTopSection(FpMemberObject fpMemberObject) {
        if (getView() != null) {
            getView().setProfileViewDetails(fpMemberObject);
            getView().showProgressBar(false);
        }
    }

    @Override
    public void refreshUpComingServicesStatus(String service, AlertStatus status, Date date) {
        if (getView() != null) {
            getView().setUpComingServicesStatus(service, status, date);
        }
    }

    @Override
    public void refreshFamilyStatus(AlertStatus status) {
        if (getView() != null) {
            getView().setFamilyStatus(status);
        }
    }
}
