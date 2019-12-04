package org.smartregister.chw.fp.presenter;

import org.smartregister.chw.fp.contract.BaseFpRegisterFragmentContract;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
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

    public BaseFpRegisterFragmentPresenter(BaseFpRegisterFragmentContract.View view, BaseFpRegisterFragmentContract.Model model) {
        this.viewReference = new WeakReference<>(view);
        this.model = model;
        this.config = model.defaultRegisterConfiguration();
    }

    @Override
    public void updateSortAndFilter(List<Field> filterList, Field sortField) {
//        implement
    }

    @Override
    public String getMainCondition() {
        return " ec_family_member.date_removed is null AND ec_family_planning.is_closed = 0";
    }

    @Override
    public String getDefaultSortQuery() {
        return FamilyPlanningConstants.DBConstants.FAMILY_PLANNING_TABLE + "." + FamilyPlanningConstants.DBConstants.LAST_INTERACTED_WITH + " DESC ";

    }

    @Override
    public void processViewConfigurations() {

        ViewConfiguration viewConfiguration = model.getViewConfiguration(FamilyPlanningConstants.CONFIGURATION.FAMILY_PLANNING_REGISTER);
        if (viewConfiguration != null) {
            config = (RegisterConfiguration) viewConfiguration.getMetadata();
            this.visibleColumns = model.getRegisterActiveColumns(FamilyPlanningConstants.CONFIGURATION.FAMILY_PLANNING_REGISTER);
        }

        if (config.getSearchBarText() != null && getView() != null) {
            getView().updateSearchBarHint(config.getSearchBarText());
        }
    }

    @Override
    public void initializeQueries(String mainCondition) {
        String tableName = FamilyPlanningConstants.DBConstants.FAMILY_PLANNING_TABLE;

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

    @Override
    public String getDueFilterCondition() {
//        TODO implement using schedule table for visit
        return " (cast( julianday(STRFTIME('%Y-%m-%d', datetime('now'))) -  julianday(IFNULL(SUBSTR(fp_reg_date,7,4)|| '-' || SUBSTR(fp_reg_date,4,2) || '-' || SUBSTR(fp_reg_date,1,2),'')) as integer) between 0 and 14) ";
    }


    @Override
    public String getMainTable() {
        return FamilyPlanningConstants.DBConstants.FAMILY_PLANNING_TABLE;
    }
}
