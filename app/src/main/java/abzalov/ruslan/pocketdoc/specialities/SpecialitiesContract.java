package abzalov.ruslan.pocketdoc.specialities;

import java.util.List;

import abzalov.ruslan.pocketdoc.BaseContract;
import abzalov.ruslan.pocketdoc.data.specialities.Speciality;

public interface SpecialitiesContract {

    interface View extends BaseContract.BaseView {

        void showSpecialities(List<Speciality> specialities);

        void showStationsUi(String specialityId);
    }

    interface Presenter extends BaseContract.BasePresenter<View> {

        void loadSpecialities();

        void updateSpecialities(boolean isMenuRefreshing);

        void chooseSpeciality(Speciality speciality);
    }
}
