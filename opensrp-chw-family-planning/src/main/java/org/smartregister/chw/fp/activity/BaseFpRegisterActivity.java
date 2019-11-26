package org.smartregister.chw.fp.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

import org.json.JSONObject;
import org.smartregister.AllConstants;
import org.smartregister.Context;
import org.smartregister.chw.fp.contract.BaseFpRegisterContract;
import org.smartregister.chw.fp.fragment.BaseFpRegisterFragment;
import org.smartregister.chw.fp.model.BaseFpRegisterModel;
import org.smartregister.chw.fp.presenter.BaseFpRegisterPresenter;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.fp.R;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.List;

public class BaseFpRegisterActivity extends BaseRegisterActivity implements BaseFpRegisterContract.View {
    public static final String TAG = BaseFpRegisterActivity.class.getCanonicalName();

    @Override
    public void startRegistration() {
        startFormActivity(getRegistrationForm(), null, null);
    }

    public String getRegistrationForm() {
        return FamilyPlanningConstants.Forms.FAMILY_PLANNING_REGISTRATION_FORM;
    }

    @Override
    public void startFormActivity(String formName, String entityId, String metaData) {
        try {
            if (mBaseFragment instanceof BaseFpRegisterFragment) {
                presenter().startForm(formName, entityId, metaData, getLocationID());
            }
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            displayToast(getString(R.string.error_unable_to_start_form));
        }
    }

    protected String getLocationID() {
        return Context.getInstance().allSharedPreferences().getPreference(AllConstants.CURRENT_LOCATION_ID);
    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        Intent intent = new Intent(this, getFamilyFormActivity());
        intent.putExtra(FamilyPlanningConstants.JsonFromExtra.JSON, jsonForm.toString());

        if (getFormConfig() != null) {
            intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, getFormConfig());
        }

        startActivityForResult(intent, FamilyPlanningConstants.REQUEST_CODE_GET_JSON);
    }

    @Override
    public Form getFormConfig() {
        return null;
    }

    public Class getFamilyFormActivity() {
        return BaseFpRegisterActivity.class;
    }

    @Override
    protected void onActivityResultExtended(int requestCode, int resultCode, Intent data) {
        if (requestCode == FamilyPlanningConstants.REQUEST_CODE_GET_JSON && resultCode == RESULT_OK) {
            try {
                String jsonString = data.getStringExtra(FamilyPlanningConstants.JsonFromExtra.JSON);
                Log.d("JSONResult", jsonString);

                JSONObject form = new JSONObject(jsonString);
                if (form.getString(FamilyPlanningConstants.ENCOUNTER_TYPE).equals(getRegisterEventType())) {
                    presenter().saveForm(jsonString, false);
                }
            } catch (Exception e) {
                Log.e(TAG, Log.getStackTraceString(e));
            }

        }
    }

    @Override
    public List<String> getViewIdentifiers() {
        return null;
    }

    /**
     * Returns the event type for a fp registration
     *
     * @return
     */
    public String getRegisterEventType() {
        return FamilyPlanningConstants.EventType.FAMILY_PLANNING_REGISTRATION;
    }

    /**
     * Override this to subscribe to bottom navigation
     */
    @Override
    protected void registerBottomNavigation() {
//        implement BottomNavigation
    }

    @Override
    protected void initializePresenter() {
        presenter = new BaseFpRegisterPresenter(this, new BaseFpRegisterModel());
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new BaseFpRegisterFragment();
    }

    @Override
    protected Fragment[] getOtherFragments() {
        return new Fragment[0];
    }

    @Override
    public BaseFpRegisterContract.Presenter presenter() {
        return (BaseFpRegisterContract.Presenter) presenter;
    }

}
