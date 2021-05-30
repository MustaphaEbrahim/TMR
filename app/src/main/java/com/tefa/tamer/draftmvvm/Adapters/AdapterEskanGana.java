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

public class AdapterEskanGana extends RecyclerView.Adapter<RecyclerViewHolders.EskanGanaViewHolder> {

    private  final Context context;
    private List<modelGawab> modelGawabList;

    public AdapterEskanGana(Context context, List<modelGawab> modelGawabList) {
        this.context = context;
        this.modelGawabList = modelGawabList;
    }

    @NonNull
    @Override
    public RecyclerViewHolders.EskanGanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerViewHolders.EskanGanaViewHolder(ModelgwabRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders.EskanGanaViewHolder holder, int position) {

        holder.answerTitleGana.setText( modelGawabList.get(position).getTitle() );
        holder.answerDateGana.setText( modelGawabList.get(position).getDate());
        holder.answerNumberGana.setText( modelGawabList.get(position).getNumber());
        holder.importSideGana.setText( modelGawabList.get(position).getImportSide());
        holder.exportSideGana.setText( modelGawabList.get(position).getExportSide());

        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(modelGawabList.get(position).getTimeAdded().getSeconds() * 1000);
        holder.dateAddedGana.setText(timeAgo);
        holder.pdfGana.setText(R.string.Click_here_to_download_pdf);
        holder.pdfGana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(modelGawabList.get(holder.getAdapterPosition()).getPdfUri()));
                context.startActivity(browserIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelGawabList.size();
    }
}
