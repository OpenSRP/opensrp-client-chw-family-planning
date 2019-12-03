package org.smartregister.chw.fp.custom_views;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.LinearLayout;

import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.fp.R;


public class BaseFpFloatingMenu extends LinearLayout implements View.OnClickListener {
    private FpMemberObject fpMemberObject;

    public BaseFpFloatingMenu(Context context, FpMemberObject fpMemberObject) {
        super(context);
        this.fpMemberObject = fpMemberObject;
        initUi();
    }

    protected void initUi() {
        inflate(getContext(), R.layout.fp_call_floating_menu, this);
        FloatingActionButton fab = findViewById(R.id.fp_fab);
        if (fab != null)
            fab.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fp_fab) {
            Activity activity = (Activity) getContext();
            // BaseFpCallDialogFragment.launchDialog(activity, fpMemberObject);
        }
    }
}