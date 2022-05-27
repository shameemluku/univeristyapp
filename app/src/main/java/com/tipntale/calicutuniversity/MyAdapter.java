package com.tipntale.calicutuniversity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.culist_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final ListItem listItem = listItems.get(position);


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            holder.textViewTitle.setText(Html.fromHtml(listItem.getTitle(), Html.FROM_HTML_MODE_LEGACY));
            holder.textViewDate.setText(Html.fromHtml(listItem.getDate(), Html.FROM_HTML_MODE_LEGACY));
            holder.image.setBackgroundResource(listItem.getImage());
        } else {
            holder.textViewTitle.setText(Html.fromHtml(listItem.getTitle()));
            holder.textViewDate.setText(Html.fromHtml(listItem.getDate()));
            holder.image.setBackgroundResource(listItem.getImage());
        }



        holder.notifItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listItem.getTitle().toString().equals("Notifications are shown here once they get loaded"))
                {}
                else if(listItem.getTitle().toString().equals("No new notifications available"))
                {}
                else if(listItem.getTitle().toString().equals("Connection timeout!"))
                {}
                else if(listItem.getTitle().toString().equals("Please wait, Loading data..... "))
                {}
                else {
                    String url = listItem.getLink();
                    String heading = Html.fromHtml(listItem.getTitle()).toString();
                    Intent intent = new Intent(context, pdfbrowserActivity.class);
                    intent.putExtra("url", url);
                    intent.putExtra("heading", heading);
                    context.startActivity(intent);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle, textViewDate;
        public ImageView image;
        LinearLayout notifItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewNotific);
            textViewDate = (TextView) itemView.findViewById(R.id.txtDateNotif);
            notifItem = (LinearLayout) itemView.findViewById(R.id.notification_item);
            image = (ImageView)itemView.findViewById(R.id.itemimage);

        }
    }
}
