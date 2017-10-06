package cl.android.trabajo.ctexpress.Vista.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Tickets.VerListaTickets;

public class MainTecnico extends AppCompatActivity {

    private static String rut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_tecnico);
        rut = getIntent().getStringExtra("rutUsuario");
    }

    public void verTicketAbiertos(View view) {

        irVerListaTickets("abierto");

    }

    public void verTicketAsignado(View view) {

        irVerListaTickets("asignado");

    }

    private void irVerListaTickets(String estado){
        Intent intent = new Intent(this, VerListaTickets.class);
        intent.putExtra("estado", estado);
        intent.putExtra("rutUsuario", rut);
        startActivity(intent);
    }
}
