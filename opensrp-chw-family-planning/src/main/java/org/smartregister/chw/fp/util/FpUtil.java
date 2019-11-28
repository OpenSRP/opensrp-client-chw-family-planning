package org.smartregister.chw.fp.util;

import org.smartregister.chw.fp.FpLibrary;
import org.smartregister.sync.ClientProcessorForJava;
import org.smartregister.sync.helper.ECSyncHelper;

public class FpUtil {
    public static ClientProcessorForJava getClientProcessorForJava() {
        return FpLibrary.getInstance().getClientProcessorForJava();
    }

    public static ECSyncHelper getSyncHelper() {
        return FpLibrary.getInstance().getEcSyncHelper();
    }


}
