package com.skariga.simorin.perusahaan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.skariga.simorin.R;
import com.skariga.simorin.model.JurnalPerusahaan;
import com.skyhope.showmoretextview.ShowMoreTextView;

import java.util.ArrayList;
import java.util.List;

public class ListJurnalPemPerusahaanAdapter extends RecyclerView.Adapter<ListJurnalPemPerusahaanAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<JurnalPerusahaan> jurnalPerusahaans;

    public ListJurnalPemPerusahaanAdapter(Context context, List<JurnalPerusahaan> jurnalPerusahaans) {
        this.context = context;
        this.jurnalPerusahaans = jurnalPerusahaans;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_jurnal_perusahaan, parent, false);
        return new RecyclerViewAdapter(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        holder.bind(jurnalPerusahaans.get(position));
//        JurnalPerusahaan jurnal = jurnalPerusahaans.get(position);
//        holder.tv_nama.setText(jurnal.getNama_siswa());
//        holder.tv_tanggal.setText(jurnal.getTanggal() + " / " + jurnal.getWaktu_masuk());
//        holder.tv_kegiatan.setText(jurnal.getKegiatan());
//        holder.tv_kegiatan.setShowingLine(2);
//        holder.tv_kegiatan.addShowLessText("Lebih Dikit");
//        holder.tv_kegiatan.addShowMoreText("Lebih Banyak");
    }

    @Override
    public int getItemCount() {
        return jurnalPerusahaans.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder {

        TextView tv_tanggal, tv_nama;
        ShowMoreTextView tv_kegiatan;
        CheckBox cb;
        CardView card_view;

        public RecyclerViewAdapter(View itemView) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv2);
            tv_tanggal = itemView.findViewById(R.id.tv22);
            tv_kegiatan = itemView.findViewById(R.id.kegiatan);
            cb = itemView.findViewById(R.id.checkBox1);
            card_view = itemView.findViewById(R.id.card_view);

        }

        void bind(final JurnalPerusahaan jurnal) {
            tv_nama.setText(jurnal.getNama_siswa());
            tv_tanggal.setText(jurnal.getTanggal() + " / " + jurnal.getWaktu_masuk());
            tv_kegiatan.setText(jurnal.getKegiatan());
            tv_kegiatan.setShowingLine(2);
            tv_kegiatan.addShowLessText("Lebih Dikit");
            tv_kegiatan.addShowMoreText("Lebih Banyak");
            cb.setChecked(jurnal.isChecked() ? true : false);
            card_view.setOnClickListener(v -> {
                jurnal.setChecked(!jurnal.isChecked());
                cb.setChecked(jurnal.isChecked() ? true : false);
            });
        }

    }

    public List<JurnalPerusahaan> getAllData() {
        ArrayList<JurnalPerusahaan> allData = new ArrayList<>();

        for (int i = 0; i < jurnalPerusahaans.size(); i++) {
            allData.add(jurnalPerusahaans.get(i));
        }

        return allData;
    }

    public List<JurnalPerusahaan> getSelected() {
        ArrayList<JurnalPerusahaan> selected = new ArrayList<>();
        for (int i = 0; i < jurnalPerusahaans.size(); i++) {
            if (jurnalPerusahaans.get(i).isChecked()) {
                selected.add(jurnalPerusahaans.get(i));
            }
        }
        return selected;
    }
}
