package org.smartregister.chw.fp.contract;

import android.content.Context;

public interface BaseFpCallDialogContract {
    interface View {

        Dialer getPendingCallRequest();

        void setPendingCallRequest(Dialer dialer);


        Context getCurrentContext();
    }


    interface Model {

        String getName();

        void setName(String name);

        String getRole();

        void setRole(String role);

        String getPhoneNumber();

        void setPhoneNumber(String phoneNumber);
    }

    interface Dialer {
        void callMe();
    }
}
