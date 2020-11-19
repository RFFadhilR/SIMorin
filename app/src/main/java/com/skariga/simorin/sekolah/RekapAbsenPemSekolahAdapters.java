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
import com.skariga.simorin.model.RekapAbsen;

import java.util.List;

public class RekapAbsenPemSekolahAdapters extends RecyclerView.Adapter<RekapAbsenPemSekolahAdapters.RecyclerViewAdapter>{

    private Context context;
    private List<RekapAbsen> rekapAbsens;
    private ItemClickListener itemClickListener;

    public RekapAbsenPemSekolahAdapters(Context context, List<RekapAbsen> rekapAbsens, ItemClickListener itemClickListener) {
        this.context = context;
        this.rekapAbsens = rekapAbsens;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_rabsen, parent, false);
        return new RecyclerViewAdapter(v, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        RekapAbsen rekapAbsen = rekapAbsens.get(position);
        holder.tv_nis.setText(rekapAbsen.getNis());
        holder.tv_nama.setText(rekapAbsen.getNama());
        holder.tv_tanggal.setText(rekapAbsen.getTanggal());
        holder.tv_total.setText("Total Jam : " + rekapAbsen.getTotal_jam());
        int status = rekapAbsen.getStatus();
        if (status == 2) {
            holder.tv_ais.setText("A / I / S : ALPHA ");
        } else {
            holder.tv_ais.setText("A / I / S : ");
        }
    }

    @Override
    public int getItemCount() {
        return rekapAbsens.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tv_nis, tv_nama, tv_tanggal, tv_ais, tv_total;
        CardView cardView;
        ItemClickListener itemClickListener;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_nis = itemView.findViewById(R.id.tv_nis);
            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_ais = itemView.findViewById(R.id.tv_AIS);
            tv_total = itemView.findViewById(R.id.tv_total);
            cardView = itemView.findViewById(R.id.card_view);

            this.itemClickListener = itemClickListener;
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onCLickListener(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onCLickListener(View v, int position);
    }
}
