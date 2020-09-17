package com.example.androidtest.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidtest.R;
import com.example.androidtest.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private LayoutInflater layoutInflater;
    public List<Contacts> cont;
    Contacts list;
    private ArrayList<Contacts> arraylist;
    boolean checked = false;
    View vv;

    public ContactsAdapter(LayoutInflater layoutInflater, List<Contacts> cont) {
        this.layoutInflater = layoutInflater;
        this.cont = cont;
        //this.arraylist = new ArrayList<Contacts>();
      //  this.arraylist.addAll(cont);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == TYPE_ITEM) {

            View v = layoutInflater.inflate(R.layout.contactlist_row, parent, false);
            ViewHolder viewHolder = new ViewHolder(v);
            return viewHolder;
        }else if(viewType == TYPE_HEADER){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contactlistheader, parent, false);
            return new HeaderViewHolder(itemView);
        }
        else
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        list = cont.get(position);

        if (holder instanceof HeaderViewHolder){
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;

            // You have to set your header items values with the help of model class and you can modify as per your needs

            headerViewHolder.headertext.setText(list.getHeader());

        }else if(holder instanceof ViewHolder ){
            ViewHolder viewHolder = (ViewHolder)holder;

            viewHolder.title.setText(list.getName());
            viewHolder.phone.setText(list.getPhone());
        }

    }

    /*@Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        list = cont.get(position);
        String name = (list.getName());

        holder.title.setText(name);
        holder.phone.setText(list.getPhone());

    }*/

    @Override
    public int getItemCount() {
        return cont.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView phone;
        public RelativeLayout contact_select_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.name);
            phone = (TextView) itemView.findViewById(R.id.no);
            contact_select_layout = (RelativeLayout) itemView.findViewById(R.id.contact_select_layout);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        TextView headertext;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);

            headertext = (TextView)itemView.findViewById(R.id.alphabet);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

}
