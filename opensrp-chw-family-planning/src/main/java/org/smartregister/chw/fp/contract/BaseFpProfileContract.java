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

        void setLastVisit(Date lastVisitDate);

        void setUpComingServicesStatus(String service, AlertStatus status, Date date);

        void setFamilyStatus(AlertStatus status);

        void setProfileViewDetails(MemberObject memberObject);

        void setOverdueColor();

        void setDueColor();

        void refreshMedicalHistory(boolean hasHistory);

        // hideView();

        void showProgressBar(boolean status);

    }

    interface Presenter extends BaseProfileContract.Presenter {

        View getView();

        void resetProfileInfo();

        void refreshProfileData();

        void fetchMemberDetails(String memberId);
    }

    interface Interactor {

        void refreshProfileView(MemberObject memberObject, boolean isForEdit, BaseFpProfileContract.InteractorCallback callback);

        void resetProfileInfo(BaseFpProfileContract.InteractorCallback callback);

    }

    interface InteractorCallback {

        void refreshProfileTopSection(MemberObject memberObject);

        void refreshLastVisit(Date lastVisitDate);

        void refreshUpComingServicesStatus(String service, AlertStatus status, Date date);

        void refreshFamilyStatus(AlertStatus status);
    }
}
