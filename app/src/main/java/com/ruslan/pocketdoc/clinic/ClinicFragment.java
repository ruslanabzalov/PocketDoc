package com.ruslan.pocketdoc.clinic;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.App;
import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.clinics.Clinic;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

/**
 * Класс, описывающий фрагмент для отображения подробной информации о клинике.
 */
public class ClinicFragment extends Fragment implements ClinicContract.View {

    private static final String ARG_CLINIC = "clinic_id";

    private ImageView mClinicLogoImageView;
    private TextView mClinicNameTextView;
    private TextView mClinicAddressTextView;

    private ClinicContract.Presenter mPresenter;

    @Inject
    Picasso mPicasso;

    private int mClinicId;
    private boolean mIsClinicShowed;

    /**
     * Статический метод для создания фрагмента <code>ClinicFragment</code>
     * с необходимым аргументом.
     * @param clinicId Идентификатор мед. учреждения.
     * @return Фрагмент <code>ClinicFragment</code>.
     */
    public static Fragment newInstance(int clinicId) {
        Bundle arguments = new Bundle();
        arguments.putInt(ARG_CLINIC, clinicId);
        Fragment clinicFragment = new ClinicFragment();
        clinicFragment.setArguments(arguments);
        return clinicFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClinicId = Objects.requireNonNull(getArguments()).getInt(ARG_CLINIC);
        App.getComponent().inject(this);
        mPresenter = new ClinicPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clinic, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!mIsClinicShowed) {
            mPresenter.loadClinicInfo(mClinicId);
        }
    }

    @Override
    public void showClinicInfo(Clinic clinic) {
        mPicasso.load(clinic.getLogo()).into(mClinicLogoImageView);
        mClinicNameTextView.setText(clinic.getShortName());
        String address = clinic.getStreet() + ", " + clinic.getHouse();
        mClinicAddressTextView.setText(address);
    }

    /**
     * Метод инициализации элементов типа <code>View</code>.
     * @param view Корневой <code>View</code> элемент.
     */
    private void initViews(View view) {
        mClinicLogoImageView = view.findViewById(R.id.clinic_logo_image_view);
        mClinicNameTextView = view.findViewById(R.id.clinic_name_text_view);
        mClinicAddressTextView = view.findViewById(R.id.clinic_address_text_view);
    }
}
