package com.pocketdoc.pocketdoc.doc.docs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.pocketdoc.pocketdoc.R;
import com.pocketdoc.pocketdoc.api.DocDocApi;
import com.pocketdoc.pocketdoc.api.DocDocClient;
import com.pocketdoc.pocketdoc.data.Doc;
import com.pocketdoc.pocketdoc.data.DocList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Фрагмент для отображения списка докторов.
 */
public class DocsFragment extends Fragment {

    private static final String TAG = "DocsFragment";
    private static final String ARG_SPEC_ID = "spec_id";
    private static final String ARG_STATION_ID = "station_id";
    private static final int MOSCOW_ID = 1;

    private RecyclerView mDocsRecyclerView;

    private List<Doc> mDocs = new ArrayList<>();
    private String mDocsSpecId;
    private String mDocsStationId;

    public static Fragment newInstance(String specId, String stationId) {
        Bundle arguments = new Bundle();
        arguments.putString(ARG_SPEC_ID, specId);
        arguments.putString(ARG_STATION_ID, stationId);
        DocsFragment fragment = new DocsFragment();
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: Исправить warning.
        mDocsSpecId = getArguments().getString(ARG_SPEC_ID, null);
        mDocsStationId = getArguments().getString(ARG_STATION_ID, null);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_docs, container, false);
        mDocsRecyclerView = view.findViewById(R.id.docs_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mDocsRecyclerView.setLayoutManager(linearLayoutManager);
        DocDocApi api = DocDocClient.getClient();
        getDocs(api, MOSCOW_ID, mDocsSpecId, mDocsStationId);
        return view;
    }

    private class DocsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mDocPhotoImageView;
        private TextView mDocNameTextView;
        private TextView mDocRatingTextView;

        private Doc mDoc;

        private DocsViewHolder(View view) {
            super(view);
            itemView.setOnClickListener(this);
            mDocPhotoImageView = itemView.findViewById(R.id.doc_photo_image_view);
            mDocNameTextView = itemView.findViewById(R.id.doc_name_text_view);
            mDocRatingTextView = itemView.findViewById(R.id.doc_rating_text_view);
        }

        private void bind(Doc doc) {
            mDoc = doc;
            String docName = mDoc.getName();
            String docRating = mDoc.getRating();
            String docPhoto = mDoc.getPhoto();
            mDocNameTextView.setText(docName);
            mDocRatingTextView.setText(docRating);
            Picasso.get()
                    .load(docPhoto)
                    .into(mDocPhotoImageView);
        }

        @Override
        public void onClick(View v) {
            // TODO: Обработать нажатие на определённого врача в списке.
        }
    }

    private class DocsAdapter extends RecyclerView.Adapter<DocsViewHolder> {

        private List<Doc> mDocs;

        private DocsAdapter(List<Doc> docs) {
            mDocs = docs;
        }

        @NonNull
        @Override
        public DocsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater
                    .from(parent.getContext())
                    .inflate(R.layout.list_item_doc, parent, false);
            return new DocsViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull DocsViewHolder viewHolder, int position) {
            viewHolder.bind(mDocs.get(position));
        }

        @Override
        public int getItemCount() {
            return mDocs.size();
        }
    }


    private void getDocs(DocDocApi api, int cityId, String specId, String stationId) {
        Call<DocList> docsCall = api.getDocs(
                DocDocClient.AUTHORIZATION, 0, 500, cityId, specId, stationId,
                "strict", "rating", 0, 0, 1, 14
        );
        docsCall.enqueue(new Callback<DocList>() {
            @Override
            public void onResponse(@NonNull Call<DocList> call,
                                   @NonNull Response<DocList> response) {
                DocList docList = response.body();
                if (docList != null) {
                    mDocs = docList.getDocList();
                    DocsAdapter docsAdapter = new DocsAdapter(mDocs);
                    mDocsRecyclerView.setAdapter(docsAdapter);
                }
            }

            @Override
            public void onFailure(@NonNull Call<DocList> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), R.string.error_toast, Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }
}
