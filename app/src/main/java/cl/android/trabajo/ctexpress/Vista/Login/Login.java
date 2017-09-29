package cl.android.trabajo.ctexpress.Vista.Login;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cl.android.trabajo.ctexpress.Vista.Main.MainTecnico;
import cl.android.trabajo.ctexpress.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView tvRecuperarClave = (TextView) findViewById(R.id.tvRecuperarClave);
        tvRecuperarClave.setPaintFlags(tvRecuperarClave.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void ingresar(View view) {

        boolean ok = validarCampos();
        if(ok) {
            EditText etRut = (EditText) findViewById(R.id.txtRut);
            String rut = etRut.getText().toString();

            Intent intent = new Intent(this, MainTecnico.class);
            intent.putExtra("rutUsuario", rut);
            startActivity(intent);
        }

    }

    public void recuperarClave(View view) {

        Intent intent = new Intent(this,RecuperarClave.class);
        startActivity(intent);

    }

    private boolean validarCampos(){

        boolean rut = validarRut();
        boolean clave = validarClave();

        if (rut && clave)
            return true;
        else
            return false;

    }

    private boolean validarRut(){

        EditText etRut = (EditText) findViewById(R.id.txtRut);
        String rut = etRut.getText().toString();
        TextView tvMensajeRut = (TextView)findViewById(R.id.tvMensajeRut);
        tvMensajeRut.setText("");

        if(rut.equals("1")) {
            return true;
        }else {
            if(rut.isEmpty())
                tvMensajeRut.setText("Debe ingresar Rut");
            else
                tvMensajeRut.setText("Rut ingresado no válido");
        }
        return false;

    }

    private boolean validarClave(){

        EditText etClave = (EditText) findViewById(R.id.txtClave);
        String clave = etClave.getText().toString();
        TextView tvMensajeClave = (TextView)findViewById(R.id.tvMensajeClave);
        tvMensajeClave.setText("");

        if(clave.equals("1")){
            return true;
        }else {
            if(clave.isEmpty())
                tvMensajeClave.setText("Debe ingresar Clave");
            else
                tvMensajeClave.setText("Clave ingresada no válida");
        }

        return false;

    }
}
