package org.smartregister.chw.fp.contract;

import com.vijay.jsonwizard.domain.Form;

import org.json.JSONObject;
import org.smartregister.view.contract.BaseRegisterContract;

public interface BaseFpRegisterContract {

    interface View extends BaseRegisterContract.View {
        Presenter presenter();

        Form getFormConfig();
    }

    interface Presenter extends BaseRegisterContract.Presenter {

        void startForm(String formName, String entityId, String metadata, String dob) throws Exception;

        void saveForm(String jsonString);
    }

    interface Model {

        JSONObject getFormAsJson(String formName, String entityId) throws Exception;
    }

    interface Interactor {

        void saveRegistration(String jsonString, final InteractorCallBack callBack);

    }

    interface InteractorCallBack {

        void onRegistrationSaved();
    }
}
