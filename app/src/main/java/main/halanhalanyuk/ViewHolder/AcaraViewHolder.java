package main.halanhalanyuk.ViewHolder;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import main.halanhalanyuk.Interface.OnItemClickListener;
import main.halanhalanyuk.R;

public class AcaraViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    private OnItemClickListener listener;
    public ImageView img;
    public TextView LokasiAcara;
    public TextView WaktuAcara;
    public TextView JudulAcara;
    public Button selengkapnya;
    public AcaraViewHolder(View itemView) {
        super(itemView);
        img = (ImageView) itemView.findViewById(R.id.acara_detail_image);
        JudulAcara = (TextView) itemView.findViewById(R.id.detail_acara_judul);
        LokasiAcara = (TextView) itemView.findViewById(R.id.detail_acara_lokasi);
        WaktuAcara = (TextView) itemView.findViewById(R.id.detail_acara_tanggal);
        selengkapnya = (Button) itemView.findViewById(R.id.detail_acara_selengkapnya);
        selengkapnya.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        listener.Onclick(view, getAdapterPosition());
    }
}
