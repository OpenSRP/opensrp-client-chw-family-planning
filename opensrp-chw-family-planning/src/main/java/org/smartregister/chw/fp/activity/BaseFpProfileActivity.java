package org.smartregister.chw.fp.activity;

import android.app.ProgressDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.smartregister.fp.R;
import org.smartregister.helper.ImageRenderHelper;
import org.smartregister.view.activity.BaseProfileActivity;
import org.smartregister.view.contract.BaseProfileContract;

public class BaseFpProfileActivity extends BaseProfileActivity {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    protected ProgressDialog progressDialog;
    protected BaseProfileContract.Presenter presenter;
    protected ImageRenderHelper imageRenderHelper;
    private boolean showAppTitleBar = true;

    @Override
    protected void onCreation() {
        setContentView(R.layout.activity_base_fp_profile);
        findViewById(R.id.btn_profile_registration_info).setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.collapsing_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        appBarLayout = findViewById(R.id.collapsing_toolbar_appbarlayout);
        collapsingToolbarLayout = appBarLayout.findViewById(R.id.collapsing_toolbar_layout);
        appBarLayout.addOnOffsetChangedListener(this);
        imageRenderHelper = new ImageRenderHelper(this);

        initializePresenter();
        setupViews();
    }

    @Override
    protected void initializePresenter() {
    }

    @Override
    protected ViewPager setupViewPager(ViewPager viewPager) {
        return null;
    }

    @Override
    protected void fetchProfileData() {

    }
}
