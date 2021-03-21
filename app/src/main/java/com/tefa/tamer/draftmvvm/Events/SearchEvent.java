package com.tefa.tamer.draftmvvm.Events;

/**
 * Created by Youssif Hamdy on 6/26/2020.
 */
public class SearchEvent {

    private String searchValue;

    public SearchEvent(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getSearchValue() {
        return searchValue;
    }
}
