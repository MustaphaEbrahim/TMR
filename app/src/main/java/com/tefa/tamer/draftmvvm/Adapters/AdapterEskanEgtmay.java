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

import com.tefa.tamer.databinding.EskanegtamyRowBinding;
import com.tefa.tamer.draftmvvm.UI.EskanEgtamy.View.EskanEgtamy;
import com.tefa.tamer.draftmvvm.Utilities.RecyclerViewHolders;

import java.util.List;

public class AdapterEskanEgtmay extends RecyclerView.Adapter<RecyclerViewHolders.EskanEgtamyViewHolder> {

    private final Context context;
    private List<EskanEgtamy> eskanEgtamyList;

    public AdapterEskanEgtmay(Context context ,List<EskanEgtamy> eskanEgtamyList){
        this.eskanEgtamyList = eskanEgtamyList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolders.EskanEgtamyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolders.EskanEgtamyViewHolder(EskanegtamyRowBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders.EskanEgtamyViewHolder holder, int position) {

        String pdfUri;

        holder.answerTitle.setText(eskanEgtamyList.get(position).getTitle());
        holder.answerDate.setText(eskanEgtamyList.get(position).getDate());
        holder.answerNumber.setText(eskanEgtamyList.get(position).getNumber());
        holder.importSide.setText(eskanEgtamyList.get(position).getImportSide());
        holder.exportSide.setText(eskanEgtamyList.get(position).getExportSide());
        pdfUri = eskanEgtamyList.get(position).getPdfUri();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString( eskanEgtamyList.get(position)
                .getTimeAdded()
                .getSeconds() * 1000);
        holder.dateAdded.setText(timeAgo);
        holder.pdf.setText(pdfUri);
        holder.pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(eskanEgtamyList.get(holder.getAdapterPosition()).getPdfUri()));
                context.startActivity(browserIntent);

            }
        });



    }

    @Override
    public int getItemCount() {
        return eskanEgtamyList.size();
    }
}
