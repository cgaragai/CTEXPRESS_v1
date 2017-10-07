package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Adapters.AdapterSoluciones;
import cl.android.trabajo.ctexpress.Adapters.AdapterTickets;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorFalla;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorSolucionPropuesta;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Falla;
import cl.android.trabajo.ctexpress.Modelo.SolucionPropuesta;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Main.MainDocente;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class Solucion extends AppCompatActivity {

    private Ticket ticket;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solucion);
        ticket = (Ticket) getIntent().getSerializableExtra("Ticket");

        MantenedorFalla mantenedorFalla = new MantenedorFalla(this);
        Falla falla = mantenedorFalla.getByCodigoFalla(ticket.getCodigoFalla());

        ListView lvSoluciones = (ListView) findViewById(R.id.lvSolucionesPropuestas);

        MantenedorSolucionPropuesta mantenedorSolucionPropuesta = new MantenedorSolucionPropuesta(this);
        ArrayList<SolucionPropuesta> soluciones = new ArrayList<>();
        soluciones.add(mantenedorSolucionPropuesta.getByCodigoSolucion(falla.getCodigoSolucion()));

        AdapterSoluciones adapterSoluciones = new AdapterSoluciones(this, soluciones);
        lvSoluciones.setAdapter(adapterSoluciones);
    }

    public void generarTicket(View view) {
        Log.i("generarTicket","OK");
        ticket.setEstado("Abierto");
        cerrar();
    }

    public void solucionado(View view) {
        Log.i("solucionado","OK");
        ticket.setEstado("AutoSolucionado");
        cerrar();
    }

    private void cerrar(){
        Log.i("cerrar","OK");
        if(updateTicket()) {
            Log.i("Update", "OK");
            Intent intent = new Intent(this, MainDocente.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    private boolean updateTicket(){
        MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
        return mantenedorTicket.update(ticket);
    }
}
