package org.smartregister.chw.familyPlanning.fragment;

import org.smartregister.chw.familyPlanning.contract.BaseFamilyPlanningRegisterFragmentContract;
import org.smartregister.chw.familyPlanning.model.BaseFamilyPlanningRegisterFragmentModel;
import org.smartregister.chw.familyPlanning.presenter.BaseFamilyPlanningRegisterFragmentPresenter;
import org.smartregister.chw.familyPlanning.provider.BaseFamilyPlanningRegisterProvider;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.Set;

public class BaseFamilyPlanningRegisterFragment extends BaseRegisterFragment implements BaseFamilyPlanningRegisterFragmentContract.View {
    @Override
    public void initializeAdapter(Set<View> visibleColumns) {
        BaseFamilyPlanningRegisterProvider baseFamilyPlanningRegisterProvider = new BaseFamilyPlanningRegisterProvider(getActivity(), paginationViewHandler);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, baseFamilyPlanningRegisterProvider, context().commonrepository(this.tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }

    @Override
    public BaseFamilyPlanningRegisterFragmentContract.Presenter presenter() {
        return (BaseFamilyPlanningRegisterFragmentContract.Presenter) presenter;
    }

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        presenter = new BaseFamilyPlanningRegisterFragmentPresenter(this, new BaseFamilyPlanningRegisterFragmentModel(), null);
    }

    @Override
    public void setUniqueID(String s) {
        if (getSearchView() != null) {
            getSearchView().setText(s);
        }
    }

    @Override
    public void setAdvancedSearchFormData(HashMap<String, String> hashMap) {
//        implement search here
    }

    @Override
    protected String getMainCondition() {
        return "";
    }

    @Override
    protected String getDefaultSortQuery() {
        return "";
    }

    @Override
    protected void startRegistration() {
//        start forms here if the module requires registration
    }

    @Override
    protected void onViewClicked(android.view.View view) {
//        implement onclick actions
    }

    @Override
    public void showNotFoundPopup(String s) {
//        implement dialog
    }
}
