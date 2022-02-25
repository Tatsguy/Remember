package com.myers.saveme;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;
    private final LayoutInflater mInflater;
    List<ListElement> originalList;

    public ListAdapter(List<ListElement> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.mData = itemList;
        originalList = new ArrayList<>();
        originalList.addAll(mData);
    }

    @Override
    public int getItemCount(){
        return mData.size();
    }

    @NotNull
    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        long id = originalList.get(position).getId();
        String title = originalList.get(position).getTitle();
        String content = originalList.get(position).getDescription();
        String date = originalList.get(position).getDate();
        String time = originalList.get(position).getTime();

        if(title.length()>20){
            title = title.substring(0,17).concat("...");
        }

        if(content.length()>25){
            content = content.substring(0,25).concat("...");
        }

        holder.nTitle.setText(title);
        holder.nDate.setText(date+" "+time);
        holder.nId.setText(String.valueOf(id));
        holder.nDescription.setText(content);
    }

    public void filtrate(String txtSearch){
        int longitude = txtSearch.length();
        if(longitude==0){
            mData.clear();
            mData.addAll(originalList);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ListElement> collection = mData.stream()
                        .filter(i -> i.getTitle().toLowerCase().contains(txtSearch.toLowerCase()))
                        .collect(Collectors.toList());
                mData.clear();
                mData.addAll(collection);
            }else{
                for (ListElement l: originalList)
                    if (l.getTitle().toLowerCase().contains(txtSearch.toLowerCase())) {
                        mData.add(l);
                    }
            }
        }
        notifyDataSetChanged();
    }

    public void setItems(List<ListElement> items){
        mData= items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView nTitle,nDescription,nDate,nId;

        ViewHolder(View itemView){
            super(itemView);

            nTitle = itemView.findViewById(R.id.titleTextView);
            nDescription = itemView.findViewById(R.id.descriptionTextView);
            nDate = itemView.findViewById(R.id.fechaTextView);
            nId = itemView.findViewById(R.id.txtId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),Activity_NewNote.class);
                    i.putExtra("ID",originalList.get(getAbsoluteAdapterPosition()).getId());
                    v.getContext().startActivity(i);
                }
            });
        }

        void bindData(final ListElement item){
            nTitle.setText(item.getTitle());
            nDescription.setText(item.getDescription());
            nDate.setText(item.getDate()+" "+item.getTime());
        }
    }
}
