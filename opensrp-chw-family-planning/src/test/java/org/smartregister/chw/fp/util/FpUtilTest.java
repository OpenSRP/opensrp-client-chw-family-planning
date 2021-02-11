package org.smartregister.chw.fp.util;

import android.content.Context;
import android.content.res.Resources;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.smartregister.fp.R;

public class FpUtilTest {


    @Test
    public void testGetGender() {
        Context context = Mockito.mock(Context.class);
        Resources resources = Mockito.mock(Resources.class);
//
        Mockito.doReturn(resources).when(context).getResources();
        Mockito.doReturn("Male").when(resources).getString(R.string.male);
        Mockito.doReturn("Female").when(resources).getString(R.string.female);

        Assert.assertEquals("Male", FpUtil.getGenderTranslated(context, "male"));
        Assert.assertEquals("Female", FpUtil.getGenderTranslated(context, "female"));
    }

}
