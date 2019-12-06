package org.smartregister.chw.fp.contract;

import org.smartregister.chw.fp.domain.FpMemberObject;
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

        void setLastVisit(Date lastVisitDate);

        void setUpComingServicesStatus(String service, AlertStatus status, Date date);

        void setFamilyStatus(AlertStatus status);

        void setProfileViewDetails(FpMemberObject fpMemberObject);

        void updateHasMedicalHistory(boolean hasMedicalHistory);

        void setOverdueColor();

        void setDueColor();

        // hideView();

        void showProgressBar(boolean status);

    }

    interface Presenter {

        View getView();

        void refreshProfileData();

        void refreshProfileFpStatusInfo();

    }

    interface Interactor {

        void refreshProfileView(FpMemberObject fpMemberObject, boolean isForEdit, BaseFpProfileContract.InteractorCallback callback);

        void updateProfileFpStatusInfo(FpMemberObject memberObject, BaseFpProfileContract.InteractorCallback callback);

    }

    interface InteractorCallback {

        void refreshProfileTopSection(FpMemberObject fpMemberObject);

        void refreshLastVisit(Date lastVisitDate);

        void refreshUpComingServicesStatus(String service, AlertStatus status, Date date);

        void refreshFamilyStatus(AlertStatus status);

        void refreshMedicalHistory(boolean hasHistory);
    }
}
