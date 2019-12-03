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
import org.joda.time.Days;
import org.joda.time.Period;
import org.smartregister.chw.fp.contract.BaseFpProfileContract;
import org.smartregister.chw.fp.domain.MemberObject;
import org.smartregister.chw.fp.interactor.BaseFpProfileInteractor;
import org.smartregister.chw.fp.presenter.BaseFpProfilePresenter;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.chw.fp.util.FpUtil;
import org.smartregister.domain.AlertStatus;
import org.smartregister.fp.R;
import org.smartregister.helper.ImageRenderHelper;
import org.smartregister.view.activity.BaseProfileActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private TextView tvLastVisitDate;
    private TextView tvUpComingServices;
    private TextView tvFamilyStatus;
    private TextView tvRecordFpFollowUp;

    protected BaseFpProfileContract.Presenter fpProfilePresenter;
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

        setupViews();
        initializePresenter();
        fpProfilePresenter.refreshProfileData();
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
        tvLastVisitDate = findViewById(R.id.textview_last_visit_day);

        tvUndo.setOnClickListener(this);
        tvRecordFpFollowUp.setOnClickListener(this);
        findViewById(R.id.rlLastVisit).setOnClickListener(this);
        findViewById(R.id.rlUpcomingServices).setOnClickListener(this);
        findViewById(R.id.rlFamilyServicesDue).setOnClickListener(this);
    }

    @Override
    protected void initializePresenter() {
        fpProfilePresenter = new BaseFpProfilePresenter(this, new BaseFpProfileInteractor(), memberObject);
    }

    @Override
    protected ViewPager setupViewPager(ViewPager viewPager) {
        return null;
    }

    @Override
    protected void fetchProfileData() {

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.title_layout) {
            onBackPressed();
        } else if (id == R.id.rlLastVisit) {
            this.openMedicalHistory();
        } else if (id == R.id.rlUpcomingServices) {
            this.openUpcomingServices();
        } else if (id == R.id.rlFamilyServicesDue) {
            this.openFamilyDueServices();
        }
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
    public void openFamilyDueServices() {

    }

    @Override
    public void openFollowUpVisitForm() {

    }

    @Override
    public void setLastVisit(Date lastVisitDate) {
        if (lastVisitDate == null)
            return;

        lastVisitRow.setVisibility(View.VISIBLE);
        rlLastVisit.setVisibility(View.VISIBLE);

        int numOfDays = Days.daysBetween(new DateTime(lastVisitDate).toLocalDate(), new DateTime().toLocalDate()).getDays();
        tvLastVisitDate.setText(getString(R.string.last_visit_n_days_ago, (numOfDays <= 1) ? getString(R.string.less_than_twenty_four) : numOfDays + " " + getString(R.string.days)));
    }

    @Override
    public void setUpComingServicesStatus(String service, AlertStatus status, Date date) {
        showProgressBar(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        if (status == AlertStatus.complete)
            return;
        overDueRow.setVisibility(View.VISIBLE);
        rlUpcomingServices.setVisibility(View.VISIBLE);

        if (status == AlertStatus.upcoming) {
            tvUpComingServices.setText(FpUtil.fromHtml(getString(R.string.fp_service_upcoming, service, dateFormat.format(date))));
        } else {
            tvUpComingServices.setText(FpUtil.fromHtml(getString(R.string.fp_service_due, service, dateFormat.format(date))));
        }
    }

    @Override
    public void setFamilyStatus(AlertStatus status) {
        familyRow.setVisibility(View.VISIBLE);
        rlFamilyServicesDue.setVisibility(View.VISIBLE);

        if (status == AlertStatus.complete) {
            tvFamilyStatus.setText(getString(R.string.family_has_nothing_due));
        } else if (status == AlertStatus.normal) {
            tvFamilyStatus.setText(getString(R.string.family_has_services_due));
        } else if (status == AlertStatus.urgent) {
            tvFamilyStatus.setText(FpUtil.fromHtml(getString(R.string.family_has_service_overdue)));
        }
    }

    @Override
    public void refreshMedicalHistory(boolean hasHistory) {
        showProgressBar(false);
        rlLastVisit.setVisibility(hasHistory ? View.VISIBLE : View.GONE);
    }

    @Override
    public void setProfileViewDetails(MemberObject memberObject) {
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
