package cl.android.trabajo.ctexpress.Vista.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Tickets.GenerarTicket;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class MainDocente extends AppCompatActivity{
    private String rut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_docente);
        rut = getIntent().getStringExtra("rutUsuario");
    }

    public void crearTicket(View view) {
        Intent intent = new Intent(this, GenerarTicket.class);
        intent.putExtra("rutUsuario",rut);
        startActivity(intent);
    }

}

