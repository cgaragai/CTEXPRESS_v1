package cl.android.trabajo.ctexpress.Vista.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Reportes.Estadisticas;
import cl.android.trabajo.ctexpress.Vista.Tickets.BuscarTicket;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class MainAdministrativo extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_administrativo);
    }

    public void estadisticasAdministrativo(View view) {
        Intent intent = new Intent(this, Estadisticas.class);
        startActivity(intent);
    }

    public void buscarAdministrativo(View view) {
        Intent intent = new Intent(this, BuscarTicket.class);
        startActivity(intent);
    }
}

