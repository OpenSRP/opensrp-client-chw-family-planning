package org.smartregister.chw.fp.model;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.smartregister.chw.fp.FpLibrary;
import org.smartregister.chw.fp.contract.BaseFpRegisterFragmentContract;
import org.smartregister.chw.fp.util.ConfigHelper;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.configurableviews.model.Field;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.View;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.cursoradapter.SmartRegisterQueryBuilder;
import org.smartregister.domain.Response;
import org.smartregister.domain.ResponseStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import timber.log.Timber;

public class BaseFpRegisterFragmentModel implements BaseFpRegisterFragmentContract.Model {

    @NonNull
    @Override
    public String mainSelect(@NonNull String tableName, @NonNull String mainCondition) {
        SmartRegisterQueryBuilder queryBuilder = new SmartRegisterQueryBuilder();
        queryBuilder.selectInitiateMainTable(tableName, mainColumns(tableName));
        queryBuilder.customJoin("INNER JOIN " + FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + " ON  " + tableName + "." + FamilyPlanningConstants.DBConstants.BASE_ENTITY_ID + " = " + FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.BASE_ENTITY_ID + " COLLATE NOCASE ");
        queryBuilder.customJoin("INNER JOIN " + FamilyPlanningConstants.DBConstants.FAMILY + " ON  " + FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.RELATIONAL_ID + " = " + FamilyPlanningConstants.DBConstants.FAMILY + "." + FamilyPlanningConstants.DBConstants.BASE_ENTITY_ID);

        return queryBuilder.mainCondition(mainCondition);
    }


    @Override
    public RegisterConfiguration defaultRegisterConfiguration() {
        return ConfigHelper.defaultRegisterConfiguration(FpLibrary.getInstance().context().applicationContext());
    }

    @Override
    public ViewConfiguration getViewConfiguration(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getViewConfiguration(viewConfigurationIdentifier);
    }

    @Override
    public Set<View> getRegisterActiveColumns(String viewConfigurationIdentifier) {
        return ConfigurableViewsLibrary.getInstance().getConfigurableViewsHelper().getRegisterActiveColumns(viewConfigurationIdentifier);
    }

    @Override
    public String countSelect(String tableName, String mainCondition) {
        SmartRegisterQueryBuilder countQueryBuilder = new SmartRegisterQueryBuilder();
        countQueryBuilder.selectInitiateMainTableCounts(tableName);
        return countQueryBuilder.mainCondition(mainCondition);
    }


    protected String[] mainColumns(String tableName) {
        Set<String> columnList = new HashSet<>();
        columnList.add(tableName + "." + FamilyPlanningConstants.DBConstants.LAST_INTERACTED_WITH);
        columnList.add(tableName + "." + FamilyPlanningConstants.DBConstants.BASE_ENTITY_ID);
        columnList.add(tableName + "." + FamilyPlanningConstants.DBConstants.FP_METHOD_ACCEPTED);
        columnList.add(tableName + "." + FamilyPlanningConstants.DBConstants.FP_PILL_CYCLES);
        columnList.add(tableName + "." + FamilyPlanningConstants.DBConstants.FP_FP_START_DATE);
        columnList.add(FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.RELATIONAL_ID + " as relationalid");
        columnList.add(FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.RELATIONAL_ID);
        columnList.add(FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.FIRST_NAME);
        columnList.add(FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.MIDDLE_NAME);
        columnList.add(FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.LAST_NAME);
        columnList.add(FamilyPlanningConstants.DBConstants.FAMILY_MEMBER + "." + FamilyPlanningConstants.DBConstants.DOB);
        columnList.add(FamilyPlanningConstants.DBConstants.FAMILY + "." + FamilyPlanningConstants.DBConstants.VILLAGE_TOWN);

        return columnList.toArray(new String[columnList.size()]);
    }

    @Override
    public String getFilterText(List<Field> list, String filterTitle) {
        List<Field> filterList = list;
        if (filterList == null) {
            filterList = new ArrayList<>();
        }

        String filter = filterTitle;
        if (filter == null) {
            filter = "";
        }
        return "<font color=#727272>" + filter + "</font> <font color=#f0ab41>(" + filterList.size() + ")</font>";
    }

    @Override
    public String getSortText(Field sortField) {
        String sortText = "";
        if (sortField != null) {
            if (StringUtils.isNotBlank(sortField.getDisplayName())) {
                sortText = "(Sort: " + sortField.getDisplayName() + ")";
            } else if (StringUtils.isNotBlank(sortField.getDbAlias())) {
                sortText = "(Sort: " + sortField.getDbAlias() + ")";
            }
        }
        return sortText;
    }

    @Override
    public JSONArray getJsonArray(Response<String> response) {
        try {
            if (response.status().equals(ResponseStatus.success)) {
                return new JSONArray(response.payload());
            }
        } catch (Exception e) {
            Timber.e(getClass().getName(), "", e);
        }
        return null;
    }
}
