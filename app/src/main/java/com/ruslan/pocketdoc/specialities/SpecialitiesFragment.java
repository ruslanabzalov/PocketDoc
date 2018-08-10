package com.ruslan.pocketdoc.specialities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ruslan.pocketdoc.R;
import com.ruslan.pocketdoc.data.specialities.Speciality;
import com.ruslan.pocketdoc.dialogs.LoadingErrorDialogFragment;
import com.ruslan.pocketdoc.stations.StationsFragment;

import java.util.List;
import java.util.Objects;

/**
 * Класс, описывающий фрагмент, содержащий список специальностей.
 */
public class SpecialitiesFragment extends Fragment implements SpecialitiesContract.View {

    private static final String TAG = "SpecialitiesFragment";
    private static final String TAG_LOADING_ERROR_DIALOG_FRAGMENT = "LoadingErrorDialogFragment";

    private static final int LOADING_ERROR_DIALOG_REQUEST_CODE = 1;

    private SpecialitiesContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private SpecialitiesAdapter mAdapter;
    private ProgressBar mProgressBar;

    private boolean mAreSpecialitiesLoaded;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mFragmentManager = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        mPresenter = new SpecialitiesPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Objects.requireNonNull(getActivity()).setTitle(R.string.specialities_title);
        View view = inflater.inflate(R.layout.fragment_specialities, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRecyclerView.getAdapter() == null) {
            mPresenter.loadSpecialities();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            mPresenter.updateSpecialities(true);
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            if (mRecyclerView.getAdapter() == null) {
                Objects.requireNonNull(getActivity()).onBackPressed();
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_specialities, menu);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if (mAreSpecialitiesLoaded) {
            setOptionsMenuVisible(menu, true);
        } else {
            setOptionsMenuVisible(menu, false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_refresh_specialities:
                mPresenter.updateSpecialities(true);
                return true;
            case R.id.item_records_history:
                // TODO: Открыть актинвость, в которой будет отображаться история записей.
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setOptionsMenuVisible(Menu menu, boolean isVisible) {
        menu.findItem(R.id.item_refresh_specialities).setVisible(isVisible);
        menu.findItem(R.id.item_records_history).setVisible(isVisible);
    }

    @Override
    public void showSpecialities(List<Speciality> specialities) {
        mAreSpecialitiesLoaded = true;
        Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        if (mAdapter == null) {
            mAdapter = new SpecialitiesAdapter(specialities, mPresenter::chooseSpeciality);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            if (mRecyclerView.getAdapter() == null) {
                mRecyclerView.setAdapter(mAdapter);
            } else {
                if (specialities.size() != mRecyclerView.getAdapter().getItemCount()) {
                    mAdapter.updateDataSet(specialities);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
    }

    @Override
    public void showErrorDialog(Throwable throwable) {
        Log.d(TAG, throwable.getMessage(), throwable);
        if (mFragmentManager.findFragmentByTag(TAG_LOADING_ERROR_DIALOG_FRAGMENT) == null) {
            DialogFragment loadingErrorDialogFragment = new LoadingErrorDialogFragment();
            loadingErrorDialogFragment
                    .setTargetFragment(this, LOADING_ERROR_DIALOG_REQUEST_CODE);
            loadingErrorDialogFragment.show(mFragmentManager, TAG_LOADING_ERROR_DIALOG_FRAGMENT);
        }
    }

    @Override
    public void showProgressBar() {
        mRecyclerView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void hideRefreshing() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showStationsUi(String specialityId) {
        mFragmentManager.beginTransaction()
                .replace(
                        R.id.main_activity_fragment_container,
                        StationsFragment.newInstance(specialityId)
                )
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Метод инициализации элементов типа <code>View</code>.
     * @param view Корневой <code>View</code> элемент.
     */
    private void initViews(@NonNull View view) {
        mSwipeRefreshLayout = view.findViewById(R.id.specialities_refresh);
        int[] swipeRefreshColors = {
                getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark)
        };
        mSwipeRefreshLayout.setColorSchemeColors(swipeRefreshColors);
        mSwipeRefreshLayout.setOnRefreshListener(
                () -> mPresenter.updateSpecialities(false)
        );
        mRecyclerView = view.findViewById(R.id.specialities_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mProgressBar = view.findViewById(R.id.specialities_progress_bar);
    }
}
