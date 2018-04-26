package com.ruslanabzalov.pocketdoc.diseases;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ruslanabzalov.pocketdoc.R;

public class DiseasesListFragment extends Fragment {

    private RecyclerView mDiseasesListRecyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(getString(R.string.title_diseases));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diseases_list, container, false);
        mDiseasesListRecyclerView = view.findViewById(R.id.diseases_list_recycler_view);
        mDiseasesListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }
}
