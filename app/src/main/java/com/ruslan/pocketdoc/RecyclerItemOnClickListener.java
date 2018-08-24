package com.ruslan.pocketdoc;

/**
 * Интерфейс, описывающий callback нажатия на пользовательский <code>RecyclerView.ViewHolder</code>.
 * @param <T> Тип объекта, на который происходит нажатие.
 */
@FunctionalInterface
public interface RecyclerItemOnClickListener<T> {

    /**
     * Callback-метод, срабатывающий при нажатии
     * на пользовательский <code>RecyclerView.ViewHolder</code>.
     * @param t Объект, на который происходит нажатие.
     */
    void onRecyclerItemClickListener(T t);
}
