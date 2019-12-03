package org.smartregister.chw.fp.presenter;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.smartregister.chw.fp.contract.BaseFpRegisterContract;
import org.smartregister.fp.R;

import java.lang.ref.WeakReference;
import java.util.List;

import timber.log.Timber;

public class BaseFpRegisterPresenter implements BaseFpRegisterContract.Presenter, BaseFpRegisterContract.InteractorCallBack {

    public static final String TAG = BaseFpRegisterPresenter.class.getName();

    protected WeakReference<BaseFpRegisterContract.View> viewReference;
    protected BaseFpRegisterContract.Model model;
    private BaseFpRegisterContract.Interactor interactor;

    public BaseFpRegisterPresenter(BaseFpRegisterContract.View view, BaseFpRegisterContract.Model model, BaseFpRegisterContract.Interactor interactor) {
        viewReference = new WeakReference<>(view);
        this.interactor = interactor;
        this.model = model;
    }

    @Override
    public void startForm(String formName, String entityId, String metadata, String currentLocationId) throws Exception {
        if (StringUtils.isBlank(entityId)) {
            return;
        }

        JSONObject form = model.getFormAsJson(formName, entityId, "2020");
        getView().startFormActivity(form);
    }

    @Override
    public void saveForm(String jsonString) {
        try {
            getView().showProgressDialog(R.string.saving_dialog_title);
            interactor.saveRegistration(jsonString, this);
        } catch (Exception e) {
            Timber.e(TAG, Log.getStackTraceString(e));
        }
    }

    @Override
    public void onRegistrationSaved() {
        getView().hideProgressDialog();

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

    private BaseFpRegisterContract.View getView() {
        if (viewReference != null)
            return viewReference.get();
        else
            return null;
    }
}
