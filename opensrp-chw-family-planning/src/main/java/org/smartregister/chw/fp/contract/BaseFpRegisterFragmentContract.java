package org.smartregister.chw.fp.contract;

import org.json.JSONArray;
import org.smartregister.configurableviews.model.Field;
import org.smartregister.configurableviews.model.RegisterConfiguration;
import org.smartregister.configurableviews.model.ViewConfiguration;
import org.smartregister.domain.Response;
import org.smartregister.view.contract.BaseRegisterFragmentContract;

import java.util.List;
import java.util.Set;

public interface BaseFpRegisterFragmentContract {


    interface View extends BaseRegisterFragmentContract.View {

        Presenter presenter();

    }

    interface Presenter extends BaseRegisterFragmentContract.Presenter {

        void updateSortAndFilter(List<Field> filterList, Field sortField);

        String getMainCondition();

        String getDefaultSortQuery();

        String getDueFilterCondition();

        String getMainTable();


    }

    interface Model extends BaseRegisterFragmentContract.Model {

        RegisterConfiguration defaultRegisterConfiguration();

        String countSelect(String tableName, String mainCondition);

        String mainSelect(String tableName, String mainCondition);

        String getSortText(Field sortField);

        JSONArray getJsonArray(Response<String> response);

    }
}
