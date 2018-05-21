package com.ruslan.pocketdoc.searching.specialities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Speciality;
import com.ruslan.pocketdoc.searching.RecyclerItemOnClickListener;

import java.util.List;

public class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialityHolder> {

    private RecyclerItemOnClickListener<Speciality> mSpecRecyclerItemOnClickListener;

    private List<Speciality> mSpecialities;

    SpecialitiesAdapter(List<Speciality> specialities, RecyclerItemOnClickListener<Speciality> onClickListener) {
        mSpecialities = specialities;
        mSpecRecyclerItemOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SpecialityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_speciality, parent, false);
        return new SpecialityHolder(view, mSpecRecyclerItemOnClickListener);
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

class SpecialityHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerItemOnClickListener<Speciality> mSpecRecyclerItemOnClickListener;

    private TextView mSpecNameTextView;

    private Speciality mSpeciality;

    SpecialityHolder(View view, RecyclerItemOnClickListener<Speciality> onClickListener) {
        super(view);
        itemView.setOnClickListener(this);
        mSpecRecyclerItemOnClickListener = onClickListener;
        mSpecNameTextView = itemView.findViewById(R.id.spec_name_text_view);
    }

    void bind(Speciality speciality) {
        mSpeciality = speciality;
        String specName = mSpeciality.getName();
        mSpecNameTextView.setText(specName);
    }

    @Override
    public void onClick(View v) {
        mSpecRecyclerItemOnClickListener.onRecyclerItemClickListener(mSpeciality);
    }
}
