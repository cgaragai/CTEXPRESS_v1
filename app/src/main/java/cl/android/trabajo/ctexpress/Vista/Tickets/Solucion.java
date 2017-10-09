package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Main.MainDocente;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class Solucion extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.solucion);
    }

    public void generarTicket(View view) {
        Spinner piso = (Spinner) findViewById(R.id.spPisoTicket);
        Spinner sala = (Spinner) findViewById(R.id.spSalaTicket);
        Spinner tipoEquipo = (Spinner) findViewById(R.id.spTipoEquipoTicket);
        Spinner codigoEquipo = (Spinner) findViewById(R.id.spCodigoEquipoTicket);
        Spinner falla = (Spinner) findViewById(R.id.spFallaTicket);
        EditText detalle = (EditText) findViewById(R.id.txtDetalleTicket);

    }

    public void solucionado(View view) {
        Intent intent = new Intent(this, MainDocente.class);
        startActivity(intent);
    }
}
