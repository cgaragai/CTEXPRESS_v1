package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Adapters.AdapterTickets;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Elaps-Merlina on 05-10-2017.
 */

public class VerListaTickets extends AppCompatActivity {

    private String rut;
    private String estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_lista_tickets);

        ListView lvTickets = (ListView) findViewById(R.id.lvListaTickets);
        MantenedorTicket mantenedorTicket = new MantenedorTicket(this);

        estado = getIntent().getStringExtra("estado");
        rut = getIntent().getStringExtra("rutUsuario");

        ArrayList<Ticket> tickets = new ArrayList<>();
        switch(estado){
            case "Abierto":
                tickets = mantenedorTicket.getAllByEstado(estado);
                break;
            case "Asignado":
                tickets = mantenedorTicket.getAllByEstadoAndRutTecnico(estado, rut);
                break;
        }

        Log.i("tickets size", String.valueOf(tickets.size()));

        AdapterTickets adapterTickets = new AdapterTickets(this, tickets);
        lvTickets.setAdapter(adapterTickets);
        lvTickets.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(estado.equals("Abierto")) {
                    Ticket ticket = (Ticket) parent.getItemAtPosition(position);
                    ticket.setRutTecnico(rut);
                    ticket.setEstado("Asignado");

                    MantenedorTicket mantenedorTicket1 = new MantenedorTicket(parent.getContext());
                    boolean ok = mantenedorTicket1.update(ticket);
                    if (ok)
                        mensaje("Ticket codigo " + ticket.getCodigoTicket() + " asignado a técnico rut " + ticket.getRutTecnico());
                    else
                        mensaje("Error al asignar ticket");
                }

            }
        });
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

}
