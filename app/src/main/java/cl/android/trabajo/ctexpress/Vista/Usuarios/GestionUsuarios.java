package cl.android.trabajo.ctexpress.Vista.Usuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import cl.android.trabajo.ctexpress.Adapters.AdapterUsuarios;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorUsuario;
import cl.android.trabajo.ctexpress.Modelo.Usuario;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Elaps-Merlina on 04-10-2017.
 */

public class GestionUsuarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_usuarios);
        ListView lvUsuarios = (ListView) findViewById(R.id.lv_lista_usuarios);
        MantenedorUsuario mantenedor = new MantenedorUsuario(this);

        AdapterUsuarios adapterUsuarios = new AdapterUsuarios(this, mantenedor.getAll());
        lvUsuarios.setAdapter(adapterUsuarios);
        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Usuario usuario = (Usuario) parent.getItemAtPosition(position);
                String mensaje = "Usuario.rut = " + usuario.getRut();
                mensaje(mensaje);

            }
        });
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

}
