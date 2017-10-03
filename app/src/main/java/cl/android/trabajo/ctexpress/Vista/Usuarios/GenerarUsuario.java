package cl.android.trabajo.ctexpress.Vista.Usuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
        cargarSpinnerUsuario();
    }

    /*public void cancelarUsuario(View view) {
        Intent intent = new Intent(this, MainAdmin.class);
        startActivity(intent);
    }*/

    public void guardarUsuario(View view) {

        try {
            EditText etRut = (EditText) findViewById(R.id.txtRutUsuario);

            String mensaje = "Debe ingresar todos los campos";
            if(!etRut.getText().toString().isEmpty()) {
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
                                    String rut = etRut.getText().toString();
                                    rut = rut.toUpperCase();
                                    if(sintaxisRut(rut)) {
                                        if(validarDigitoVerificador(rut)) {
                                            Log.i("validarDigito", "true");
                                            Usuario dto = new Usuario();
                                            MantenedorUsuario auxNegocio = new MantenedorUsuario(this);

                                            dto.setRut(rut);
                                            dto.setApellido(apellido.getText().toString());
                                            dto.setNombre(nombre.getText().toString());
                                            dto.setCorreo(correo.getText().toString());
                                            dto.setClave(clave.getText().toString());
                                            dto.setTipoUsuario(tipoUsuario.getSelectedItem().toString());
                                            Log.i("Seteo", "OK");
                                            boolean insertOk = auxNegocio.insert(dto);
                                            Log.i("Insert", "OK");
                                            if (insertOk) {
                                                mensaje = "Usuario Guardado";

                                                etRut.setText("");
                                                nombre.setText("");
                                                apellido.setText("");
                                                correo.setText("");
                                                clave.setText("");
                                                tipoUsuario.setSelection(0);
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
}
