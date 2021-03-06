package org.smartregister.chw.fp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.MenuRes;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.v4.app.Fragment;

import com.vijay.jsonwizard.constants.JsonFormConstants;
import com.vijay.jsonwizard.domain.Form;

import org.json.JSONException;
import org.json.JSONObject;
import org.smartregister.chw.fp.contract.BaseFpRegisterContract;
import org.smartregister.chw.fp.fragment.BaseFpRegisterFragment;
import org.smartregister.chw.fp.interactor.BaseFpRegisterInteractor;
import org.smartregister.chw.fp.listener.BaseFpBottomNavigationListener;
import org.smartregister.chw.fp.model.BaseFpRegisterModel;
import org.smartregister.chw.fp.presenter.BaseFpRegisterPresenter;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.fp.R;
import org.smartregister.helper.BottomNavigationHelper;
import org.smartregister.listener.BottomNavigationListener;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.Arrays;
import java.util.List;

import timber.log.Timber;

public class BaseFpRegisterActivity extends BaseRegisterActivity implements BaseFpRegisterContract.View {

    protected String BASE_ENTITY_ID;
    protected String DOB;
    protected String ACTION;
    protected String FORM_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BASE_ENTITY_ID = getIntent().getStringExtra(FamilyPlanningConstants.ActivityPayload.BASE_ENTITY_ID);
        DOB = getIntent().getStringExtra(FamilyPlanningConstants.ActivityPayload.DOB);
        ACTION = getIntent().getStringExtra(FamilyPlanningConstants.ActivityPayload.ACTION);
        FORM_NAME = getIntent().getStringExtra(FamilyPlanningConstants.ActivityPayload.FP_FORM_NAME);
        onStartActivityWithAction();
    }

    /**
     * Process a payload when an activity is started with an action
     */
    protected void onStartActivityWithAction() {
        if (FORM_NAME != null && ACTION != null) {
            startFormActivity(FORM_NAME, BASE_ENTITY_ID, ACTION);
        }
    }

    @Override
    public void startRegistration() {
        startFormActivity(FORM_NAME, null, null);
    }

    @Override
    public void startFormActivity(String formName, String entityId, String payloadType) {
        try {
            if (mBaseFragment instanceof BaseFpRegisterFragment) {

                presenter().startForm(formName, entityId, payloadType, DOB, getFpFormForEdit());
            }
        } catch (Exception e) {
            Timber.e(e);
            displayToast(getString(R.string.error_unable_to_start_form));
        }
    }

    public JSONObject getFpFormForEdit() {
        return null;
    }

    @Override
    public void startFormActivity(JSONObject jsonForm) {
        Intent intent = new Intent(this, BaseFpRegisterActivity.class);
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

    @Override
    public void onFormSaved() {
        hideProgressDialog();
    }

    @Override
    protected void onActivityResultExtended(int requestCode, int resultCode, Intent data) {
        if (requestCode == FamilyPlanningConstants.REQUEST_CODE_GET_JSON && resultCode == RESULT_OK) {
            presenter().saveForm(data.getStringExtra(FamilyPlanningConstants.JsonFromExtra.JSON));
        }
    }

    protected Activity getFpRegisterActivity() {
        return this;
    }

    @Override
    public List<String> getViewIdentifiers() {
        return Arrays.asList(FamilyPlanningConstants.CONFIGURATION.FAMILY_PLANNING_REGISTER);
    }

    /**
     * Override this to subscribe to bottom navigation
     */
    @Override
    protected void registerBottomNavigation() {
        bottomNavigationHelper = new BottomNavigationHelper();
        bottomNavigationView = findViewById(org.smartregister.R.id.bottom_navigation);

        if (bottomNavigationView != null) {
            bottomNavigationView.setLabelVisibilityMode(LabelVisibilityMode.LABEL_VISIBILITY_LABELED);
            bottomNavigationView.getMenu().removeItem(org.smartregister.R.id.action_clients);
            bottomNavigationView.getMenu().removeItem(R.id.action_register);
            bottomNavigationView.getMenu().removeItem(org.smartregister.R.id.action_search);
            bottomNavigationView.getMenu().removeItem(org.smartregister.R.id.action_library);
            bottomNavigationView.inflateMenu(getMenuResource());
            bottomNavigationHelper.disableShiftMode(bottomNavigationView);
            BottomNavigationListener familyBottomNavigationListener = new BaseFpBottomNavigationListener(this);
            bottomNavigationView.setOnNavigationItemSelectedListener(familyBottomNavigationListener);
        }
    }

    @MenuRes
    public int getMenuResource() {
        return R.menu.bottom_nav_family_menu;
    }

    @Override
    protected void initializePresenter() {
        presenter = new BaseFpRegisterPresenter(this, new BaseFpRegisterModel(), new BaseFpRegisterInteractor());
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == FamilyPlanningConstants.REQUEST_CODE_GET_JSON) {
            try {
                String jsonString = data.getStringExtra(FamilyPlanningConstants.JsonFromExtra.JSON);
                JSONObject form = new JSONObject(jsonString);
                presenter().saveForm(form.toString());
            } catch (JSONException e) {
                Timber.e(e);
                displayToast(getString(R.string.error_unable_to_save_form));
            }
        } else {
            getFpRegisterActivity().finish();
        }
    }
}
