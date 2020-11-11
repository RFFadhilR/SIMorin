package com.skariga.simorin.perusahaan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.helper.Absen;

import java.util.List;

public class ListAbsenPemPerusahaanAdapter extends RecyclerView.Adapter<ListAbsenPemPerusahaanAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Absen> absens;
    private ItemClickListener itemClickListener;

    public ListAbsenPemPerusahaanAdapter(Context context, List<Absen> absens, ItemClickListener itemClickListener) {
        this.context = context;
        this.absens = absens;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_absen, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Absen absen = absens.get(position);
        holder.tv_nama.setText(absen.getNama_siswa());
        if (absen.getWaktu_pulang() == null) {
            holder.tv_tanggal.setText(absen.getTanggal() + " / " + absen.getWaktu_masuk());
            absen.setStatus("MASUK");
            holder.tv_status.setText(absen.getStatus());
            holder.tv_status.setTextColor(Color.GREEN);
        } else {
            holder.tv_tanggal.setText(absen.getTanggal() + " / " + absen.getWaktu_pulang());
            absen.setStatus("PULANG");
            holder.tv_status.setText(absen.getStatus());
            holder.tv_status.setTextColor(Color.argb(255, 255, 165, 0));
        }
    }

    @Override
    public int getItemCount() {
        return absens.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_nama, tv_tanggal, tv_status;
        CheckBox cb;
        CardView cardView;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv2);
            tv_tanggal = itemView.findViewById(R.id.tv22);
            tv_status = itemView.findViewById(R.id.tv222);
            cb = itemView.findViewById(R.id.checkBox1);
            cardView = itemView.findViewById(R.id.isi_absen);

            this.itemClickListener = itemClickListener;
            cardView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
