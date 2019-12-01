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
import org.smartregister.AllConstants;
import org.smartregister.Context;
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
import org.smartregister.repository.BaseRepository;
import org.smartregister.view.activity.BaseRegisterActivity;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import timber.log.Timber;

import static org.smartregister.chw.fp.util.FpUtil.getClientProcessorForJava;
import static org.smartregister.chw.fp.util.FpUtil.getSyncHelper;
import static org.smartregister.util.Utils.getAllSharedPreferences;

public class BaseFpRegisterActivity extends BaseRegisterActivity implements BaseFpRegisterContract.View {
    public static final String TAG = BaseFpRegisterActivity.class.getCanonicalName();

    protected String BASE_ENTITY_ID;
    protected String ACTION;
    protected String FORM_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BASE_ENTITY_ID = getIntent().getStringExtra(FamilyPlanningConstants.ACTIVITY_PAYLOAD.BASE_ENTITY_ID);
        ACTION = getIntent().getStringExtra(FamilyPlanningConstants.ACTIVITY_PAYLOAD.ACTION);
        FORM_NAME = getIntent().getStringExtra(FamilyPlanningConstants.ACTIVITY_PAYLOAD.FP_FORM_NAME);
        onStartActivityWithAction();
    }

    /**
     * Process a payload when an activity is started with an action
     */
    protected void onStartActivityWithAction() {
        if (FORM_NAME != null && ACTION != null) {
            startFormActivity(FORM_NAME, BASE_ENTITY_ID, null);
        }
    }

    @Override
    public void startRegistration() {
        startFormActivity(FORM_NAME, null, null);
    }

    @Override
    public void startFormActivity(String formName, String entityId, String metaData) {
        try {
            if (mBaseFragment instanceof BaseFpRegisterFragment) {
                String locationId = Context.getInstance().allSharedPreferences().getPreference(AllConstants.CURRENT_LOCATION_ID);
                presenter().startForm(formName, entityId, metaData, locationId);
            }
        } catch (Exception e) {
            Timber.e(e);
            displayToast(getString(R.string.error_unable_to_start_form));
        }
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
        return Arrays.asList(FamilyPlanningConstants.CONFIGURATION.FAMILY_PLANNING_REGISTRATION);
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
//                process family planning form
                presenter().saveForm(form.toString());
            } catch (JSONException e) {
                Timber.e(e);
                displayToast(getString(R.string.error_unable_to_save_form));
            }
            startClientProcessing();
        } else {
            getFpRegisterActivity().finish();
        }
    }

    public void startClientProcessing() {
        try {
            long lastSyncTimeStamp = getAllSharedPreferences().fetchLastUpdatedAtDate(0);
            Date lastSyncDate = new Date(lastSyncTimeStamp);
            getClientProcessorForJava().processClient(getSyncHelper().getEvents(lastSyncDate, BaseRepository.TYPE_Unprocessed));
            getAllSharedPreferences().saveLastUpdatedAtDate(lastSyncDate.getTime());
        } catch (Exception e) {
            Timber.d(e);
        }

    }
}
