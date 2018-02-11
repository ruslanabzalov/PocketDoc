package com.ruslanabzalov.pocketdoc.illnesses;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ruslanabzalov.pocketdoc.MainActivity;
import com.ruslanabzalov.pocketdoc.R;

import java.util.UUID;

public class MedicamentFragment extends Fragment {

    private static final String ARG_DRUG_ID = "drug_id";

    private Medicament mMedicament;

    private EditText mMedicamentTitleEditText;
    private Button mUseButton;
    private Button mDurationInDaysButton;
    private Button mDosageButton;
    private Button mHourlyGapsButton;
    private Button mFirstReceptionButton;
    private Button mSecondReceptionButton;
    private Button mSetNotificationButton;

    public static MedicamentFragment newInstance(UUID drugId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_DRUG_ID, drugId);
        MedicamentFragment fragment = new MedicamentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.medicament_fragment_label));
        UUID medicamentId = (UUID) getArguments().getSerializable(ARG_DRUG_ID);
        mMedicament = MedicamentsList.get(getActivity()).getMedicament(medicamentId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_medicament, container, false);
        mMedicamentTitleEditText = view.findViewById(R.id.medicament_name);
        mMedicamentTitleEditText.setText(mMedicament.getName());
        mMedicamentTitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mMedicament.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        mUseButton = view.findViewById(R.id.use_button);
        mUseButton.setText("После еды");
        mDurationInDaysButton = view.findViewById(R.id.duration_in_days_button);
        mDurationInDaysButton.setText("3 дн.");
        mDosageButton = view.findViewById(R.id.dosage_button);
        mDosageButton.setText("1 табл.");
        mHourlyGapsButton = view.findViewById(R.id.hourly_gaps_button);
        mHourlyGapsButton.setText("Каждые 2 ч.");
        mFirstReceptionButton = view.findViewById(R.id.first_reception_button);
        mFirstReceptionButton.setText("10:00");
        mSecondReceptionButton = view.findViewById(R.id.second_reception_button);
        mSecondReceptionButton.setText("18:00");
        mSetNotificationButton = view.findViewById(R.id.set_notification_button);
        mSetNotificationButton.setOnClickListener(v ->
                scheduleNotification(getContext(), 1231234));
        checkButtons();
        return view;
    }

    /**
     * Метод для проверки выбора всех параметров создания напоминаний.
     */
    private void checkButtons() {
        if (!mUseButton.getText().equals("")
                && !mDurationInDaysButton.getText().equals("")
                && !mDosageButton.getText().equals("")
                && !mHourlyGapsButton.getText().equals("")
                && !mFirstReceptionButton.getText().equals("")
                && !mSecondReceptionButton.getText().equals("")
                && !mMedicamentTitleEditText.getText().toString().equals("")) {
            mSetNotificationButton.setEnabled(true);
        }
    }

    /**
     * Метод для создания запланированного напоминания.
     * @param context контекст.
     * @param notificationId идентификатор уведомления.
     */
    private void scheduleNotification(Context context, int notificationId) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Приём лекарства")
                .setContentText("Примите такое-то лекарство!")
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent activity = PendingIntent.getActivity(context, notificationId, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        builder.setContentIntent(activity);
        Notification notification = builder.build();
        Intent notificationIntent = new Intent(context, Receiver.class);
        notificationIntent.putExtra(Receiver.NOTIFICATION_ID, notificationId);
        notificationIntent.putExtra(Receiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, notificationId,
                notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                10000, pendingIntent);
        getActivity().finish();
        Toast.makeText(getContext(), "Напоминание создано!", Toast.LENGTH_SHORT);
    }
}
