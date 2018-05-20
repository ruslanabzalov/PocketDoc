package com.ruslan.pocketdoc.doc.docs;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.Doc;
import com.ruslan.pocketdoc.doc.RecyclerItemOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DocsAdapter extends RecyclerView.Adapter<DocViewHolder> {

    private RecyclerItemOnClickListener<Doc> mDocRecyclerItemOnClickListener;

    private List<Doc> mDocs;

    DocsAdapter(List<Doc> docs, RecyclerItemOnClickListener<Doc> docRecyclerItemOnClickListener) {
        mDocs = docs;
        mDocRecyclerItemOnClickListener = docRecyclerItemOnClickListener;
    }

    @NonNull
    @Override
    public DocViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item_doc, parent, false);
        return new DocViewHolder(view, mDocRecyclerItemOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DocViewHolder holder, int position) {
        holder.bind(mDocs.get(position));
    }

    @Override
    public int getItemCount() {
        return mDocs.size();
    }
}

class DocViewHolder extends RecyclerView.ViewHolder {

    private ImageView mDocPhotoImageView;
    private TextView mDocNameTextView;
    private TextView mDocRatingTextView;

    private Doc mDoc;

    DocViewHolder(View view, RecyclerItemOnClickListener<Doc> docRecyclerItemOnClickListener) {
        super(view);
        itemView.setOnClickListener((View v)
                -> docRecyclerItemOnClickListener.onRecyclerItemClickListener(mDoc));
        mDocPhotoImageView = itemView.findViewById(R.id.doc_photo_image_view);
        mDocNameTextView = itemView.findViewById(R.id.doc_name_text_view);
        mDocRatingTextView = itemView.findViewById(R.id.doc_rating_text_view);
    }

    void bind(Doc doc) {
        mDoc = doc;
        Picasso.get()
                .load(mDoc.getPhoto())
                .into(mDocPhotoImageView);
        mDocNameTextView.setText(mDoc.getName());
        mDocRatingTextView.setText(mDoc.getRating());
    }
}
