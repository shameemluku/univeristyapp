package com.tipntale.calicutuniversity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MyAdapterContact extends RecyclerView.Adapter<MyAdapterContact.ViewHolder> {
    private List<ListItemContact> listItems;
    private Context context;

    public MyAdapterContact(List<ListItemContact> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contactlist_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final ListItemContact listItem = listItems.get(position);


            holder.textViewName.setText(listItem.getName());
            holder.textViewNumber.setText(listItem.getNumber());




        holder.contactitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent dailIntent = new Intent(Intent.ACTION_DIAL);
                dailIntent.setData(Uri.parse("tel:"+holder.textViewNumber.getText().toString()));
                context.startActivity(dailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewName, textViewNumber;
        public ImageView image;
        LinearLayout contactitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewNumber = (TextView) itemView.findViewById(R.id.textViewMobile);
            contactitem = (LinearLayout)itemView.findViewById(R.id.contact_item);

        }
    }
}
