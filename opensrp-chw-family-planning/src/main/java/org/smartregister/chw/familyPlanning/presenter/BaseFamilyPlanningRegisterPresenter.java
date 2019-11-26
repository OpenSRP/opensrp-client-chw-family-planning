package org.smartregister.chw.familyPlanning.presenter;

import org.apache.commons.lang3.tuple.Triple;
import org.smartregister.chw.familyPlanning.contract.BaseFamilyPlanningRegisterContract;
import org.smartregister.chw.familyPlanning.interactor.BaseFamilyPlanningRegisterInteractor;

import java.lang.ref.WeakReference;
import java.util.List;

public class BaseFamilyPlanningRegisterPresenter implements BaseFamilyPlanningRegisterContract.Presenter, BaseFamilyPlanningRegisterContract.InteractorCallBack  {

    public static final String TAG = BaseFamilyPlanningRegisterPresenter.class.getName();

    protected WeakReference<BaseFamilyPlanningRegisterContract.View> viewReference;
    protected BaseFamilyPlanningRegisterContract.Interactor interactor;
    protected BaseFamilyPlanningRegisterContract.Model model;

    public BaseFamilyPlanningRegisterPresenter(BaseFamilyPlanningRegisterContract.View view, BaseFamilyPlanningRegisterContract.Model model) {
        viewReference = new WeakReference<>(view);
        interactor = new BaseFamilyPlanningRegisterInteractor();
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
    public void saveForm(String jsonString, boolean isEditMode) {
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
