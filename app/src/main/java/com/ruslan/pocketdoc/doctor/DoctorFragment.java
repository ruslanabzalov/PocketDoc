package com.ruslan.pocketdoc.doctor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.doctors.Doctor;
import com.ruslan.pocketdoc.records.NewRecordFragment;
import com.squareup.picasso.Picasso;

public class DoctorFragment extends Fragment implements DoctorContract.View {

    private static final String ARG_DOCTOR = "doctor";

    private DoctorContract.Presenter mPresenter;

    private Doctor mDoctor;

    private ImageView mDoctorPhotoImageView;
    private TextView mDoctorNameTextView;
    private TextView mDoctorSpecialityTextView;
    private TextView mDoctorExperienceTextView;
    private TextView mDoctorPriceTextView;
    private TextView mDoctorDescriptionTextView;
    private Button mEnrollDoctorButton;

    public static Fragment newInstance(Doctor doctor) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DOCTOR, doctor);
        DoctorFragment doctorFragment = new DoctorFragment();
        doctorFragment.setArguments(args);
        return doctorFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.doctor_title);
        mDoctor = (Doctor) getArguments().getSerializable(ARG_DOCTOR);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (!getActivity().getTitle().equals(getString(R.string.doctor_title))) {
            getActivity().setTitle(R.string.doctor_title);
        }
        View rootView = inflater.inflate(R.layout.fragment_doctor, container, false);
        initViews(rootView);
        mPresenter = new DoctorPresenter();
        mPresenter.attachView(this);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.displayDoctor(mDoctor);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Если текущий фрагмент находится в обратном стеке,
        // то mPresenter зануляется при пересоздании активности.
        // При замене другого фрагмента на ClinicsMapFragment (во время чистки обратного стека)
        // mPresenter снова пытается занулиться, из-за чего возникает NPE.
        // По этой причине здесь необходима проверка на null!
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showDoctorInfo(Doctor doctor) {
        Picasso.get()
                .load(doctor.getPhotoUrl().replace("_small", ""))
                .into(mDoctorPhotoImageView);
        mDoctorNameTextView.setText(doctor.getName());
        mDoctorSpecialityTextView.setText(mDoctor.getSpecialities().get(0).getName());
        mDoctorExperienceTextView.setText(mDoctor.getExperience() + "");
        mDoctorPriceTextView.setText(mDoctor.getPrice() + "");
        mDoctorDescriptionTextView.setText(mDoctor.getDescription());
    }

    @Override
    public void showNewRecordUi() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.main_activity_fragment_container, NewRecordFragment.newInstance(mDoctor.getId()))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void showErrorMessage(Throwable throwable) {}

    @Override
    public void showProgressBar() {}

    @Override
    public void hideProgressBar() {}

    private void initViews(View rootView) {
        mDoctorPhotoImageView = rootView.findViewById(R.id.photo_image_view);
        mDoctorNameTextView = rootView.findViewById(R.id.name_text_view);
        mDoctorSpecialityTextView = rootView.findViewById(R.id.speciality_text_view);
        mDoctorExperienceTextView = rootView.findViewById(R.id.experience_text_view);
        mDoctorPriceTextView = rootView.findViewById(R.id.price_text_view);
        mDoctorDescriptionTextView = rootView.findViewById(R.id.desc_text_view);
        mEnrollDoctorButton = rootView.findViewById(R.id.create_record);
        mEnrollDoctorButton.setOnClickListener((View view) -> mPresenter.onCreateRecordButtonClick());
    }
}
