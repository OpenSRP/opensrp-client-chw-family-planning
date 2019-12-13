package org.smartregister.chw.fp.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.smartregister.chw.fp.contract.BaseFpCallDialogContract;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.chw.fp.listener.BaseFpCallWidgetDialogListener;
import org.smartregister.chw.fp.util.FpUtil;
import org.smartregister.fp.R;

import static android.view.View.GONE;
import static org.smartregister.util.Utils.getName;

public class BaseFpCallDialogFragment extends DialogFragment implements BaseFpCallDialogContract.View {

    public static final String DIALOG_TAG = "BaseFpCallWidgetDialogFragment_DIALOG_TAG";
    private static FpMemberObject fpMemberObject;
    private View.OnClickListener listener = null;

    public static BaseFpCallDialogFragment launchDialog(Activity activity, FpMemberObject memberObject) {
        BaseFpCallDialogFragment dialogFragment = BaseFpCallDialogFragment.newInstance();
        FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
        Fragment prev = activity.getFragmentManager().findFragmentByTag(DIALOG_TAG);
        fpMemberObject = memberObject;
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        dialogFragment.show(ft, DIALOG_TAG);

        return dialogFragment;
    }

    public static BaseFpCallDialogFragment newInstance() {
        return new BaseFpCallDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ChwTheme_Dialog_FullWidth);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup dialogView = (ViewGroup) inflater.inflate(R.layout.fp_member_call_widget_dialog_fragment, container, false);
        setUpPosition();
        TextView callTitle = dialogView.findViewById(R.id.call_fp_woman_title);
        callTitle.setText(getString((R.string.call_fp_woman)));

        if (listener == null) {
            listener = new BaseFpCallWidgetDialogListener(this);
        }

        initUI(dialogView);
        return dialogView;
    }

    private void initUI(ViewGroup rootView) {

        if (StringUtils.isNotBlank(fpMemberObject.getPhoneNumber())) {
            TextView fpWomanNameTextView = rootView.findViewById(R.id.call_fp_woman_name);
            fpWomanNameTextView.setText(FpUtil.getFullName(fpMemberObject));

            TextView fpWomanPhone = rootView.findViewById(R.id.call_fp_woman_phone);
            fpWomanPhone.setTag(fpMemberObject.getPhoneNumber());
            fpWomanPhone.setText(getName(getCurrentContext().getString(R.string.call), fpMemberObject.getPhoneNumber()));
            fpWomanPhone.setOnClickListener(listener);
        } else {
            rootView.findViewById(R.id.layout_fp_woman).setVisibility(GONE);
        }

        if (StringUtils.isNotBlank(fpMemberObject.getFamilyHeadPhoneNumber())) {
            TextView fpPrimaryCaregiverTitle = rootView.findViewById(R.id.call_primary_caregiver_title);
            if (StringUtils.isNotBlank(fpMemberObject.getPrimaryCareGiver()) &&
                    fpMemberObject.getFamilyHead().equals(fpMemberObject.getPrimaryCareGiver())) {
                fpPrimaryCaregiverTitle = rootView.findViewById(R.id.call_primary_caregiver_title);
                fpPrimaryCaregiverTitle.setVisibility(View.VISIBLE);
            } else {
                fpPrimaryCaregiverTitle.setVisibility(GONE);
            }
            TextView familyHeadName = rootView.findViewById(R.id.fp_call_head_name);
            familyHeadName.setText(fpMemberObject.getFamilyHeadName());

            TextView callHeadPhoneNumber = rootView.findViewById(R.id.fp_call_head_phone_number);
            callHeadPhoneNumber.setTag(fpMemberObject.getFamilyHeadPhoneNumber());
            callHeadPhoneNumber.setText(getName(getCurrentContext().getString(R.string.call), fpMemberObject.getFamilyHeadPhoneNumber()));
            callHeadPhoneNumber.setOnClickListener(listener);

        } else {
            rootView.findViewById(R.id.fp_layout_family_head).setVisibility(GONE);
        }

        rootView.findViewById(R.id.fp_call_close).setOnClickListener(listener);

    }

    private void setUpPosition() {
        getDialog().getWindow().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.TOP);
        WindowManager.LayoutParams p = getDialog().getWindow().getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;
        p.y = 20;
        getDialog().getWindow().setAttributes(p);
    }

    @Override
    public BaseFpCallDialogContract.Dialer getPendingCallRequest() {
        return null;
    }

    @Override
    public void setPendingCallRequest(BaseFpCallDialogContract.Dialer dialer) {
        // TODO :: set a pending call request
    }

    @Override
    public Context getCurrentContext() {
        return getActivity();
    }
}
