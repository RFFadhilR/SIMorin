package com.skariga.simorin.perusahaan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.model.AbsenPerusahaan;
import com.skariga.simorin.model.JurnalPerusahaan;

import java.util.ArrayList;
import java.util.List;

public class ListAbsenPemPerusahaanAdapter extends RecyclerView.Adapter<ListAbsenPemPerusahaanAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<AbsenPerusahaan> absenPerusahaans;
    private ItemClickListener itemClickListener;

    public ListAbsenPemPerusahaanAdapter(Context context, List<AbsenPerusahaan> absenPerusahaans, ItemClickListener itemClickListener) {
        this.context = context;
        this.absenPerusahaans = absenPerusahaans;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_absen_perusahaan, parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        holder.bind(absenPerusahaans.get(position));
//        AbsenPerusahaan absenPerusahaan = absenPerusahaans.get(position);
//        holder.tv_nama.setText(absenPerusahaan.getNama_siswa());
//        if (absenPerusahaan.getWaktu_pulang() == null) {
//            holder.tv_tanggal.setText(absenPerusahaan.getTanggal() + " / " + absenPerusahaan.getWaktu_masuk());
//            absenPerusahaan.setStatus("MASUK");
//            holder.tv_status.setText(absenPerusahaan.getStatus());
//            holder.tv_status.setTextColor(Color.GREEN);
//        } else {
//            holder.tv_tanggal.setText(absenPerusahaan.getTanggal() + " / " + absenPerusahaan.getWaktu_pulang());
//            absenPerusahaan.setStatus("PULANG");
//            holder.tv_status.setText(absenPerusahaan.getStatus());
//            holder.tv_status.setTextColor(Color.argb(255, 255, 165, 0));
//        }
    }

    @Override
    public int getItemCount() {
        return absenPerusahaans.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_nama, tv_tanggal, tv_status, lihat_lokasi;
        CheckBox cb;
        CardView cardView;
        LinearLayout ll;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv2);
            tv_tanggal = itemView.findViewById(R.id.tv22);
            tv_status = itemView.findViewById(R.id.tv222);
            cb = itemView.findViewById(R.id.checkBox1);
            cardView = itemView.findViewById(R.id.isi_absen);
            lihat_lokasi = itemView.findViewById(R.id.tv_lihat);
            ll = itemView.findViewById(R.id.linear_ly);

            this.itemClickListener = itemClickListener;
            lihat_lokasi.setOnClickListener(this);

        }

        void bind(final AbsenPerusahaan absenPerusahaan) {
            tv_nama.setText(absenPerusahaan.getNama_siswa());
            if (absenPerusahaan.getWaktu_pulang() == null) {
                if (absenPerusahaan.getStatus() == 1) {
                    tv_nama.setTextColor(Color.WHITE);
                    tv_tanggal.setTextColor(Color.WHITE);
                    ll.setBackground(ContextCompat.getDrawable(context, R.drawable.capsule_terima));
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_terima));
                } else if (absenPerusahaan.getStatus() == 2) {
                    tv_nama.setTextColor(Color.WHITE);
                    tv_tanggal.setTextColor(Color.WHITE);
                    ll.setBackground(ContextCompat.getDrawable(context, R.drawable.capsule_tolak));
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_tolak));
                } else {
                    tv_nama.setTextColor(Color.BLACK);
                    tv_tanggal.setTextColor(Color.BLACK);
                    ll.setBackground(ContextCompat.getDrawable(context, R.drawable.capsule_putih));
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_putih));
                }
                tv_tanggal.setText(absenPerusahaan.getTanggal() + " / " + absenPerusahaan.getWaktu_masuk());
                absenPerusahaan.setKeterangan("MASUK");
                tv_status.setText(absenPerusahaan.getKeterangan());
                tv_status.setTextColor(Color.GREEN);
                cb.setChecked(absenPerusahaan.isChecked() ? true : false);
                cardView.setOnClickListener(v -> {
                    absenPerusahaan.setChecked(!absenPerusahaan.isChecked());
                    cb.setChecked(absenPerusahaan.isChecked() ? true : false);
                });
            } else {
                if (absenPerusahaan.getStatus() == 1) {
                    tv_nama.setTextColor(Color.WHITE);
                    tv_tanggal.setTextColor(Color.WHITE);
                    ll.setBackground(ContextCompat.getDrawable(context, R.drawable.capsule_terima));
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_terima));
                } else if (absenPerusahaan.getStatus() == 2) {
                    tv_nama.setTextColor(Color.WHITE);
                    tv_tanggal.setTextColor(Color.WHITE);
                    ll.setBackground(ContextCompat.getDrawable(context, R.drawable.capsule_tolak));
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_tolak));
                } else {
                    tv_nama.setTextColor(Color.BLACK);
                    tv_tanggal.setTextColor(Color.BLACK);
                    ll.setBackground(ContextCompat.getDrawable(context, R.drawable.capsule_putih));
                    ll.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.capsule_putih));
                }
                tv_tanggal.setText(absenPerusahaan.getTanggal() + " / " + absenPerusahaan.getWaktu_masuk() + " - " + absenPerusahaan.getWaktu_pulang());
                absenPerusahaan.setKeterangan("PULANG");
                tv_status.setText(absenPerusahaan.getKeterangan());
                tv_status.setTextColor(Color.argb(255, 255, 165, 0));
                cb.setChecked(absenPerusahaan.isChecked() ? true : false);
                cardView.setOnClickListener(v -> {
                    absenPerusahaan.setChecked(!absenPerusahaan.isChecked());
                    cb.setChecked(absenPerusahaan.isChecked() ? true : false);
                });
            }
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public List<AbsenPerusahaan> getAllData() {
        ArrayList<AbsenPerusahaan> allData = new ArrayList<>();

        for (int i = 0; i < absenPerusahaans.size(); i++) {
            allData.add(absenPerusahaans.get(i));
        }

        return allData;
    }

    public List<AbsenPerusahaan> getSelected() {
        ArrayList<AbsenPerusahaan> selected = new ArrayList<>();
        for (int i = 0; i < absenPerusahaans.size(); i++) {
            if (absenPerusahaans.get(i).isChecked()) {
                selected.add(absenPerusahaans.get(i));
            }
        }
        return selected;
    }

}
