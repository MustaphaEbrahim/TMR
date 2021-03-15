package com.tefa.tamer.draftmvvm.Base;

public interface OnDialogActionResponse<T> {

    void onPositiveButton(T response);

    void onNegativeButton();
}
