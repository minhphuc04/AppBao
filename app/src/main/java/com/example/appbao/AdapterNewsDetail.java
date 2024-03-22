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

public class AdapterNewsDetail extends BaseAdapter {
    Context context;
    int Layout;
    ArrayList<News> arrayListNews;
    public AdapterNewsDetail(Context context, int layout, ArrayList<News> arrayListNews) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AdapterNewsDetail.ViewHolder holder;
        if(convertView==null){

            holder = new AdapterNewsDetail.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView= inflater.inflate(Layout,null);
            //Tham chieu id trong layout_item
            holder.txtTitle=convertView.findViewById(R.id.txtTitlee);
            holder.Content=convertView.findViewById(R.id.Content11);
            holder.ImageDetail=convertView.findViewById(R.id.ImageDetail);
            convertView.setTag(holder);

        }
        else {
            holder= (AdapterNewsDetail.ViewHolder) convertView.getTag();
        }
        //get data from SanPham -> Holder
        News sp =arrayListNews.get(position);
        holder.txtTitle.setText(sp.getTitle());
        holder.txtContent.setText(sp.getContent());
        byte[] hinh = sp.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.ImageDetail.setImageBitmap(bitmap);
        return convertView;
    }
    public class ViewHolder{
        TextView Content, txtTitle,txtContent;
        ImageView ImageDetail;

    }
}
