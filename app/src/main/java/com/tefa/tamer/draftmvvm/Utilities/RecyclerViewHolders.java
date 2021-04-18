package com.tefa.tamer.draftmvvm.Utilities;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.tefa.tamer.databinding.ModelgwabRowBinding;


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

    public static class ModelGwabViewHodel extends RecyclerView.ViewHolder {
        public TextView
                answerTitle,
                answerDate ,
                answerNumber,
                exportSide,
                importSide,
                pdf,
                dateAdded;

        public Button editButton ,
                deleteButton;




        public ModelGwabViewHodel (ModelgwabRowBinding itmBinding ) {
            super(itmBinding.getRoot());

            answerTitle = itmBinding.titleAnswer;
            answerDate = itmBinding.dateAnswer;
            answerNumber = itmBinding.numberAnswer;
            exportSide = itmBinding.sideExport;
            importSide = itmBinding.sideImport;
            dateAdded = itmBinding.egtmayTimestampList;
            pdf = itmBinding.pdf;
            editButton = itmBinding.editButton;
            deleteButton = itmBinding.deleteButton;

        }
    }

    public static class EskanGanaViewHolder extends RecyclerView.ViewHolder{

        public TextView
                answerTitleGana,
                answerDateGana ,
                answerNumberGana,
                exportSideGana,
                importSideGana,
                pdfGana,
                dateAddedGana;

        public Button editButtonGana ,
                deleteButtonGana;

        public EskanGanaViewHolder(ModelgwabRowBinding itmBinding) {
            super(itmBinding.getRoot());

            answerTitleGana = itmBinding.titleAnswer;
            answerDateGana = itmBinding.dateAnswer;
            answerNumberGana = itmBinding.numberAnswer;
            exportSideGana = itmBinding.sideExport;
            importSideGana = itmBinding.sideImport;
            dateAddedGana = itmBinding.egtmayTimestampList;
            pdfGana = itmBinding.pdf;
            editButtonGana = itmBinding.editButton;
            deleteButtonGana = itmBinding.deleteButton;

        }
    }


}
