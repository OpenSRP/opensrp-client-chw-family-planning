package org.smartregister.chw.fp.activity;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.MemberObject;
import org.smartregister.chw.fp.interactor.BaseFpProfileInteractor;
import org.smartregister.chw.fp.presenter.BaseFpProfilePresenter;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.fp.R;
import org.smartregister.helper.ImageRenderHelper;
import org.smartregister.view.activity.BaseProfileActivity;
import org.smartregister.view.contract.BaseProfileContract;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class BaseFpProfileActivity extends BaseProfileActivity implements BaseFpProfileContract.View {
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private ImageRenderHelper imageRenderHelper;
    private ProgressBar progressBar;
    private CircleImageView profileImageView;
    private TextView tvName;
    private TextView tvGender;
    private TextView tvLocation;
    private TextView tvUniqueID;
    private TextView familyHead;
    private TextView primaryCareGiver;
    private View lastVisitRow;
    private View overDueRow;
    private View familyRow;
    private RelativeLayout rlLastVisit;
    private RelativeLayout rlUpcomingServices;
    private RelativeLayout rlFamilyServicesDue;
    private RelativeLayout visitStatus;
    private TextView tvUndo;
    private TextView tvUpComingServices;
    private TextView tvFamilyStatus;
    private TextView tvRecordFpFollowUp;

    protected BaseProfileContract.Presenter profilePresenter;
    protected MemberObject memberObject;

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

        memberObject = (MemberObject) getIntent().getSerializableExtra(FamilyPlanningConstants.FAMILY_PLANNING_MEMBER_OBJECT.MEMBER_OBJECT);
        imageRenderHelper = new ImageRenderHelper(this);

        initializePresenter();
        setupViews();
    }

    @Override
    protected void setupViews() {
        tvName = findViewById(R.id.textview_name);
        tvGender = findViewById(R.id.textview_gender);
        tvLocation = findViewById(R.id.textview_address);
        tvUniqueID = findViewById(R.id.textview_unique_id);
        familyHead = findViewById(R.id.fp_family_head);
        primaryCareGiver = findViewById(R.id.fp_primary_caregiver);
        lastVisitRow = findViewById(R.id.view_last_visit_row);
        overDueRow = findViewById(R.id.view_most_due_overdue_row);
        familyRow = findViewById(R.id.view_family_row);
        tvUpComingServices = findViewById(R.id.text_view_upcoming_services);
        tvFamilyStatus = findViewById(R.id.textview_family_has);
        rlLastVisit = findViewById(R.id.rlLastVisit);
        rlUpcomingServices = findViewById(R.id.rlUpcomingServices);
        rlFamilyServicesDue = findViewById(R.id.rlFamilyServicesDue);
        visitStatus = findViewById(R.id.record_visit_status_bar);
        progressBar = findViewById(R.id.progress_bar);
        tvUndo = findViewById(R.id.textview_undo);
        profileImageView = findViewById(R.id.profile_image_view);
        tvRecordFpFollowUp = findViewById(R.id.textview_record_reccuring_visit);

        tvUndo.setOnClickListener(this);
        tvRecordFpFollowUp.setOnClickListener(this);
        findViewById(R.id.rlLastVisit).setOnClickListener(this);
        findViewById(R.id.rlUpcomingServices).setOnClickListener(this);
        findViewById(R.id.rlFamilyServicesDue).setOnClickListener(this);

    }

    @Override
    protected void initializePresenter() {
        showProgressBar(true);
        profilePresenter = new BaseFpProfilePresenter(this, new BaseFpProfileInteractor(), memberObject);
        fetchProfileData();
    }

    @Override
    protected ViewPager setupViewPager(ViewPager viewPager) {
        return null;
    }

    public void onClick(View view) {

    }

    @Override
    protected void fetchProfileData() {
        // TODO
    }

    @Override
    public void openMedicalHistory() {

    }

    @Override
    public void openFamilyPlanningRegistration() {

    }

    @Override
    public void openUpcomingServices() {

    }

    @Override
    public void openFollowUpVisitForm() {

    }

    @Override
    public void refreshUpcomingServices() {

    }

    @Override
    public void refreshMedicalHistory() {

    }

    @Override
    public void setProfileViewData(MemberObject memberObject) {
        int age = new Period(new DateTime(memberObject.getAge()), new DateTime()).getYears();
        tvName.setText(String.format(Locale.getDefault(), "%s %s %s, %d", memberObject.getFirstName(),
                memberObject.getMiddleName(), memberObject.getLastName(), age));
        tvGender.setText(memberObject.getGender());
        tvLocation.setText(memberObject.getAddress());
        tvUniqueID.setText(memberObject.getUniqueId());

        if (StringUtils.isNotBlank(memberObject.getFamilyHead()) && memberObject.getFamilyHead().equals(memberObject.getBaseEntityId())) {
            findViewById(R.id.fp_family_head).setVisibility(View.VISIBLE);
        }
        if (StringUtils.isNotBlank(memberObject.getPrimaryCareGiver()) && memberObject.getPrimaryCareGiver().equals(memberObject.getBaseEntityId())) {
            findViewById(R.id.fp_primary_caregiver).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setOverdueColor() {
        tvRecordFpFollowUp.setBackground(getResources().getDrawable(R.drawable.record_fp_followup));
    }

    @Override
    public void setDueColor() {
        tvRecordFpFollowUp.setBackground(getResources().getDrawable(R.drawable.record_fp_followup_overdue));
    }

    @Override
    public void showProgressBar(boolean status) {
        progressBar.setVisibility(status ? View.VISIBLE : View.GONE);
    }
}
