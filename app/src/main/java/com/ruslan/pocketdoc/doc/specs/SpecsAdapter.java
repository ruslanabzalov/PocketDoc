package com.ruslan.pocketdoc.doc.specs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Spec;
import com.ruslan.pocketdoc.doc.RecyclerItemOnClickListener;

import java.util.List;

public class SpecsAdapter extends RecyclerView.Adapter<SpecsHolder> {

    private RecyclerItemOnClickListener<Spec> mSpecRecyclerItemOnClickListener;

    private List<Spec> mSpecialities;

    SpecsAdapter(List<Spec> specialities, RecyclerItemOnClickListener<Spec> onClickListener) {
        mSpecialities = specialities;
        mSpecRecyclerItemOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SpecsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_spec, parent, false);
        return new SpecsHolder(view, mSpecRecyclerItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecsHolder specsHolder, int position) {
        specsHolder.bind(mSpecialities.get(position));
    }

    @Override
    public int getItemCount() {
        return mSpecialities.size();
    }
}

class SpecsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private RecyclerItemOnClickListener<Spec> mSpecRecyclerItemOnClickListener;

    private TextView mSpecNameTextView;

    private Spec mSpec;

    SpecsHolder(View view, RecyclerItemOnClickListener<Spec> onClickListener) {
        super(view);
        itemView.setOnClickListener(this);
        mSpecRecyclerItemOnClickListener = onClickListener;
        mSpecNameTextView = itemView.findViewById(R.id.spec_name_text_view);
    }

    void bind(Spec spec) {
        mSpec = spec;
        String specName = mSpec.getName();
        mSpecNameTextView.setText(specName);
    }

    @Override
    public void onClick(View v) {
        mSpecRecyclerItemOnClickListener.onRecyclerItemClickListener(mSpec);
    }
}
