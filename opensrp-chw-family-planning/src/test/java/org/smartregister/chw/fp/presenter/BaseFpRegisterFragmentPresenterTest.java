package org.smartregister.chw.fp.presenter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.smartregister.chw.fp.contract.BaseFpRegisterFragmentContract;
import org.smartregister.chw.fp.util.FamilyPlanningConstants;

import static org.junit.Assert.*;

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


    @Test
    public void getDefaultSortQuery() {
        Assert.assertEquals(FamilyPlanningConstants.DBConstants.FAMILY_PLANNING_TABLE + "." + FamilyPlanningConstants.DBConstants.LAST_INTERACTED_WITH + " DESC ", baseFpRegisterFragmentPresenter.getDefaultSortQuery());
    }


}