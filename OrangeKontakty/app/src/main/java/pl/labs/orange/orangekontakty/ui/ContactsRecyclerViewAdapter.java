package pl.labs.orange.orangekontakty.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pl.labs.orange.orangekontakty.R;
import pl.labs.orange.orangekontakty.data.Contact;

public class ContactsRecyclerViewAdapter extends RecyclerView.Adapter<ContactsRecyclerViewAdapter.ContactsViewHolder> {

    private final List<Contact> contactsList;
    private final OnItemClick onItemClick;

    public ContactsRecyclerViewAdapter(List<Contact> contactsList, OnItemClick onItemClick){
        this.contactsList = contactsList;
        this.onItemClick = onItemClick;
    }

    public void updateContacts(List<Contact> contactsList){
        this.contactsList.clear();
        this.contactsList.addAll(contactsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return  new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        Contact contact = contactsList.get(position);
        holder.tvName.setText(contact.getName() == null ? "" : contact.getName());
        holder.tvSurName.setText(contact.getLastName() == null ? "" : contact.getLastName());
        holder.tvPhoneNumber.setText(contact.getPhone() == null ? "" : contact.getPhone());
        holder.cvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick.OnItemClick(contact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ContactsViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        TextView tvSurName;
        TextView tvPhoneNumber;
        CardView cvContact;


        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            cvContact = itemView.findViewById(R.id.cvContact);
            tvName = itemView.findViewById(R.id.tvName);
            tvSurName = itemView.findViewById(R.id.tvSurName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
        }
    }

    interface OnItemClick{
        void OnItemClick(Contact contact);
    }
}
