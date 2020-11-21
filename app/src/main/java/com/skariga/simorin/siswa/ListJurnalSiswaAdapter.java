package com.skariga.simorin.siswa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.model.JurnalPerusahaan;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.List;

public class ListJurnalSiswaAdapter extends RecyclerView.Adapter<ListJurnalSiswaAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<JurnalPerusahaan> jurnalPerusahaans;
    private ItemClickListener itemClickListener;

    public ListJurnalSiswaAdapter(Context context, List<JurnalPerusahaan> jurnalPerusahaans, ItemClickListener itemClickListener) {
        this.context = context;
        this.jurnalPerusahaans = jurnalPerusahaans;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_jurnal_siswa, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        JurnalPerusahaan jurnalPerusahaan = jurnalPerusahaans.get(position);
        holder.tv_tanggal.setText(jurnalPerusahaan.getTanggal() + " / " + jurnalPerusahaan.getWaktu_masuk() + " - " + jurnalPerusahaan.getWaktu_pulang());
        holder.tv_kegiatan.setText(jurnalPerusahaan.getKegiatan());
        holder.tv_kegiatan.setShowingLine(2);
        holder.tv_kegiatan.addShowLessText("Lebih Dikit");
        holder.tv_kegiatan.addShowMoreText("Lebih Banyak");
    }

    @Override
    public int getItemCount() {
        return jurnalPerusahaans.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemClickListener itemClickListener;
        TextView tv_tanggal;
        ShowMoreTextView tv_kegiatan;
        CardView cardView;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_kegiatan = itemView.findViewById(R.id.kegiatan);
            tv_tanggal = itemView.findViewById(R.id.tv22);
            cardView = itemView.findViewById(R.id.card_view);

            this.itemClickListener = itemClickListener;
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClickListener(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onClickListener(View v, int position);
    }
}
