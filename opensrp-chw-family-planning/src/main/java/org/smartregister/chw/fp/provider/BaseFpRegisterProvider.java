package org.smartregister.chw.fp.provider;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.smartregister.chw.fp.fragment.BaseFpRegisterFragment;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;
import org.smartregister.commonregistry.CommonPersonObjectClient;
import org.smartregister.cursoradapter.RecyclerViewProvider;
import org.smartregister.fp.R;
import org.smartregister.util.Utils;
import org.smartregister.view.contract.SmartRegisterClient;
import org.smartregister.view.contract.SmartRegisterClients;
import org.smartregister.view.dialog.FilterOption;
import org.smartregister.view.dialog.ServiceModeOption;
import org.smartregister.view.dialog.SortOption;
import org.smartregister.view.viewholder.OnClickFormLauncher;

import java.text.MessageFormat;
import java.util.Set;

import timber.log.Timber;

import static org.smartregister.util.Utils.getName;

public class BaseFpRegisterProvider implements RecyclerViewProvider<BaseFpRegisterProvider.RegisterViewHolder> {

    private final LayoutInflater inflater;
    protected View.OnClickListener onClickListener;
    private View.OnClickListener paginationClickListener;
    private Context context;
    private Set<org.smartregister.configurableviews.model.View> visibleColumns;

