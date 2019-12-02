package org.smartregister.chw.fp.presenter;

import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.MemberObject;

import java.lang.ref.WeakReference;

public class BaseFpProfilePresenter implements BaseFpProfileContract.Presenter {
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
    public void refreshProfileInfo() {

    }

    @Override
    public void updateMedicalHistory() {

    }

    @Override
    public void updateUpcomingServices() {

    }

    @Override
    public void refreshFamilyStatus() {

    }

    @Override
    public void onDestroy(boolean b) {

    }
}
