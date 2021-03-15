package com.tefa.tamer.draftmvvm.Utilities;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tefa.tamer.databinding.EskanegtamyRowBinding;

import java.net.URI;

/**
 * Created by Youssif Hamdy on 12/8/2019.
 */
public class RecyclerViewHolders {

/*
    public static class MostRequestedHolder extends RecyclerView.ViewHolder {

        public ImageView ivItem;
        public TextView txtItemName;
        public TextView txtItemPrice;

        public MostRequestedHolder(MostRequestedItemBinding itmBinding) {
            super(itmBinding.getRoot());
            ivItem = itmBinding.ivItem;
            txtItemName = itmBinding.txtDesc;
            txtItemPrice = itmBinding.txtItemPrice;
        }
    }*/

    public static class EskanEgtamyViewHolder extends RecyclerView.ViewHolder {
        public TextView
                answerTitle,
                answerDate ,
                answerNumber,
                exportSide,
                importSide,
                pdf,
                dateAdded;




        public EskanEgtamyViewHolder(EskanegtamyRowBinding itmBinding ) {
            super(itmBinding.getRoot());

            answerTitle = itmBinding.titleAnswer;
            answerDate = itmBinding.dateAnswer;
            answerNumber = itmBinding.numberAnswer;
            exportSide = itmBinding.sideExport;
            importSide = itmBinding.sideImport;
            dateAdded = itmBinding.egtmayTimestampList;
            pdf = itmBinding.pdf;

        }
    }


}
