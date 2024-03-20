package com.example.appbao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterTest extends BaseAdapter {
    Context context;
    int Layout;
    ArrayList<BaiBao> arrayListNews;
    public AdapterTest(Context context, int layout, ArrayList<BaiBao> arrayListNews) {
        this.context = context;
        Layout = layout;
        this.arrayListNews = arrayListNews;
    }
    @Override
    public int getCount() {
        return arrayListNews.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    public class ViewHolder{
        TextView txtType, txt1,txtTime;
        ImageView img1;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterTest.ViewHolder holder;
        if(convertView==null){

            holder = new AdapterTest.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView= inflater.inflate(Layout,null);
            //Tham chieu id trong layout_item
            holder.txtType=convertView.findViewById(R.id.txtType);
            holder.txt1=convertView.findViewById(R.id.txtTitle);
            holder.txtTime=convertView.findViewById(R.id.txtTime);
            holder.img1=convertView.findViewById(R.id.ivHinhitem);
            convertView.setTag(holder);

        }
        else {
            holder= (AdapterTest.ViewHolder) convertView.getTag();
        }
        //get data from SanPham -> Holder
        BaiBao sp =arrayListNews.get(position);
        holder.txtType.setText(sp.getMa());
        holder.txt1.setText(sp.getTitle());
        byte[] hinh = sp.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.img1.setImageBitmap(bitmap);
        return convertView;
    }
}
