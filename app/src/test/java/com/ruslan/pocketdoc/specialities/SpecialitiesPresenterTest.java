package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.BuildConfig;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(
        application = TestApplication.class,
        constants = BuildConfig.class,
        sdk = 25
)
public class SpecialitiesPresenterTest {

    @Mock
    private SpecialitiesContract.View mView;

    private SpecialitiesContract.Presenter mPresenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SpecialitiesPresenter();
        mPresenter.attachView(mView);
    }

    @Test
    public void chooseSpeciality_ShowStationsListUi() {
        mPresenter.chooseSpeciality(new Speciality("109302"));
        Mockito.verify(mView).showStationListUi(Mockito.anyString());
    }
}