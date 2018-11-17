package io.mtini.android.adaptor;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import io.mtini.model.EstateModel;
import io.mtini.android.tenantmanager.R;

public class EstateListBindingAdapter extends BaseAdapter {
    Context context;
    List<EstateModel> rowItems;
    View.OnClickListener onClickListener;
    public EstateListBindingAdapter(Context context, List<EstateModel> items, View.OnClickListener onClickListener) {
        this.context = context;
        this.rowItems = items;
        this.onClickListener = onClickListener;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView name;
        TextView description;
        TextView estateId;
        TextView type;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        convertView = mInflater.inflate(R.layout.estate_layout, null);
        holder = new ViewHolder();
        holder.description = (TextView) convertView.findViewById(R.id.description);
        holder.name = (TextView) convertView.findViewById(R.id.name);
        //holder.name.setOnClickListener(onClickListener);
        holder.estateId = (TextView) convertView.findViewById(R.id.estateId);
        holder.type = (TextView) convertView.findViewById(R.id.estateType);
        //holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
        convertView.setOnClickListener(onClickListener);


        EstateModel rowItem = (EstateModel) getItem(position);
        holder.description.setText(rowItem.getDescription());
        holder.name.setText(rowItem.getName());
        holder.type.setText(rowItem.getType().name());
        holder.estateId.setText(rowItem.getId()+"");
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