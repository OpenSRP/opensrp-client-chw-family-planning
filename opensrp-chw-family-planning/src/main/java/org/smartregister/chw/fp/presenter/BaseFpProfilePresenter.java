package org.smartregister.chw.fp.presenter;

import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.MemberObject;
import org.smartregister.domain.AlertStatus;
import org.smartregister.view.contract.BaseProfileContract;

import java.lang.ref.WeakReference;
import java.util.Date;

public class BaseFpProfilePresenter implements BaseProfileContract, BaseFpProfileContract.Presenter, BaseFpProfileContract.InteractorCallback {
    private WeakReference<BaseFpProfileContract.View> view;
    private MemberObject memberObject;
    private BaseFpProfileContract.Interactor interactor;

    public BaseFpProfilePresenter(BaseFpProfileContract.View view, BaseFpProfileContract.Interactor interactor, MemberObject memberObject) {
        this.view = new WeakReference<>(view);
        this.interactor = interactor;
        this.memberObject = memberObject;
    }

    @Override
    public BaseFpProfileContract.View getView() {
        if (view != null && view.get() != null)
            return view.get();

        return null;
    }

    @Override
    public void resetProfileInfo() {

    }

    @Override
    public void refreshProfileData() {
        if (getView() != null) {
            getView().showProgressBar(true);
        }
        interactor.refreshProfileView(memberObject, false, this);
    }

    @Override
    public void fetchMemberDetails(String memberId) {

    }

    @Override
    public void onDestroy(boolean b) {

    }

    @Override
    public void refreshProfileTopSection(MemberObject memberObject) {
        if (getView() != null) {
            getView().setProfileViewDetails(memberObject);
            getView().showProgressBar(false);
        }
    }

    @Override
    public void refreshLastVisit(Date lastVisitDate) {
        if (getView() != null) {
            getView().setLastVisit(lastVisitDate);
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
