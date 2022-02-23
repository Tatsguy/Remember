package com.myers.saveme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {
    private List<ListElement> mData;

    List<ListElement> originalList;
    private LayoutInflater mInflater;
    private Context context;

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

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.list_element,parent,false);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position){
        String title = originalList.get(position).getTitle();
        String content = originalList.get(position).getDescription();
        String date = originalList.get(position).getDate();
        String time = originalList.get(position).getTime();
        holder.title.setText(title);
        holder.description.setText(content);
        holder.date.setText(date+" "+time);
    }

    public void filtrado(String txtBuscar){
        int longitud = txtBuscar.length();
        if(longitud==0){
            mData.clear();
            mData.addAll(originalList);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<ListElement> coleccion = mData.stream()
                        .filter(i -> i.getTitle().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                mData.clear();
                mData.addAll(coleccion);
            }else{
                for (ListElement l: originalList) {
                    if (l.getTitle().toLowerCase().contains(txtBuscar.toLowerCase())){
                        mData.add(l);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    public void setItems(List<ListElement> items){
        mData= items;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,description,date,id;

        ViewHolder(View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            description = itemView.findViewById(R.id.descriptionTextView);
            date = itemView.findViewById(R.id.fechaTextView);
            id = itemView.findViewById(R.id.txtId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(),"Item Clicked",Toast.LENGTH_SHORT).show();
                }
            });
        }

        void bindData(final ListElement item){
            id.setText(String.valueOf(item.getId()));
            title.setText(item.getTitle());
            description.setText(item.getDescription());
            date.setText(item.getDate()+" "+item.getTime());
        }
    }
}
