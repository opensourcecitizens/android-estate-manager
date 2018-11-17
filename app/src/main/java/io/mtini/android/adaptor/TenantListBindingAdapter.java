package io.mtini.android.adaptor;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.mtini.android.tenantmanager.R;
import io.mtini.model.TenantModel;

public class TenantListBindingAdapter extends BaseAdapter {
    Context context;
    List<TenantModel> rowItems;
    View.OnClickListener onClickListener;
    public TenantListBindingAdapter(Context context, List<TenantModel> items, View.OnClickListener onClickListener) {
        this.context = context;
        this.rowItems = items;
        this.onClickListener = onClickListener;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView roomNumber;
        TextView status;
        TextView dueDate;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.tenant_layout, null);
        holder = new ViewHolder();
        holder.roomNumber = (TextView) convertView.findViewById(R.id.roomNumber);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        //holder.name.setOnClickListener(onClickListener);
        holder.status = (TextView) convertView.findViewById(R.id.status);
        holder.dueDate = (TextView) convertView.findViewById(R.id.dueDate);
        //holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
        convertView.setOnClickListener(onClickListener);


        TenantModel rowItem = (TenantModel) getItem(position);

        holder.roomNumber.setText(rowItem.getBuildingNumber());
        holder.name.setText(rowItem.getName());
        holder.status.setText(rowItem.getStatus().name());
        holder.dueDate.setText(rowItem.getRentDueDate().toString());
        //holder.imageView.setImageResource(rowItem.getImageId());
        convertView.setFocusable(true);
        convertView.setTag(rowItem);

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}