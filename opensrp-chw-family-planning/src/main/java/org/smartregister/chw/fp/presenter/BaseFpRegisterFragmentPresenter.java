package org.smartregister.chw.fp.presenter;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.fp.contract.BaseFpRegisterFragmentContract;
import org.smartregister.chw.fp.util.DBConstants;
import org.smartregister.configurableviews.model.Field;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class BaseFpRegisterFragmentPresenter implements BaseFpRegisterFragmentContract.Presenter {

    protected WeakReference<BaseFpRegisterFragmentContract.View> viewReference;

    protected BaseFpRegisterFragmentContract.Model model;

    protected RegisterConfiguration config;

    protected Set<View> visibleColumns = new TreeSet<>();
    protected String viewConfigurationIdentifier;

    public BaseFpRegisterFragmentPresenter(BaseFpRegisterFragmentContract.View view, BaseFpRegisterFragmentContract.Model model, String viewConfigurationIdentifier) {
        this.viewReference = new WeakReference<>(view);
        this.model = model;
        this.viewConfigurationIdentifier = viewConfigurationIdentifier;
        this.config = model.defaultRegisterConfiguration();
    }

    @Override
    public void updateSortAndFilter(List<Field> filterList, Field sortField) {
//        implement
    }

    @Override
    public String getMainCondition() {
        return "";
    }

    @Override
    public String getDefaultSortQuery() {
        return "";
    }

    @Override
    public void processViewConfigurations() {
        if (StringUtils.isBlank(viewConfigurationIdentifier)) {
            return;
        }

        ViewConfiguration viewConfiguration = model.getViewConfiguration(viewConfigurationIdentifier);
        if (viewConfiguration != null) {
            config = (RegisterConfiguration) viewConfiguration.getMetadata();
            this.visibleColumns = model.getRegisterActiveColumns(viewConfigurationIdentifier);
        }

        if (config.getSearchBarText() != null && getView() != null) {
            getView().updateSearchBarHint(config.getSearchBarText());
        }
    }

    @Override
    public void initializeQueries(String mainCondition) {
        String tableName = DBConstants.tableName.FAMILY_PLANNING_TABLE;

        String countSelect = model.countSelect(tableName, mainCondition);
        String mainSelect = model.mainSelect(tableName, mainCondition);

        getView().initializeQueryParams(tableName, countSelect, mainSelect);
        getView().initializeAdapter(visibleColumns);

        getView().countExecute();
        getView().filterandSortInInitializeQueries();
    }

    protected BaseFpRegisterFragmentContract.View getView() {
        if (viewReference != null)
            return viewReference.get();
        else
            return null;
    }

    @Override
    public void startSync() {
//        implement

    }

    @Override
    public void searchGlobally(String s) {
//        implement

    }
}
