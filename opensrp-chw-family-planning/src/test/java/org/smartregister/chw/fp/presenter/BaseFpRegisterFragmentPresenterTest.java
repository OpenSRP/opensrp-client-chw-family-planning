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

        baseFpRegisterFragmentPresenter = new BaseFpRegisterFragmentPresenter(view, model);
    }

    @Test
    public void assertNotNull() {
        Assert.assertNotNull(baseFpRegisterFragmentPresenter);
    }

    @Test
    public void getMainCondition() {
        Assert.assertEquals(" ec_family_member.date_removed is null AND ec_family_planning.is_closed = 0", baseFpRegisterFragmentPresenter.getMainCondition());
    }

}