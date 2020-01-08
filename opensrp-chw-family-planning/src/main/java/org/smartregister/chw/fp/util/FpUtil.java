package org.smartregister.chw.fp.util;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.json.JSONObject;
import org.smartregister.chw.fp.FpLibrary;
import org.smartregister.chw.fp.contract.BaseFpCallDialogContract;
import org.smartregister.chw.fp.dao.FpDao;
import org.smartregister.chw.fp.domain.FpMemberObject;
import org.smartregister.clientandeventmodel.Event;
import org.smartregister.fp.R;
import org.smartregister.repository.AllSharedPreferences;
import org.smartregister.repository.BaseRepository;
import org.smartregister.sync.ClientProcessorForJava;
import org.smartregister.sync.helper.ECSyncHelper;
import org.smartregister.util.PermissionUtils;

import java.util.Date;

import timber.log.Timber;

import static org.smartregister.util.Utils.getAllSharedPreferences;

public class FpUtil {
    public static ClientProcessorForJava getClientProcessorForJava() {
        return FpLibrary.getInstance().getClientProcessorForJava();
    }

    public static ECSyncHelper getSyncHelper() {
        return FpLibrary.getInstance().getEcSyncHelper();
    }

    public static void saveFormEvent(final String jsonString) throws Exception {
        AllSharedPreferences allSharedPreferences = FpLibrary.getInstance().context().allSharedPreferences();
        Event baseEvent = FpJsonFormUtils.processJsonForm(allSharedPreferences, jsonString);
        processEvent(allSharedPreferences, baseEvent);
    }

    public static String getFullName(FpMemberObject fpMemberObject) {
        StringBuilder nameBuilder = new StringBuilder();
        String firstName = fpMemberObject.getFirstName();
        String lastName = fpMemberObject.getLastName();
        String middleName = fpMemberObject.getMiddleName();
        if (StringUtils.isNotBlank(firstName)) {
            nameBuilder.append(firstName);
        } else if (StringUtils.isNotBlank(middleName)) {
            nameBuilder.append(" ");
            nameBuilder.append(middleName);
        } else if (StringUtils.isNotBlank(lastName)) {
            nameBuilder.append(" ");
            nameBuilder.append(lastName);
        }
        return nameBuilder.toString();
    }

    public static void processEvent(AllSharedPreferences allSharedPreferences, Event baseEvent) throws Exception {
        if (baseEvent != null) {
            FpJsonFormUtils.tagEvent(allSharedPreferences, baseEvent);
            JSONObject eventJson = new JSONObject(FpJsonFormUtils.gson.toJson(baseEvent));
            getSyncHelper().addEvent(baseEvent.getBaseEntityId(), eventJson);

            long lastSyncTimeStamp = getAllSharedPreferences().fetchLastUpdatedAtDate(0);
            Date lastSyncDate = new Date(lastSyncTimeStamp);
            getClientProcessorForJava().processClient(getSyncHelper().getEvents(lastSyncDate, BaseRepository.TYPE_Unsynced));
            getAllSharedPreferences().saveLastUpdatedAtDate(lastSyncDate.getTime());
            startClientProcessing();
        }
    }

    public static void startClientProcessing() {
        try {
            long lastSyncTimeStamp = getAllSharedPreferences().fetchLastUpdatedAtDate(0);
            Date lastSyncDate = new Date(lastSyncTimeStamp);
            getClientProcessorForJava().processClient(getSyncHelper().getEvents(lastSyncDate, BaseRepository.TYPE_Unprocessed));
            getAllSharedPreferences().saveLastUpdatedAtDate(lastSyncDate.getTime());
        } catch (Exception e) {
            Timber.d(e);
        }

    }

    public static Spanned fromHtml(String text) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY);
        } else {
            return Html.fromHtml(text);
        }
    }

    public static boolean launchDialer(final Activity activity, final BaseFpCallDialogContract.View callView, final String phoneNumber) {

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            // set a pending call execution request
            if (callView != null) {
                callView.setPendingCallRequest(() -> FpUtil.launchDialer(activity, callView, phoneNumber));
            }

            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, PermissionUtils.PHONE_STATE_PERMISSION_REQUEST_CODE);

            return false;
        } else {

            // Permissions should be in the module making use of this FP lib
            if (((TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number() == null) {

                Timber.i("No dial application so we launch 'Copy to clipboard'...");

                ClipboardManager clipboard = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(activity.getText(R.string.copied_phone_number), phoneNumber);
                clipboard.setPrimaryClip(clip);

                CopyToClipboardDialog copyToClipboardDialog = new CopyToClipboardDialog(activity, R.style.copy_clipboard_dialog);
                copyToClipboardDialog.setContent(phoneNumber);
                copyToClipboardDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                copyToClipboardDialog.show();
                // no phone
                Toast.makeText(activity, activity.getText(R.string.copied_phone_number), Toast.LENGTH_SHORT).show();

            } else {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null));
                activity.startActivity(intent);
            }
            return true;
        }
    }

    public static int getMemberProfileImageResourceIDentifier() {
        return R.mipmap.ic_member;
    }

    public static void processChangeFpMethod(String baseEntityId) {
        FpDao.closeFpMemberFromRegister(baseEntityId);
    }

    public static String getTranslatedMethodValue(@Nullable String fpMethod, Context context){
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
}
