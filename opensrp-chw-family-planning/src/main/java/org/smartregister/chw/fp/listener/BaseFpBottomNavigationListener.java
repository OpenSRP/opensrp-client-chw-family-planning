package org.smartregister.chw.fp.listener;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.MenuItem;

import org.smartregister.fp.R;
import org.smartregister.listener.BottomNavigationListener;
import org.smartregister.view.activity.BaseRegisterActivity;

public class BaseFpBottomNavigationListener extends BottomNavigationListener {
    private Activity context;

    public BaseFpBottomNavigationListener(Activity context) {
        super(context);
        this.context = context;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        super.onNavigationItemSelected(item);

        BaseRegisterActivity baseRegisterActivity = (BaseRegisterActivity) context;

        if (item.getItemId() == R.id.action_family) {
            baseRegisterActivity.switchToBaseFragment();
        } else if (item.getItemId() == R.id.action_scan_qr) {
            baseRegisterActivity.startQrCodeScanner();
        }

        return true;
    }
}