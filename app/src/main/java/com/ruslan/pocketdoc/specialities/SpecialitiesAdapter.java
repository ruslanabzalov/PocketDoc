package com.ruslan.pocketdoc.specialities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;

import java.util.List;

class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialityHolder> {

    private RecyclerItemOnClickListener<Speciality> mListener;

    private List<Speciality> mSpecialities;

    SpecialitiesAdapter(List<Speciality> specialities, RecyclerItemOnClickListener<Speciality> listener) {
        mSpecialities = specialities;
        mListener = listener;
    }

    @NonNull
    @Override
    public SpecialityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_speciality, parent, false);
        return new SpecialityHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityHolder specialityHolder, int position) {
        specialityHolder.bind(mSpecialities.get(position));
    }

    @Override
    public int getItemCount() {
        return mSpecialities.size();
    }
}

class SpecialityHolder extends RecyclerView.ViewHolder {

    private TextView mSpecNameTextView;

    private Speciality mSpeciality;

    SpecialityHolder(View view, RecyclerItemOnClickListener<Speciality> listener) {
        super(view);
        itemView.setOnClickListener(v -> listener.onRecyclerItemClickListener(mSpeciality));
        mSpecNameTextView = itemView.findViewById(R.id.spec_name_text_view);
    }

    void bind(Speciality speciality) {
        mSpeciality = speciality;
        String specName = mSpeciality.getName();
        mSpecNameTextView.setText(specName);
    }
}
