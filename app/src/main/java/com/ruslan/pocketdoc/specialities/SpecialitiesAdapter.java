package com.ruslan.pocketdoc.specialities;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.RecyclerItemOnClickListener;
import com.ruslan.pocketdoc.data.specialities.Speciality;

import java.util.List;

/**
 * Класс, описывающий пользовательский <code>RecyclerView.Adapter</code>.
 */
class SpecialitiesAdapter extends RecyclerView.Adapter<SpecialitiesAdapter.SpecialityHolder> {

    private RecyclerItemOnClickListener<Speciality> mListener;

    private List<Speciality> mSpecialities;

    SpecialitiesAdapter(List<Speciality> specialities,
                        RecyclerItemOnClickListener<Speciality> listener) {
        mSpecialities = specialities;
        mListener = listener;
    }

    @NonNull
    @Override
    public SpecialityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_speciality, parent, false);
        return new SpecialityHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityHolder holder, int position) {
        Speciality speciality = mSpecialities.get(position);
        holder.bind(speciality);
    }

    @Override
    public int getItemCount() {
        return mSpecialities.size();
    }

    void updateDataSet(List<Speciality> specialities) {
        mSpecialities = specialities;
        notifyDataSetChanged();
    }

    /**
     * Вложенный класс, описывающий пользовательский <code>RecyclerView.ViewHolder</code>.
     */
    static class SpecialityHolder extends RecyclerView.ViewHolder {

        private TextView mSpecialityNameTextView;

        private Speciality mSpeciality;

        private SpecialityHolder(View view, RecyclerItemOnClickListener<Speciality> listener) {
            super(view);
            mSpecialityNameTextView = itemView.findViewById(R.id.speciality_name_text_view);
            itemView.setOnClickListener(v -> listener.onRecyclerItemClickListener(mSpeciality));
        }

        /**
         * Метод привязки данных специальности к экземпляру {@link SpecialityHolder}.
         * @param speciality Специальность.
         */
        private void bind(@NonNull Speciality speciality) {
            mSpeciality = speciality;
            mSpecialityNameTextView.setText(speciality.getName());
        }
    }
}
