package com.br.bnsantos.login.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import com.br.bnsantos.login.example.LoginActivity;
import com.br.bnsantos.login.example.R;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: bruno
 * Date: 1/7/14
 * Time: 11:30 AM
 * To change this template use File | Settings | File Templates.
 */
public class ServerArrayAdapter extends BaseAdapter implements Filterable {
    private ArrayList<String> servers;
    private LoginActivity loginActivity;
    private final LayoutInflater inflater;

    public ServerArrayAdapter(ArrayList<String> servers, LoginActivity loginActivity) {
        inflater = (LayoutInflater) loginActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.servers = servers;
        this.loginActivity = loginActivity;
    }

    public Object getItem(int position) {
        return servers.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public int getCount() {
        return servers.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.component_server, null);

        TextView address = (TextView) view.findViewById(R.id.componentServerName);

        final String current = servers.get(position);
        address.setText(current);

        view.findViewById(R.id.componentServerLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginActivity.selectedServer(current);
            }
        });

        view.findViewById(R.id.componentServerRemoveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(position);
            }
        });

        return view;
    }

    private void remove(int position){
        servers.remove(position);
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

                results.values = servers;
                results.count = servers.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                servers = (ArrayList<String>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
