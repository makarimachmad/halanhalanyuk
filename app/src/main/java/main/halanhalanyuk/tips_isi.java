package main.halanhalanyuk;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
* Created by witono on 01/04/2018.
*/
public class tips_isi extends PagerAdapter{

    Context context;
    LayoutInflater layoutInflater;

    public tips_isi(Context context){
        this.context = context;
    }

    //arrays
    public int[] slide_gambar = {
            R.drawable.medical,
            R.drawable.notes,
            R.drawable.shopping,
            R.drawable.agenda,
            R.drawable.placeholder
    };

    public String[] slide_heading = {

            "Jaga Kesehatan",
            "Bawa Uang Tunai",
            "List Belanjaan",
            "Buat Jadwal",
            "Lihat Lokasi"
    };

    public String[] slide_deskripsi = {

            "fisik yang kuat dan sehat menjadikan liburan menjadi menyenangkan",
            "membawa uang yang tidak berlebih pada dompet untk keadaan mendesak",
            "daftar barang belanjaan yang akan dibeli sebagi buah tangan",
            "agendakan kegiatan supaya kegiatan tidak ada yang bertabrakan dan terlewatkan",
            "caritahu lokasi yang ingin dituju supaya tidak tersesat dan tidak memakan waktu banyak"
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        ImageView slideimageview = view.findViewById(R.id.gambar);
        TextView slidetextview = view.findViewById(R.id.heading);
        TextView slidedeskripsi = view.findViewById(R.id.deskripsi);

        slidetextview.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        slidedeskripsi.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));

        container.addView(view);

        slideimageview.setImageResource(slide_gambar[position]);
        slidetextview.setText(slide_heading[position]);
        slidedeskripsi.setText(slide_deskripsi[position]);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}