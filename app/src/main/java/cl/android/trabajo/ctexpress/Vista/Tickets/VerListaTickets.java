package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Adapters.AdapterTickets;
import cl.android.trabajo.ctexpress.Mantenedor.SQLite.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Main.MainAdmin;
import cl.android.trabajo.ctexpress.Vista.Main.MainTecnico;

/**
 * Created by Elaps-Merlina on 05-10-2017.
 */

public class VerListaTickets extends AppCompatActivity {

    private String rut, estado, main;
    private ListView lvTickets;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_lista_tickets);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        lvTickets = (ListView) findViewById(R.id.lvListaTickets);


        estado = getIntent().getStringExtra("estado");
        rut = getIntent().getStringExtra("rutUsuario");
        main = getIntent().getStringExtra("Main");

        cargarList();
        lvTickets.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(estado.equals("Abierto")) {
                    Ticket ticket = (Ticket) parent.getItemAtPosition(position);
                    ticket.setRutTecnico(rut);
                    ticket.setEstado("Asignado");

                    MantenedorTicket mantenedorTicket1 = new MantenedorTicket(parent.getContext());
                    boolean ok = mantenedorTicket1.update(ticket);
                    if (ok) {
                        cargarList();
                        mensaje("Ticket codigo " + ticket.getCodigoTicket() + " asignado a técnico rut " + ticket.getRutTecnico());
                    }else
                        mensaje("Error al asignar ticket");
                }
                if(estado.equals("")){
                    Ticket ticket = (Ticket) parent.getItemAtPosition(position);
                    editarTicket(ticket);
                }

            }
        });
    }

    private void cargarList(){
        MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
        ArrayList<Ticket> tickets = new ArrayList<>();
        switch(estado){
            case "Abierto":
                tickets = mantenedorTicket.getAllByEstado(estado);
                break;
            case "Asignado":
                tickets = mantenedorTicket.getAllByEstadoAndRutTecnico(estado, rut);
                break;
            default:
                tickets = mantenedorTicket.getAll();
                break;
        }

        AdapterTickets adapterTickets = new AdapterTickets(this, tickets);
        lvTickets.setAdapter(adapterTickets);
    }

    private void editarTicket(Ticket ticket){
        Intent intent = new Intent(this, GenerarTicket.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("Ticket", ticket);
        intent.putExtra("rutUsuario", "");
        intent.putExtra("anterior", "VerListaTickets");
        startActivity(intent);
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(main.equals("MainAdmin")) {
            Intent intent = new Intent(this, MainAdmin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else if (main.equals("MainTecnico")) {
            Intent intent = new Intent(this, MainTecnico.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("rutUsuario", rut);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ok = false;
        if(item.getItemId() == android.R.id.home){
            ok = true;
            onBackPressed();
        }
        return ok;
    }
}
