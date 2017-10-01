package cl.android.trabajo.ctexpress.Vista.Usuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorUsuario;
import cl.android.trabajo.ctexpress.Modelo.Usuario;
import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Main.MainAdmin;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class GenerarUsuario extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_usuario);
    }

    public void cancelarUsuario(View view) {
        Intent intent = new Intent(this, MainAdmin.class);
        startActivity(intent);
    }

    public void guardarUsuario(View view) {
        Usuario dto = new Usuario();
        MantenedorUsuario auxNegocio = new MantenedorUsuario(this);
        try {
            EditText rut = (EditText) findViewById(R.id.txtRutUsuario);
            EditText nombre = (EditText) findViewById(R.id.txtNombreUsuario);
            EditText apellido = (EditText) findViewById(R.id.txtApellidoUsuario);
            EditText correo = (EditText) findViewById(R.id.txtEmailUsuario);
            EditText clave = (EditText) findViewById(R.id.txtClaveUsuario);
            Spinner tipoUsuario = (Spinner) findViewById(R.id.spTipoUsuario);

            dto.setRut(rut.getText().toString());
            dto.setApellido(apellido.getText().toString());
            dto.setNombre(nombre.getText().toString());
            dto.setCorreo(correo.getText().toString());
            dto.setClave(clave.getText().toString());
            dto.setTipoUsuario(tipoUsuario.getSelectedItem().toString());

            auxNegocio.insert(dto);
            this.mensaje("Usuario Guardado");

            rut.setText("");
            nombre.setText("");
            apellido.setText("");
            correo.setText("");
            clave.setText("");
            tipoUsuario.setSelection(0);

        }
        catch (Exception e)
        {

        }
    }

    private boolean comprobar(){
        return true;
    }

    private void cargarSpinnerUsuario(){
        Spinner spinner = (Spinner) findViewById(R.id.spTipoUsuario);
        String[] tipoUsuario = {"Admin","Administrativo","Docente","Tecnico"};
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoUsuario));
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
