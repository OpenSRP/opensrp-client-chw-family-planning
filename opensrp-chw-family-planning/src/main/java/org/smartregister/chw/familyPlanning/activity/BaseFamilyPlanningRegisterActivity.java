package org.smartregister.chw.familyPlanning.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

import org.json.JSONObject;
import org.smartregister.AllConstants;
import org.smartregister.Context;
import org.smartregister.chw.familyPlanning.contract.BaseFamilyPlanningRegisterContract;
import org.smartregister.chw.familyPlanning.fragment.BaseFamilyPlanningRegisterFragment;
import org.smartregister.chw.familyPlanning.model.BaseFamilyPlanningRegisterModel;
import org.smartregister.chw.familyPlanning.presenter.BaseFamilyPlanningRegisterPresenter;
import org.smartregister.chw.familyPlanning.util.Constants;
import org.smartregister.familyPlanning.R;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.List;

public class BaseFamilyPlanningRegisterActivity extends BaseRegisterActivity implements BaseFamilyPlanningRegisterContract.View {
    public static final String TAG = BaseFamilyPlanningRegisterActivity.class.getCanonicalName();

    @Override
    public void startRegistration() {
        startFormActivity(getRegistrationForm(), null, null);
    }

    public String getRegistrationForm() {
        return Constants.FORMS.FAMILY_PLANNING_REGISTRATION_FORM;
    }

    @Override
    public void startFormActivity(String formName, String entityId, String metaData) {
        try {
            if (mBaseFragment instanceof BaseFamilyPlanningRegisterFragment) {
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
        intent.putExtra(Constants.JSON_FORM_EXTRA.JSON, jsonForm.toString());

        if (getFormConfig() != null) {
            intent.putExtra(JsonFormConstants.JSON_FORM_KEY.FORM, getFormConfig());
        }

        startActivityForResult(intent, Constants.REQUEST_CODE_GET_JSON);
    }

    @Override
    public Form getFormConfig() {
        return null;
    }

    public Class getFamilyFormActivity() {
        return BaseFamilyPlanningRegisterActivity.class;
    }

    @Override
    protected void onActivityResultExtended(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.REQUEST_CODE_GET_JSON && resultCode == RESULT_OK) {
            try {
                String jsonString = data.getStringExtra(Constants.JSON_FORM_EXTRA.JSON);
                Log.d("JSONResult", jsonString);

                JSONObject form = new JSONObject(jsonString);
                if (form.getString(Constants.ENCOUNTER_TYPE).equals(getRegisterEventType())) {
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
     * Returns the event type for a familyPlanning registration
     *
     * @return
     */
    public String getRegisterEventType() {
        return Constants.EVENT_TYPE.FAMILY_PLANNING_REGISTRATION;
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
        presenter = new BaseFamilyPlanningRegisterPresenter(this, new BaseFamilyPlanningRegisterModel());
    }

    @Override
    protected BaseRegisterFragment getRegisterFragment() {
        return new BaseFamilyPlanningRegisterFragment();
    }

    @Override
    protected Fragment[] getOtherFragments() {
        return new Fragment[0];
    }

    @Override
    public BaseFamilyPlanningRegisterContract.Presenter presenter() {
        return (BaseFamilyPlanningRegisterContract.Presenter) presenter;
    }

}
