package org.smartregister.fp.application;

import android.util.Log;

import org.smartregister.Context;
import org.smartregister.CoreLibrary;
import org.smartregister.commonregistry.CommonFtsObject;
import org.smartregister.configurableviews.ConfigurableViewsLibrary;
import org.smartregister.fp.BuildConfig;
import org.smartregister.chw.fp.FpLibrary;
import org.smartregister.fp.repository.SampleRepository;
import org.smartregister.chw.fp.util.DBConstants;
import org.smartregister.fp.utils.SampleConstants;
import org.smartregister.receiver.SyncStatusBroadcastReceiver;
import org.smartregister.repository.Repository;
import org.smartregister.repository.UniqueIdRepository;
import org.smartregister.view.activity.DrishtiApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SampleApplication extends DrishtiApplication {
    private static final String TAG = SampleApplication.class.getCanonicalName();

    private static CommonFtsObject commonFtsObject;
    private UniqueIdRepository uniqueIdRepository;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        context = Context.getInstance();
        context.updateApplicationContext(getApplicationContext());
        context.updateCommonFtsObject(createCommonFtsObject());

        //Initialize Modules
        CoreLibrary.init(context);
        ConfigurableViewsLibrary.init(context, getRepository());
        FpLibrary.init(context, getRepository(), BuildConfig.VERSION_CODE, BuildConfig.DATABASE_VERSION);

        SyncStatusBroadcastReceiver.init(this);

        //Auto login by default
        String password = "pwd";
        context.session().start(context.session().lengthInMilliseconds());
        context.configuration().getDrishtiApplication().setPassword(password);
        context.session().setPassword(password);


        sampleUniqueIds();

    }

    @Override
    public void logoutCurrentUser() {
    }

    public static synchronized SampleApplication getInstance() {
        return (SampleApplication) mInstance;
    }

    @Override
    public Repository getRepository() {
        try {
            if (repository == null) {
                repository = new SampleRepository(getInstance().getApplicationContext(), context);
            }
        } catch (UnsatisfiedLinkError e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return repository;
    }


    public static CommonFtsObject createCommonFtsObject() {
        if (commonFtsObject == null) {
            commonFtsObject = new CommonFtsObject(getFtsTables());
            for (String ftsTable : commonFtsObject.getTables()) {
                commonFtsObject.updateSearchFields(ftsTable, getFtsSearchFields(ftsTable));
                commonFtsObject.updateSortFields(ftsTable, getFtsSortFields(ftsTable));
            }
        }
        return commonFtsObject;
    }

    private static String[] getFtsTables() {
        return new String[]{SampleConstants.TABLE_NAME.FAMILY, SampleConstants.TABLE_NAME.FAMILY_MEMBER};
    }

    private static String[] getFtsSearchFields(String tableName) {
        if (tableName.equals(SampleConstants.TABLE_NAME.FAMILY)) {
            return new String[]{DBConstants.DBKeys.BASE_ENTITY_ID, DBConstants.DBKeys.VILLAGE_TOWN, DBConstants.DBKeys.FIRST_NAME,
                    DBConstants.DBKeys.LAST_NAME, DBConstants.DBKeys.UNIQUE_ID};
        } else if (tableName.equals(SampleConstants.TABLE_NAME.FAMILY_MEMBER)) {
            return new String[]{DBConstants.DBKeys.BASE_ENTITY_ID, DBConstants.DBKeys.FIRST_NAME, DBConstants.DBKeys.MIDDLE_NAME,
                    DBConstants.DBKeys.LAST_NAME, DBConstants.DBKeys.UNIQUE_ID};
        }
        return null;
    }

    private static String[] getFtsSortFields(String tableName) {
        if (tableName.equals(SampleConstants.TABLE_NAME.FAMILY)) {
            return new String[]{DBConstants.DBKeys.LAST_INTERACTED_WITH, DBConstants.DBKeys.DATE_REMOVED};
        } else if (tableName.equals(SampleConstants.TABLE_NAME.FAMILY_MEMBER)) {
            return new String[]{DBConstants.DBKeys.DOB, DBConstants.DBKeys.DOD, DBConstants.DBKeys
                    .LAST_INTERACTED_WITH, DBConstants.DBKeys.DATE_REMOVED};
        }
        return null;
    }

    public UniqueIdRepository getUniqueIdRepository() {
        if (uniqueIdRepository == null) {
            uniqueIdRepository = new UniqueIdRepository(getRepository());
        }
        return uniqueIdRepository;
    }

    private void sampleUniqueIds() {
        List<String> ids = generateIds(20);
        getUniqueIdRepository().bulkInserOpenmrsIds(ids);
    }

    private List<String> generateIds(int size) {
        List<String> ids = new ArrayList<>();
        Random r = new Random();

        for (int i = 0; i < size; i++) {
            Integer randomInt = r.nextInt(1000) + 1;
            ids.add(randomInt.toString());
        }

        return ids;
    }

}