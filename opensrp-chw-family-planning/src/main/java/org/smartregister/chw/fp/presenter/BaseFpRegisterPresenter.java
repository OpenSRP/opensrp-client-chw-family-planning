package org.smartregister.chw.fp.presenter;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.fp.contract.BaseFpRegisterContract;
import org.smartregister.chw.fp.interactor.BaseFpRegisterInteractor;

import java.lang.ref.WeakReference;
import java.util.List;

public class BaseFpRegisterPresenter implements BaseFpRegisterContract.Presenter, BaseFpRegisterContract.InteractorCallBack  {

    public static final String TAG = BaseFpRegisterPresenter.class.getName();

    protected WeakReference<BaseFpRegisterContract.View> viewReference;
    protected BaseFpRegisterContract.Interactor interactor;
    protected BaseFpRegisterContract.Model model;

    public BaseFpRegisterPresenter(BaseFpRegisterContract.View view, BaseFpRegisterContract.Model model,BaseFpRegisterContract.Interactor interactor) {
        viewReference = new WeakReference<>(view);
        this.interactor = interactor;
        this.model = model;
    }

    @Override
    public void saveLanguage(String language) {
//        implement
    }

    @Override
    public void startForm(String formName, String entityId, String metadata, String currentLocationId) throws Exception {
//        implement
    }

    @Override
    public void saveForm(String jsonString) {
//        implement
    }

    @Override
    public void closeFamilyRecord(String jsonString) {
//        implement
    }

    @Override
    public void onUniqueIdFetched(Triple<String, String, String> triple, String entityId) {
//        implement
    }

    @Override
    public void onNoUniqueId() {
//        implement
    }

    @Override
    public void onRegistrationSaved(boolean isEdit) {
//        implement
    }

    @Override
    public void registerViewConfigurations(List<String> list) {
//        implement
    }

    @Override
    public void unregisterViewConfiguration(List<String> list) {
//        implement
    }

    @Override
    public void onDestroy(boolean b) {
//        implement
    }

    @Override
    public void updateInitials() {
//        implement
    }
}
