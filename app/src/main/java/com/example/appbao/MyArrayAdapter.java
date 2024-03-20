package com.example.appbao;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private int layout;
    private List<BaiBao> originalList; // Danh sách dữ liệu gốc
    private List<BaiBao> filteredList; // Danh sách dữ liệu đã được lọc
    private ItemFilter filter = new ItemFilter();

    public MyArrayAdapter(Context context, int layout, List<BaiBao> arrayList) {
        this.context = context;
        this.layout = layout;
        this.originalList = arrayList;
        this.filteredList = arrayList; // Ban đầu danh sách lọc sẽ giống với danh sách gốc
    }

    @Override
    public int getCount() {
        return filteredList.size(); // Trả về số lượng phần tử của danh sách đã lọc
    }

    @Override
    public BaiBao getItem(int position) {
        return filteredList.get(position); // Trả về phần tử ở vị trí position trong danh sách đã lọc
    }

    @Override
    public long getItemId(int position) {
        return position; // Trả về id của phần tử, trong trường hợp này có thể trả về position
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Lấy layoutInflater để inflate layout
        LayoutInflater inflater = LayoutInflater.from(context);
        convertView = inflater.inflate(layout, parent, false);

        // Lấy thông tin của phần tử ở vị trí position trong danh sách đã lọc
        BaiBao item = filteredList.get(position);

        // Ánh xạ view trong layout
        TextView des = convertView.findViewById(R.id.txt1);
        ImageView img = convertView.findViewById(R.id.img1);
        TextView type = convertView.findViewById(R.id.txtType);
        TextView time = convertView.findViewById(R.id.txtTime);

        // Hiển thị thông tin của phần tử
           des.setText(item.getMa());
        des.setText(item.getTitle());
        byte[] hinh = item.getHinh();
        Bitmap bitmap = BitmapFactory.decodeByteArray(hinh,0,hinh.length);
        img.setImageBitmap(bitmap);
         type.setText(item.getCategoryID());
         time.setText(item.getCreatedDate());


        return convertView; // Trả về view đã được thiết kế
    }

    @Override
    public Filter getFilter() {
        return filter; // Trả về đối tượng Filter để thực hiện việc lọc dữ liệu
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                ArrayList<BaiBao> filteredItems = new ArrayList<>();
                for (BaiBao item : originalList) {
                    if (item.getTitle().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredItems.add(item);
                    }
                }
                results.count = filteredItems.size();
                results.values = filteredItems;
            } else {
                results.count = originalList.size();
                results.values = originalList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredList = (List<BaiBao>) results.values;
            notifyDataSetChanged(); // Thông báo cho adapter biết là dữ liệu đã thay đổi
        }
    }
}
