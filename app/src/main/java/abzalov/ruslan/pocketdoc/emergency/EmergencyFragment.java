package abzalov.ruslan.pocketdoc.emergency;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import abzalov.ruslan.pocketdoc.R;

public class EmergencyFragment extends Fragment {

    private Button mMain;
    private Button mMCHS;
    private Button mPolice;
    private Button mEmbulance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);
        initViews(view);
        return view;
    }

    private void initViews(@NonNull View view) {
        mMain = view.findViewById(R.id.main_emergency);
        mMain.setOnClickListener(main -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mMain.getText()));
            startActivity(callIntent);
        });

        mMCHS = view.findViewById(R.id.mchs);
        mMCHS.setOnClickListener(mchs -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mMCHS.getText()));
            startActivity(callIntent);
        });
        mPolice = view.findViewById(R.id.police);
        mPolice.setOnClickListener(police -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mPolice.getText()));
            startActivity(callIntent);
        });
        mEmbulance = view.findViewById(R.id.embulance);
        mEmbulance.setOnClickListener(main -> {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + mEmbulance.getText()));
            startActivity(callIntent);
        });
    }
}
