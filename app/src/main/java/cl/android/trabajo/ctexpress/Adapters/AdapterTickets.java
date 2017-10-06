package cl.android.trabajo.ctexpress.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Elaps-Merlina on 05-10-2017.
 */

public class AdapterTickets extends BaseAdapter {

    protected Activity activity;
    ArrayList<Ticket> tickets = new ArrayList<>();

    public AdapterTickets(Activity activity, ArrayList<Ticket> Tickes){
        this.activity = activity;
        this.tickets = tickets;
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Object getItem(int position) {
        return tickets.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_tickets, null);
        }

        Ticket ticket = new Ticket();

        TextView tvCodTicket = v.findViewById(R.id.tvCodTicket);
        tvCodTicket.setText(String.valueOf(tickets.get(position).getCodigoTicket()));

        TextView tvCodFalla = v.findViewById(R.id.tvCodFalla);
        tvCodFalla.setText(String.valueOf(tickets.get(position).getCodigoFalla()));

        TextView tvDetalle = v.findViewById(R.id.tvDetalle);
        tvDetalle.setText(tickets.get(position).getDetalle());

        return v;
    }
}
