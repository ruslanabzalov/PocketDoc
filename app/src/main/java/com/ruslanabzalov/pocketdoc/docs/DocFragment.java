package com.ruslanabzalov.pocketdoc.docs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Класс, отвечающий за запись к конкретному врачу.
 */
public class DocFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * Метод, создающий экземпляр фрагмента DocFragment, а также задающий его аргументы.
     * @return
     */
    public static DocsListFragment newInstance() {
        Bundle args = new Bundle();
        DocsListFragment fragment = new DocsListFragment();
        return fragment;
    }
}
