package cl.android.trabajo.ctexpress.Vista.Usuarios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorUsuario;
import cl.android.trabajo.ctexpress.Modelo.Usuario;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class GenerarUsuario extends AppCompatActivity {

    private Usuario dtoUsuario;
    private boolean existe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_usuario);
        cargarSpinnerUsuario();
        existe = getIntent().getBooleanExtra("existe",false);
        if(existe) {
            dtoUsuario = (Usuario) getIntent().getSerializableExtra("Usuario");
            llenarDatos(dtoUsuario);
            Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
            btnGuardar.setText("Actualizar");
        }else{
            Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
            btnEliminar.setVisibility(View.INVISIBLE);
        }
    }

    /*public void cancelarUsuario(View view) {
        Intent intent = new Intent(this, MainAdmin.class);
        startActivity(intent);
    }*/

    public void guardarUsuario(View view) {

        try {
            String mensaje = "Debe ingresar todos los campos";
            EditText nombre = (EditText) findViewById(R.id.txtNombreUsuario);
            if (!nombre.getText().toString().isEmpty()) {
                EditText apellido = (EditText) findViewById(R.id.txtApellidoUsuario);
                if (!apellido.getText().toString().isEmpty()) {
                    EditText correo = (EditText) findViewById(R.id.txtEmailUsuario);
                    if (!correo.getText().toString().isEmpty()) {
                        EditText clave = (EditText) findViewById(R.id.txtClaveUsuario);
                        if (!clave.getText().toString().isEmpty()) {
                            Spinner tipoUsuario = (Spinner) findViewById(R.id.spTipoUsuario);
                            if (tipoUsuario.getSelectedItemPosition() > 0) {
                                EditText etRut = (EditText) findViewById(R.id.txtRutUsuario);
                                if(existe || !etRut.getText().toString().isEmpty()) {
                                    String rut = "";
                                    if(!existe) {
                                        rut = etRut.getText().toString();
                                        rut = rut.toUpperCase();
                                    }
                                    if(sintaxisRut(rut)) {
                                        if(validarDigitoVerificador(rut)) {
                                            MantenedorUsuario auxNegocio = new MantenedorUsuario(this);

                                            if(!existe){
                                                dtoUsuario = new Usuario();
                                                dtoUsuario.setRut(rut);
                                            }
                                            dtoUsuario.setApellido(apellido.getText().toString());
                                            dtoUsuario.setNombre(nombre.getText().toString());
                                            dtoUsuario.setCorreo(correo.getText().toString());
                                            dtoUsuario.setClave(clave.getText().toString());
                                            dtoUsuario.setTipoUsuario(tipoUsuario.getSelectedItem().toString());

                                            if(existe){
                                                boolean updateOk = auxNegocio.update(dtoUsuario);
                                                if(updateOk) mensaje = "Usuario Actualizado";
                                            }else {
                                                boolean insertOk = auxNegocio.insert(dtoUsuario);
                                                if (insertOk) {
                                                    mensaje = "Usuario Guardado";

                                                    etRut.setText("");
                                                    nombre.setText("");
                                                    apellido.setText("");
                                                    correo.setText("");
                                                    clave.setText("");
                                                    tipoUsuario.setSelection(0);
                                                }
                                            }
                                        }else{
                                            mensaje = "Rut ingresado no válido";
                                        }
                                    }else{
                                        mensaje = "Rut ingresado no válido";
                                    }
                                }
                            }
                        }
                    }
                }
            }

            this.mensaje(mensaje);

        }
        catch (Exception e)
        {

        }
    }

    private boolean sintaxisRut(String rut){
        if(existe)return true;
        String primeraParte = rut.substring(0, rut.length() - 2);
        try{
            int numero = Integer.parseInt(primeraParte);
            if(rut.substring(rut.length() - 2, rut.length() - 1).equals("-"))
                return true;
        }catch (Exception e){
            return false;
        }
        return false;
    }

    private boolean validarDigitoVerificador(String rut){
        if(existe)return true;
        if(rut.length() > 1) {
            String rutSinDigito = rut.substring(0, rut.length() - 2);
            String[] rutSplit = rutSinDigito.split("");
            int suma = 0;
            int multiplicador = 2;
            for(int i = rutSplit.length - 1; i > 0; i--){
                suma += (Integer.parseInt(rutSplit[i]) * multiplicador);
                if(multiplicador == 7)
                    multiplicador = 2;
                else
                    ++multiplicador;
            }
            int resto = suma % 11;
            int digitoNumero = 11 - resto;
            String digito = String.valueOf(digitoNumero);

            if(digitoNumero == 11) digito = "0";
            if(digitoNumero == 10) digito = "K";

            if(digito.equals(rut.substring(rut.length()-1)))
                return true;

        }
        return false;
    }

    private void cargarSpinnerUsuario(){
        Spinner spinner = (Spinner) findViewById(R.id.spTipoUsuario);
        String[] tipoUsuario = {"Seleccione","Admin","Administrativo","Docente","Tecnico"};
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoUsuario));
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    private void llenarDatos(Usuario usuario){

        EditText etRut = (EditText) findViewById(R.id.txtRutUsuario);
        etRut.setText(usuario.getRut());
        etRut.setEnabled(false);

        EditText nombre = (EditText) findViewById(R.id.txtNombreUsuario);
        nombre.setText(usuario.getNombre());

        EditText apellido = (EditText) findViewById(R.id.txtApellidoUsuario);
        apellido.setText(usuario.getApellido());

        EditText correo = (EditText) findViewById(R.id.txtEmailUsuario);
        correo.setText(usuario.getCorreo());

        EditText clave = (EditText) findViewById(R.id.txtClaveUsuario);
        clave.setText(usuario.getClave());

        int posicion = 0;
        Spinner tipoUsuario = (Spinner) findViewById(R.id.spTipoUsuario);
        switch (usuario.getTipoUsuario()){
            case "Admin":
                posicion = 1;
                break;
            case "Administrativo":
                posicion = 2;
                break;
            case "Docente":
                posicion = 3;
                break;
            case "Tecnico":
                posicion = 4;
                break;
        }
        tipoUsuario.setSelection(posicion);

    }

    public void eliminarUsuario(View view) {
        Log.i("Eliminar", "OK");
        MantenedorUsuario mantenedorUsuario = new MantenedorUsuario(this);
        boolean deleteOk = mantenedorUsuario.delete(dtoUsuario.getRut());

        if(deleteOk){
            EditText nombre = (EditText) findViewById(R.id.txtNombreUsuario);
            EditText apellido = (EditText) findViewById(R.id.txtApellidoUsuario);
            EditText correo = (EditText) findViewById(R.id.txtEmailUsuario);
            EditText clave = (EditText) findViewById(R.id.txtClaveUsuario);
            Spinner tipoUsuario = (Spinner) findViewById(R.id.spTipoUsuario);
            EditText etRut = (EditText) findViewById(R.id.txtRutUsuario);
            etRut.setText("");
            nombre.setText("");
            apellido.setText("");
            correo.setText("");
            clave.setText("");
            tipoUsuario.setSelection(0);

            etRut.setEnabled(true);
            dtoUsuario = null;
            existe = false;
            Button btnGuardar = (Button) findViewById(R.id.btnGuardar);
            btnGuardar.setText("Guardar");
            Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
            btnEliminar.setVisibility(View.INVISIBLE);
        }else{
            this.mensaje("Error al eliminar usuario");
        }

    }
}
