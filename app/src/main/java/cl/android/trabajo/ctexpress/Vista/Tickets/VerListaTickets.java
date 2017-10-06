package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Adapters.AdapterTickets;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Elaps-Merlina on 05-10-2017.
 */

public class VerListaTickets extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_lista_tickets);

        ListView lvTickets = (ListView) findViewById(R.id.lvListaTickets);
        MantenedorTicket mantenedorTicket = new MantenedorTicket(this);

        String estado = getIntent().getStringExtra("estado");
        String rut = getIntent().getStringExtra("rutUsuario");

        ArrayList<Ticket> tickets = new ArrayList<>();
        switch(estado){
            case "abierto":
                tickets = mantenedorTicket.getAllByEstado(estado);
                break;
            case "asignado":
                tickets = mantenedorTicket.getAllByEstadoAndRutTecnico(estado, rut);
                break;
        }

        AdapterTickets adapterTickets = new AdapterTickets(this, tickets);
        lvTickets.setAdapter(adapterTickets);
        lvTickets.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Ticket ticket = (Ticket) parent.getItemAtPosition(position);

            }
        });
    }

}
