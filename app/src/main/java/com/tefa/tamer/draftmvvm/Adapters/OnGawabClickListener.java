package com.tefa.tamer.draftmvvm.Adapters;


import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;

public interface OnGawabClickListener {
    void onGawabEditClick(int adapterPosition, modelGawab gawab);

    void onGawabDeleteClick(int adapterPosition, modelGawab gawab);
}
