package com.montoya181; // usa tu propio paquete

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.montoya181.domain.Contact;

import java.util.ArrayList;
import java.util.List;

public class ContactsAdapter
        extends RecyclerView.Adapter<ContactsAdapter.ViewHolder>
        implements Filterable {

    private List<Contact> originalList;
    private List<Contact> filteredList;

    public interface OnContactClickListener{
        void onEditContact(Contact c);
    }
    public ContactsAdapter(List<Contact> contacts) {
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
    public void onBindViewHolder(ViewHolder h, int pos) {
        Contact c = filteredList.get(pos);
        h.name.setText(String.format("%s %s", c.getFirstName(), c.getLastName()));
        h.phone.setText(c.getPhone());

        h.itemView.setOnClickListener(v -> {
            Intent i = new Intent(v.getContext(), ContactDetailActivity.class);
            i.putExtra("contact_id",c.getId());
            v.getContext().startActivity(i);
        });

    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String query = (constraint == null ? "" : constraint.toString().toLowerCase().trim());
                List<Contact> result = new ArrayList<>();
                if (query.isEmpty()) {
                    result.addAll(originalList);
                } else {
                    for (Contact c : originalList) {
                        String fullName = (c.getFirstName() + " " + c.getLastName()).toLowerCase();
                        String phone = c.getPhone().toLowerCase();

                        if (fullName.contains(query) || phone.contains(query)) {
                            result.add(c);
                        }
                    }
                }
                FilterResults fr = new FilterResults();
                fr.values = result;
                return fr;
            }

            @Override
            @SuppressWarnings("unchecked")
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredList.clear();
                filteredList.addAll((List<Contact>) results.values);
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

    public void addContact(Contact c){
        originalList.add(c);
        filteredList.add(c);
        notifyItemInserted(filteredList.size() -1);
    }

    public void updateList(List<Contact> newContacts) {
        originalList.clear();
        originalList.addAll(newContacts);

        filteredList.clear();
        filteredList.addAll(newContacts);

        notifyDataSetChanged();
    }
}
