package cl.android.trabajo.ctexpress.Vista.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Usuarios.GenerarUsuario;

/**
 * Created by Elaps-Merlina on 29-09-2017.
 */

public class MainAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_admin);
    }

    public void registrarUsuario(View view) {

        Intent intent = new Intent(this, GenerarUsuario.class);
        startActivity(intent);

    }

    public void gestionarUsuarios(View view) {



    }

    public void gestionarTickets(View view) {



    }
}
