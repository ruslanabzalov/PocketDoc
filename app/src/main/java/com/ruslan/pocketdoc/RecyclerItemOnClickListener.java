package com.ruslan.pocketdoc;

/**
 * Интерфейс, описывающий callback нажатия на элемент RecyclerView.
 * @param <T>
 */
public interface RecyclerItemOnClickListener<T> {

    /**
     * Callback-метод, срабатывающий при нажатии на элемент RecyclerView.
     * @param t Объект, на который происходит нажатие.
     */
    void onRecyclerItemClickListener(T t);
}
