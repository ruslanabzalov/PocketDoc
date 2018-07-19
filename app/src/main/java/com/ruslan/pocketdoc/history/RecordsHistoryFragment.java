package com.ruslan.pocketdoc.history;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslan.pocketdoc.R;

import java.util.Objects;

public class RecordsHistoryFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.records_history_title);
        View rootView = inflater.inflate(R.layout.fragment_records_history, container, false);
        return rootView;
    }
}
