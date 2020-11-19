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
import com.skariga.simorin.model.Siswa;

import java.util.List;

public class RekapJurnalPemSekolahAdapters extends RecyclerView.Adapter<RekapJurnalPemSekolahAdapters.RecyclerViewAdapter> {

    private Context context;
    private List<Siswa> siswas;
    private ItemClicklistener itemClicklistener;

    public RekapJurnalPemSekolahAdapters(Context context, List<Siswa> siswas, ItemClicklistener itemClicklistener) {
        this.context = context;
        this.siswas = siswas;
        this.itemClicklistener = itemClicklistener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_siswa, parent, false);
        return new RecyclerViewAdapter(view, itemClicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Siswa siswa = siswas.get(position);
        holder.tv_nis.setText(siswa.getNis());
        holder.tv_nama.setText(siswa.getNama());
    }

    @Override
    public int getItemCount() {
        return siswas.size();
    }

    public class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_nama, tv_nis;
        CardView cardView;
        ItemClicklistener itemClicklistener;

        public RecyclerViewAdapter(@NonNull View itemView, ItemClicklistener itemClicklistener) {
            super(itemView);

            tv_nama = itemView.findViewById(R.id.tv_nama);
            tv_nis = itemView.findViewById(R.id.tv_nis);
            cardView = itemView.findViewById(R.id.card_view);

            this.itemClicklistener = itemClicklistener;
            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            itemClicklistener.onClickLisntener(v, getAdapterPosition());
        }
    }

    public interface ItemClicklistener {
        void onClickLisntener(View v, int position);
    }
}
