package com.montoya181; // usa tu propio paquete

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter
        extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>
        implements Filterable {

    private List<String> originalList;
    private List<String> filteredList;

    public ContactsAdapter(List<String> contacts) {
        this.originalList = contacts;
        this.filteredList = new ArrayList<>(contacts);
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = filteredList.get(position);
        String[] parts = item.split(" • ", 2);
        holder.name.setText(parts[0]);
        holder.phone.setText(parts.length > 1 ? parts[1] : "");    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = constraint.toString().toLowerCase().trim();
                List<String> result = new ArrayList<>();
                if (query.isEmpty()) {
                    result.addAll(originalList);
                } else {
                    for (String name : originalList) {
                        if (name.toLowerCase().contains(query)) {
                            result.add(name);
                        }
                    }
                }
                FilterResults fr = new FilterResults();
                fr.values = result;
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                //noinspection unchecked
                filteredList.addAll((List<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,phone;
        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tvContactName);
            phone = itemView.findViewById(R.id.tvContactPhone);
        }
    }

    public void addContact(String displayName){
        originalList.add(displayName);
        filteredList.add(displayName);
        notifyItemInserted(filteredList.size() -1);
    }
}
