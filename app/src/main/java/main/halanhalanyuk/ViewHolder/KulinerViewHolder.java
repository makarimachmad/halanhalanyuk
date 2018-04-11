package main.halanhalanyuk.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import main.halanhalanyuk.Interface.OnItemClickListener;
import main.halanhalanyuk.R;

/**
 * Created by XAgusart on 4/10/2018.
 */

public class KulinerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView kuliner_detail_image;
    public TextView judulKuliner;
    public Button butonSelengkapnya;
    private OnItemClickListener click;

    public OnItemClickListener getClick() {
        return click;
    }

    public void setClick(OnItemClickListener click) {
        this.click = click;
    }

    public KulinerViewHolder(View itemView) {
        super(itemView);
        kuliner_detail_image = (ImageView) itemView.findViewById(R.id.kuliner_detail_image);
        judulKuliner = (TextView) itemView.findViewById(R.id.detail_kuliner_nama);
        butonSelengkapnya = (Button) itemView.findViewById(R.id.detail_kuliner_selengkapnya);
        butonSelengkapnya.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(click != null) click.Onclick(view, getAdapterPosition());
    }
}
