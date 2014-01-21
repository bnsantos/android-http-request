package com.br.bnsantos.login.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.br.bnsantos.login.example.R;
import com.br.bnsantos.login.example.RequestActivity;
import com.br.bnsantos.login.example.entities.JsonField;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class FieldArrayAdapter extends BaseAdapter implements Filterable {
    private ArrayList<JsonField> fields;
    private RequestActivity requestActivity;
    private final LayoutInflater inflater;

    public FieldArrayAdapter(ArrayList<JsonField> fields, RequestActivity requestActivity) {
        inflater = (LayoutInflater) requestActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.fields = fields;
        this.requestActivity = requestActivity;
    }

    public Object getItem(int position) {
        return fields.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return fields.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.component_field, null);

        TextView fieldName = (TextView) view.findViewById(R.id.componentFieldName);

        final JsonField current = fields.get(position);
        fieldName.setText(current.getName());

        TextView fieldValue = (TextView) view.findViewById(R.id.componentFieldValue);
        if(current.getValue()!=null){
            fieldValue.setText(current.getValue());
        }

        view.findViewById(R.id.componentFieldRemoveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return view;
    }

    private void remove(int position){
        fields.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();

                results.values = fields;
                results.count = fields.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                fields = (ArrayList<JsonField>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
