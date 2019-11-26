package org.smartregister.chw.fp.presenter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.fp.contract.BaseFpRegisterFragmentContract;

public class BaseFpRegisterFragmentPresenterTest {

    @Mock
    protected BaseFpRegisterFragmentContract.View view;

    @Mock
    protected BaseFpRegisterFragmentContract.Model model;

    private BaseFpRegisterFragmentPresenter baseFpRegisterFragmentPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        baseFpRegisterFragmentPresenter = new BaseFpRegisterFragmentPresenter(view, model, "");
    }

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(baseFpRegisterFragmentPresenter);
    }

    @Test
    public void getMainCondition() {
        Assert.assertEquals("", baseFpRegisterFragmentPresenter.getMainCondition());
    }

}