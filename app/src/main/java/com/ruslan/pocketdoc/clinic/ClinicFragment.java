package com.ruslan.pocketdoc.clinic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Group;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
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
    private Group mClinicPhoneGroup;
    private TextView mClinicPhoneTextView;
    private Group mClinicUrlGroup;
    private TextView mClinicUrlTextView;
    private Group mClinicDescriptionGroup;
    private TextView mClinicDescriptionTextView;

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
        View rootView = inflater.inflate(R.layout.fragment_clinic, container, false);
        initViews(rootView);
        return rootView;
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
        if (!mIsClinicShowed) {
            mPicasso.load(clinic.getLogo()).into(mClinicLogoImageView);
            mClinicNameTextView.setText(clinic.getShortName());
            String address = clinic.getStreet() + ", " + clinic.getHouse();
            mClinicAddressTextView.setText(address);
            if (clinic.getPhone() == null || clinic.getPhone().equals("")) {
                mClinicPhoneGroup.setVisibility(View.GONE);
            } else {
                String incorrectPhone = clinic.getPhone();
                String correctPhone =  "+7(" + incorrectPhone.substring(1, 4) + ")" +
                        incorrectPhone.substring(4, 7) + "-" + incorrectPhone.substring(7, 9) +
                        "-" + incorrectPhone.substring(9);
                mClinicPhoneTextView.setText(correctPhone);
            }
            if (clinic.getUrl() == null || clinic.getUrl().equals("")) {
                mClinicUrlGroup.setVisibility(View.GONE);
            } else {
                String clinicUrl = clinic.getUrl();
                char lastCharacter = clinicUrl.charAt(clinicUrl.length() - 1);
                if (lastCharacter == '/') {
                    clinicUrl = clinicUrl.substring(0, clinicUrl.length() - 1);
                }
                mClinicUrlTextView.setText(clinicUrl);
            }
            if (clinic.getDescription() == null || clinic.getDescription().equals("")) {
                mClinicDescriptionGroup.setVisibility(View.GONE);
            } else {
                mClinicDescriptionTextView.setText(clinic.getDescription());
            }
            mIsClinicShowed = true;
        }
    }

    /**
     * Метод инициализации элементов типа <code>View</code>.
     * @param rootView Корневой <code>View</code> элемент.
     */
    private void initViews(@NonNull View rootView) {
        mClinicLogoImageView = rootView.findViewById(R.id.clinic_logo_image_view);
        mClinicNameTextView = rootView.findViewById(R.id.clinic_name_text_view);
        mClinicAddressTextView = rootView.findViewById(R.id.clinic_address_text_view);
        mClinicPhoneGroup = rootView.findViewById(R.id.clinic_phone_group);
        mClinicPhoneTextView = rootView.findViewById(R.id.clinic_phone_text_view);
        mClinicPhoneTextView
                .setOnClickListener(view -> onPhoneClick(mClinicPhoneTextView.getText().toString()));
        mClinicUrlGroup = rootView.findViewById(R.id.clinic_url_group);
        mClinicUrlTextView = rootView.findViewById(R.id.clinic_url_text_view);
        mClinicUrlTextView
                .setOnClickListener(view -> onUrlClick(mClinicUrlTextView.getText().toString()));
        mClinicDescriptionGroup = rootView.findViewById(R.id.clinic_description_group);
        mClinicDescriptionTextView = rootView.findViewById(R.id.clinic_description_text_view);
    }

    /**
     * Метод обработки нажатия на номер телефона для возможности звонка.
     * @param phone Номер телефона.
     */
    private void onPhoneClick(String phone) {
        Intent intent =
                new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    /**
     * Метод обработки нажатия на URL клиники.
     * @param url URL клиники.
     */
    private void onUrlClick(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(intent);
    }
}
