package com.ruslanabzalov.pocketdoc.doctors;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslanabzalov.pocketdoc.R;

public class DoctorsListFragment extends Fragment {

    private RecyclerView mDoctorsListRecyclerView;
    private RecyclerView.Adapter mDoctorsListAdapter;

    private String[] mSimpleStringsForTextView = new String[333];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctors_list, container, false);
        mDoctorsListRecyclerView = view.findViewById(R.id.doctors_list_recycler_view);
        mDoctorsListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDoctorsListRecyclerView.setHasFixedSize(true);
        mDoctorsListAdapter = new DoctorListAdapter(mSimpleStringsForTextView);
        mDoctorsListRecyclerView.setAdapter(mDoctorsListAdapter);
        return view;
    }

    public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

        private String[] mSimpleStringsForTextView;

        class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mSimpleTextView;

            ViewHolder(View view) {
                super(view);
                mSimpleTextView = itemView.findViewById(R.id.simple_text_view);
            }
        }

        DoctorListAdapter(String[] simpleStringsForTextView) {
            mSimpleStringsForTextView = simpleStringsForTextView;
        }

        @NonNull
        @Override
        public DoctorListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                               int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.simple_text_view, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.mSimpleTextView.setText("Lol");
        }

        /**
         * Метод, возвращающий кол-во элементов списка.
         * @return Кол-во элементов списка.
         */
        @Override
        public int getItemCount() {
            return mSimpleStringsForTextView.length;
        }
    }
}
