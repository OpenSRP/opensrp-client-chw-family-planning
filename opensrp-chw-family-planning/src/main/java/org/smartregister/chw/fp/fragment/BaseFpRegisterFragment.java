package org.smartregister.chw.fp.fragment;

import org.smartregister.chw.fp.contract.BaseFpRegisterFragmentContract;
import org.smartregister.chw.fp.model.BaseFpRegisterFragmentModel;
import org.smartregister.chw.fp.presenter.BaseFpRegisterFragmentPresenter;
import org.smartregister.chw.fp.provider.BaseFpRegisterProvider;
import org.smartregister.configurableviews.model.View;
import org.smartregister.cursoradapter.RecyclerViewPaginatedAdapter;
import org.smartregister.view.fragment.BaseRegisterFragment;

import java.util.HashMap;
import java.util.Set;

public class BaseFpRegisterFragment extends BaseRegisterFragment implements BaseFpRegisterFragmentContract.View {
    @Override
    public void initializeAdapter(Set<View> visibleColumns) {
        BaseFpRegisterProvider baseFpRegisterProvider = new BaseFpRegisterProvider(getActivity(), paginationViewHandler);
        clientAdapter = new RecyclerViewPaginatedAdapter(null, baseFpRegisterProvider, context().commonrepository(this.tablename));
        clientAdapter.setCurrentlimit(20);
        clientsView.setAdapter(clientAdapter);
    }

    @Override
    public BaseFpRegisterFragmentContract.Presenter presenter() {
        return (BaseFpRegisterFragmentContract.Presenter) presenter;
    }

    @Override
    protected void initializePresenter() {
        if (getActivity() == null) {
            return;
        }
        presenter = new BaseFpRegisterFragmentPresenter(this, new BaseFpRegisterFragmentModel(), null);
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
