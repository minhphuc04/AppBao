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

public class NewsAdapter extends BaseAdapter {
    Context context;
    int Layout;
    ArrayList<News> arrayListNews;
    public NewsAdapter(Context context, int layout, ArrayList<News> arrayListNews) {
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
        TextView txtID, txtTitle,txtContent,txtPDate,txtCateID,txtAuthorID;
        ImageView ivHinhItem;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){

            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView= inflater.inflate(Layout,null);
            //Tham chieu id trong layout_item
            holder.txtID=convertView.findViewById(R.id.txtID);
            holder.txtTitle=convertView.findViewById(R.id.txtTitle);
            holder.txtContent=convertView.findViewById(R.id.txtContent);
            holder.txtPDate=convertView.findViewById(R.id.txtPDate);
            holder.txtCateID=convertView.findViewById(R.id.txtCateID);
            holder.txtAuthorID=convertView.findViewById(R.id.txtAuthorID);
            holder.ivHinhItem=convertView.findViewById(R.id.ivHinhitem);
            convertView.setTag(holder);

        }
        else {
            holder= (ViewHolder) convertView.getTag();
        }
        //get data from SanPham -> Holder
        News sp =arrayListNews.get(position);
        holder.txtID.setText(sp.getMa());
        holder.txtTitle.setText(sp.getTitle());
        holder.txtContent.setText(sp.getContent());
        holder.txtPDate.setText(sp.getCreatedDate());
        holder.txtCateID.setText(sp.getCategoryID());
        holder.txtAuthorID.setText(sp.getAuthorID());

        byte[] hinh = sp.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        holder.ivHinhItem.setImageBitmap(bitmap);
        return convertView;
    }
}
