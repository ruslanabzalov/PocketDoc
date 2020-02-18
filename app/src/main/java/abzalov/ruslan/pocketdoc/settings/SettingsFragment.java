package abzalov.ruslan.pocketdoc.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Objects;

import abzalov.ruslan.pocketdoc.R;

/**
 * Фрагмент, отображающий настройки приложения.
 */
public final class SettingsFragment extends Fragment {

    private Button mEmergencyButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.doctors_title);
        View view = inflater.inflate(R.layout.settings_fragment, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        mEmergencyButton = view.findViewById(R.id.emergency_button);
//        mEmergencyButton.setOnClickListener(some_view -> {
//
//        });
    }
}
