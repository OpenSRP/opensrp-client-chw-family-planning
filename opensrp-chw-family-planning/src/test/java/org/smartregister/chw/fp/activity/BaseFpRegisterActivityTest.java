package org.smartregister.chw.fp.activity;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;

public class BaseFpRegisterActivityTest {

    @Mock
    private BaseFpRegisterActivity baseFpRegisterActivity = new BaseFpRegisterActivity();

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(baseFpRegisterActivity);
    }

    @Test
    public void testFormConfig() {
        Assert.assertNull(baseFpRegisterActivity.getFormConfig());
    }

}