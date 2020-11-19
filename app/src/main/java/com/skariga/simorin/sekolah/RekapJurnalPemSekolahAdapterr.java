package com.skariga.simorin.sekolah;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.model.RekapJurnal;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.List;

public class RekapJurnalPemSekolahAdapterr extends RecyclerView.Adapter<RekapJurnalPemSekolahAdapterr.RecyclerViewAdapter> {

    private Context context;
    private List<RekapJurnal> jurnals;
    private ItemClickListener itemClickListener;

    public RekapJurnalPemSekolahAdapterr(Context context, List<RekapJurnal> jurnals, ItemClickListener itemClickListener) {
        this.context = context;
        this.jurnals = jurnals;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RekapJurnalPemSekolahAdapterr.RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rjurnal, parent, false);
        return new RecyclerViewAdapter(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RekapJurnalPemSekolahAdapterr.RecyclerViewAdapter holder, int position) {
        RekapJurnal rekapJurnal = jurnals.get(position);
        holder.tv_tanggal.setText(rekapJurnal.getTanggal() + " / " + rekapJurnal.getWaktu_masuk() + " - " + rekapJurnal.getWaktu_pulang());
        holder.tv_spek.setText(rekapJurnal.getSpek());
        holder.tv_prosedur.setText(rekapJurnal.getProsedur());
        holder.tv_kerja.setText(rekapJurnal.getKegiatana());
    }

    @Override
    public int getItemCount() {
        return jurnals.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView cardView;
        TextView tv_tanggal;
        ShowMoreTextView tv_kerja, tv_prosedur, tv_spek;
        ItemClickListener itemClickListener;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            tv_kerja = itemView.findViewById(R.id.kerja);
            tv_prosedur = itemView.findViewById(R.id.prosedur);
            tv_spek = itemView.findViewById(R.id.spek);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);

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
