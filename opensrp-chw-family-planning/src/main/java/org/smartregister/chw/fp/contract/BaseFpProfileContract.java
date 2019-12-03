package org.smartregister.chw.fp.contract;

import org.smartregister.chw.fp.domain.MemberObject;
import org.smartregister.domain.AlertStatus;
import org.smartregister.view.contract.BaseProfileContract;

import java.util.Date;

public interface BaseFpProfileContract {

    interface View extends BaseProfileContract.View {

        void openMedicalHistory();

        void openFamilyPlanningRegistration();

        void openUpcomingServices();

        void openFamilyDueServices();

        void openFollowUpVisitForm();

        void refreshUpcomingServices();

        void refreshMedicalHistory(boolean hasHistory);

        void setProfileViewDetails(MemberObject memberObject);

        void setOverdueColor();

        void setDueColor();

        // hideView();

        void showProgressBar(boolean status);

    }

    interface Presenter extends BaseProfileContract.Presenter {

        View getView();

        void refreshProfileInfo();

        void updateMedicalHistory();

        void updateUpcomingServices();

        void refreshFamilyStatus();

    }


    interface Model {

    }

    interface Interactor {

        void fetchProfileInfo(String baseEntityId);

    }

    interface InteractorCallback {

        void refreshProfileTopSection(MemberObject memberObject);

        void refreshLastVisit(Date lastVisitDate);

        void refreshUpComingServicesStatus(String service, AlertStatus status, Date date);

        void refreshFamilyStatus(AlertStatus status);
    }
}