    public BaseFpRegisterProvider(Context context, View.OnClickListener paginationClickListener, View.OnClickListener onClickListener, Set visibleColumns) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.paginationClickListener = paginationClickListener;
        this.onClickListener = onClickListener;
        this.visibleColumns = visibleColumns;
        this.context = context;
    }

    @Override
    public void getView(Cursor cursor, SmartRegisterClient smartRegisterClient, RegisterViewHolder registerViewHolder) {
        CommonPersonObjectClient pc = (CommonPersonObjectClient) smartRegisterClient;
        if (visibleColumns.isEmpty()) {
            populatePatientColumn(pc, registerViewHolder);
        }
    }

    private void populatePatientColumn(CommonPersonObjectClient pc, final RegisterViewHolder viewHolder) {
        try {

            String firstName = getName(
                    Utils.getValue(pc.getColumnmaps(), FamilyPlanningConstants.DBConstants.FIRST_NAME, true),
                    Utils.getValue(pc.getColumnmaps(), FamilyPlanningConstants.DBConstants.MIDDLE_NAME, true));

            String dobString = Utils.getValue(pc.getColumnmaps(), FamilyPlanningConstants.DBConstants.DOB, false);
            int age = new Period(new DateTime(dobString), new DateTime()).getYears();

            String patientName = getName(firstName, Utils.getValue(pc.getColumnmaps(), FamilyPlanningConstants.DBConstants.LAST_NAME, true));
            String methodAccepted = getTranslatedValue(FamilyPlanningConstants.DBConstants.FP_METHOD_ACCEPTED);
            viewHolder.patientName.setText(patientName + ", " + age);
            viewHolder.textViewFpMethod.setText(Utils.getValue(pc.getColumnmaps(), methodAccepted, true));
            viewHolder.textViewVillage.setText(Utils.getValue(pc.getColumnmaps(), FamilyPlanningConstants.DBConstants.VILLAGE_TOWN, true));
            viewHolder.patientColumn.setOnClickListener(onClickListener);
            viewHolder.patientColumn.setTag(pc);
            viewHolder.patientColumn.setTag(R.id.VIEW_ID, BaseFpRegisterFragment.CLICK_VIEW_NORMAL);

            viewHolder.dueButton.setOnClickListener(onClickListener);
            viewHolder.dueButton.setTag(pc);
            viewHolder.dueButton.setTag(R.id.VIEW_ID, BaseFpRegisterFragment.FOLLOW_UP_VISIT);
            viewHolder.registerColumns.setOnClickListener(onClickListener);

            viewHolder.registerColumns.setOnClickListener(v -> viewHolder.patientColumn.performClick());
            viewHolder.registerColumns.setOnClickListener(v -> viewHolder.dueButton.performClick());

        } catch (Exception e) {
            Timber.e(e);
        }
    }

    private String getTranslatedValue(@Nullable String fpMethod){
        if(fpMethod != null){
            switch (fpMethod){
                case "COC":
                    return context.getString(R.string.coc);
                case "POP":
                    return context.getString(R.string.pop);
                case "Female sterilization":
                    return context.getString(R.string.female_sterilization);
                case "Injectable":
                    return context.getString(R.string.injectable);
                case "Male condom":
                    return context.getString(R.string.male_condom);
                case "Female condom":
                    return context.getString(R.string.female_condom);
                case "IUCD":
                    return context.getString(R.string.iucd);
                case "Implanon - NXT":
                    return context.getString(R.string.implanon);
                case "Male sterilization":
                    return context.getString(R.string.male_sterilization);
                case "Jadelle":
                    return context.getString(R.string.jadelle);
                case "Standard day method":
                    return context.getString(R.string.standard_day_method);
                default:
                    return fpMethod;
            }
        }
        return fpMethod;
    }


    @Override
    public void getFooterView(RecyclerView.ViewHolder viewHolder, int currentPageCount, int totalPageCount, boolean hasNext, boolean hasPrevious) {
        FooterViewHolder footerViewHolder = (FooterViewHolder) viewHolder;
        footerViewHolder.pageInfoView.setText(MessageFormat.format(context.getString(org.smartregister.R.string.str_page_info), currentPageCount, totalPageCount));

        footerViewHolder.nextPageView.setVisibility(hasNext ? View.VISIBLE : View.INVISIBLE);
        footerViewHolder.previousPageView.setVisibility(hasPrevious ? View.VISIBLE : View.INVISIBLE);

        footerViewHolder.nextPageView.setOnClickListener(paginationClickListener);
        footerViewHolder.previousPageView.setOnClickListener(paginationClickListener);
    }

    @Override
    public SmartRegisterClients updateClients(FilterOption filterOption, ServiceModeOption serviceModeOption, FilterOption filterOption1, SortOption sortOption) {
        return null;
    }

    @Override
    public void onServiceModeSelected(ServiceModeOption serviceModeOption) {
//        implement
    }

    @Override
    public OnClickFormLauncher newFormLauncher(String s, String s1, String s2) {
        return null;
    }

    @Override
    public LayoutInflater inflater() {
        return inflater;
    }

    @Override
    public RegisterViewHolder createViewHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.family_planning_register_list_row, parent, false);
        return new RegisterViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder createFooterHolder(ViewGroup parent) {
        View view = inflater.inflate(R.layout.smart_register_pagination, parent, false);
        return new FooterViewHolder(view);
    }

    @Override
    public boolean isFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        return viewHolder instanceof FooterViewHolder;
    }

    public class RegisterViewHolder extends RecyclerView.ViewHolder {
        public TextView patientName;
        public TextView parentName;
        public TextView textViewVillage;
        public TextView textViewFpMethod;
        public Button dueButton;
        public View patientColumn;

        public View registerColumns;
        public View dueWrapper;

        public RegisterViewHolder(View itemView) {
            super(itemView);

            parentName = itemView.findViewById(R.id.patient_parent_name);
            patientName = itemView.findViewById(R.id.patient_name_age);
            textViewVillage = itemView.findViewById(R.id.text_view_village);
            textViewFpMethod = itemView.findViewById(R.id.text_view_fp_method);
            dueButton = itemView.findViewById(R.id.due_button);
            patientColumn = itemView.findViewById(R.id.patient_column);
            registerColumns = itemView.findViewById(R.id.register_columns);
            dueWrapper = itemView.findViewById(R.id.due_button_wrapper);
        }
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public TextView pageInfoView;
        public Button nextPageView;
        public Button previousPageView;

        public FooterViewHolder(View view) {
            super(view);

            nextPageView = view.findViewById(org.smartregister.R.id.btn_next_page);
            previousPageView = view.findViewById(org.smartregister.R.id.btn_previous_page);
            pageInfoView = view.findViewById(org.smartregister.R.id.txt_page_info);
        }
    }
}
