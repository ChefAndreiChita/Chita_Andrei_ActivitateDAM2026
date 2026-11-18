package com.example.lab8bun;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImagineAdapter extends BaseAdapter {
    private Context context;
    private int resource;
    private List<ImaginiDomeniu> lista;

    public ImagineAdapter(Context context, int resource, List<ImaginiDomeniu> lista) {
        this.context = context;
        this.resource = resource;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        ImaginiDomeniu item = lista.get(position);

        ImageView imagineIV = convertView.findViewById(R.id.imagineIV);
        TextView textTV = convertView.findViewById(R.id.textTV);

        textTV.setText(item.getTextAfisat());
        if (item.getImagine() != null) {
            imagineIV.setImageBitmap(item.getImagine());
        }

        return convertView;
    }
}