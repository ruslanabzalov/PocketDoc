package com.ruslan.pocketdoc;

@FunctionalInterface
public interface RecyclerItemOnClickListener<T> {

    void onRecyclerItemClickListener(T t);
}
