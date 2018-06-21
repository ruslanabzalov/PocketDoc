package com.ruslan.pocketdoc.specialities;

import com.ruslan.pocketdoc.data.Repository;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class SpecialitiesPresenterTest {

    private SpecialitiesContract.Presenter mPresenter;

    @Mock
    private SpecialitiesContract.View mView;
    private Repository mRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mPresenter = new SpecialitiesPresenter(mView, mRepository);
    }

    @Test
    public void onSpecialityClickTest() {
        Speciality speciality = new Speciality();
        String specialityId = speciality.getId();
        mPresenter.onSpecialityClick(speciality);
        Mockito.verify(mView).showStationListUi(specialityId);
    }
}