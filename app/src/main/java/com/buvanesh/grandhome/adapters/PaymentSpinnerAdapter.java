package com.buvanesh.grandhome.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.buvanesh.grandhome.R;
import com.buvanesh.grandhome.model.ContactModel;

import java.util.List;

/**
 * Created by buvaneshkumar_p on 5/31/2018.
 */

public class PaymentSpinnerAdapter extends BaseAdapter {
        LayoutInflater inflater;
        List<ContactModel>  contactModels;
        int mResource;

    public PaymentSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<ContactModel> objects) {
        contactModels = objects;
        inflater = LayoutInflater.from(context);
        mResource =resource;
    }

    @Override
    public int getCount() {
        return contactModels.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(mResource,parent,false);
        ContactViewHolder viewHolder = null;
        if(viewHolder== null){
            viewHolder = new ContactViewHolder();
        }
        viewHolder.mTxtName = view.findViewById(R.id.txt_contact_name);
        viewHolder.mTxtContact = view.findViewById(R.id.txt_contact_number);
        viewHolder.mTxtName.setText(contactModels.get(position).getName());
        viewHolder.mTxtContact.setText(contactModels.get(position).getNumber());
        return view;
    }

    static class ContactViewHolder{
        public TextView mTxtName;
        public TextView mTxtContact;
    }
}
