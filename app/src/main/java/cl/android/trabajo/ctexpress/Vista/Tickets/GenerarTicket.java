package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cl.android.trabajo.ctexpress.R;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class GenerarTicket extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_ticket);
    }

    public void siguienteTicket(View view) {
        Intent intent = new Intent(this,Solucion.class);
        startActivity(intent);
    }
}
