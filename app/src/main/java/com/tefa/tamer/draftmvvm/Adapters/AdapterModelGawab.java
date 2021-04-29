package com.tefa.tamer.draftmvvm.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tefa.tamer.R;
import com.tefa.tamer.databinding.ModelgwabRowBinding;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.modelGawab;
import com.tefa.tamer.draftmvvm.Utilities.RecyclerViewHolders;

import java.util.List;

public class AdapterModelGawab extends RecyclerView.Adapter<RecyclerViewHolders.ModelGwabViewHodel> {

    private final Context context;
    private List<modelGawab> modelGawabList;
    private  OnGawabClickListener clickListener;

    public AdapterModelGawab(Context context, List<modelGawab> modelGawabList, OnGawabClickListener clickListener){
        this.modelGawabList = modelGawabList;
        this.context = context;
        this.clickListener = clickListener;

    }

    @NonNull
    @Override
    public RecyclerViewHolders.ModelGwabViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolders.ModelGwabViewHodel(ModelgwabRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders.ModelGwabViewHodel holder, int position) {

        //String pdfUri;

        holder.answerTitle.setText(modelGawabList.get(position).getTitle());
        holder.answerDate.setText( modelGawabList.get(position).getDate());
        holder.answerNumber.setText(modelGawabList.get(position).getNumber());
        holder.importSide.setText(modelGawabList.get(position).getImportSide());
        holder.exportSide.setText( modelGawabList.get(position).getExportSide());
        //pdfUri = modelGawabList.get(position).getPdfUri();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString( modelGawabList.get(position)
                .getTimeAdded()
                .getSeconds() * 1000);
        holder.dateAdded.setText(timeAgo);
        holder.pdf.setText(R.string.Click_here_to_download_pdf);
        holder.pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*PdfHandler pdfHandler = new PdfHandler(context);
                pdfHandler.openPdf("document.pdf");*/
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(modelGawabList.get(holder.getAdapterPosition()).getPdfUri()));
                context.startActivity(browserIntent);

            }
        });

        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onGawabEditClick(holder.getAdapterPosition(),modelGawabList.get(holder.getAdapterPosition()));
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onGawabDeleteClick(holder.getAdapterPosition(),modelGawabList.get(holder.getAdapterPosition()));
            }
        });

        /*holder.editButton.setOnClickListener(new View.OnGawabClickListener() {
            @Override
            public void onClick(View v) {
                modelGawab modelGawab = modelGawabList.get(position);
                editItem(modelGawab);
            }


    }

    /*
    private void editItem(modelGawab modelGawab) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.confirmation_pop, null);

        Button noButton = view.findViewById(R.id.conf_no_button);
        Button yesButton = view.findViewById(R.id.conf_yes_button);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        yesButton.setOnClickListener(new View.OnGawabClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        noButton.setOnClickListener(new View.OnGawabClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
*/

    }

    @Override
    public int getItemCount() {
        return modelGawabList.size();
    }


}
