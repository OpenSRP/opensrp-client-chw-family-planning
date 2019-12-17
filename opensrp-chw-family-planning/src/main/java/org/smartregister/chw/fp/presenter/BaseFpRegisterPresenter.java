package org.smartregister.chw.fp.presenter;

import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.fp.contract.BaseFpRegisterContract;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.chw.fp.util.FpJsonFormUtils;
import org.smartregister.fp.R;
import org.smartregister.util.JsonFormUtils;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.annotations.Nullable;
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
    public void startForm(String formName, String entityId, String payloadType, String dob) throws Exception {
        if (StringUtils.isBlank(entityId)) {
            return;
        }

        JSONObject form = model.getFormAsJson(formName, entityId);
        try {
            JSONObject stepOne = form.getJSONObject(JsonFormUtils.STEP1);
            JSONArray jsonArray = stepOne.getJSONArray(JsonFormUtils.FIELDS);
            int age = new Period(new DateTime(dob), new DateTime()).getYears();
            FpJsonFormUtils.updateFormField(jsonArray, FamilyPlanningConstants.DBConstants.AGE, String.valueOf(age));

        } catch (JSONException e) {
            Timber.e(e);
        }
        getView().startFormActivity(form);
    }

    @Override
    public void saveForm(String jsonString) {
        try {
            if (getView() != null)
                getView().showProgressDialog(R.string.saving_dialog_title);
            interactor.saveRegistration(jsonString, this);
        } catch (Exception e) {
            Timber.e(TAG, Log.getStackTraceString(e));
        }
    }

    @Override
    public void onRegistrationSaved() {
        if (getView() != null) {
            getView().onFormSaved();
        }
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

    @Nullable
    private BaseFpRegisterContract.View getView() {
        if (viewReference != null)
            return viewReference.get();
        else
            return null;
    }
}
