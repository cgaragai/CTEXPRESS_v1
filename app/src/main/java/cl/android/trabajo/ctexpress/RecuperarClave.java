package cl.android.trabajo.ctexpress;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class RecuperarClave extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_clave);
    }

    public void enviarClave(View view) {

        TextView tvRespuestaEnvio = (TextView) findViewById(R.id.tvRespuestaEnvio);
        EditText etCorreo = (EditText)findViewById(R.id.txtCorreo);

        String correo = etCorreo.getText().toString();
        boolean ok = validarCorreo(correo);
        if(ok) {
            if(enviarCorreo(correo)) {
                tvRespuestaEnvio.setTextColor(Color.BLACK);
                tvRespuestaEnvio.setText("Clave enviada al correo ingresado");
            }else{
                tvRespuestaEnvio.setText("Error en envío de correo, favor inténtelo de nuevo");
            }
        }

    }

    private boolean validarCorreo(String correo){//Validar lo ingresado en el campo correo

        TextView tvRespuestaEnvio = (TextView) findViewById(R.id.tvRespuestaEnvio);
        tvRespuestaEnvio.setTextColor(Color.RED);

        if(comprobarCorreo(correo)) {
            return true;
        }else{
            tvRespuestaEnvio.setText("Correo ingresado no existe");
        }

        if(correo.isEmpty())
            tvRespuestaEnvio.setText("Debe ingresar un correo");

        return false;
    }

    private boolean comprobarCorreo(String correo){//comprobar si el correo ingresado existe

        if(correo.equals("cristian@leo.cl"))
            return true;

        return false;
    }

    private boolean enviarCorreo(String correo){//Se envía correo, true para envío efectuado, false error en conexión
        return true;
    }
}
