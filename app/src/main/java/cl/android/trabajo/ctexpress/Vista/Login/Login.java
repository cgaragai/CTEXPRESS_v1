package cl.android.trabajo.ctexpress.Vista.Login;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorEquipo;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorFalla;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorSala;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorSolucionPropuesta;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTipoEquipo;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorUsuario;
import cl.android.trabajo.ctexpress.Modelo.Usuario;
import cl.android.trabajo.ctexpress.Vista.Main.MainAdmin;
import cl.android.trabajo.ctexpress.Vista.Main.MainAdministrativo;
import cl.android.trabajo.ctexpress.Vista.Main.MainDocente;
import cl.android.trabajo.ctexpress.Vista.Main.MainTecnico;
import cl.android.trabajo.ctexpress.R;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        TextView tvRecuperarClave = (TextView) findViewById(R.id.tvRecuperarClave);
        tvRecuperarClave.setPaintFlags(tvRecuperarClave.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        MantenedorUsuario mantenedor = new MantenedorUsuario(this);
        mantenedor.insertUsuariosIniciales();

        MantenedorSala mantenedorSala = new MantenedorSala(this);
        mantenedorSala.insertSalasIniciales();

        MantenedorTipoEquipo mantenedorTipoEquipo = new MantenedorTipoEquipo(this);
        mantenedorTipoEquipo.insertTipoEquiposIniciales();

        MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
        mantenedorEquipo.insertEquiposIniciales();

        MantenedorSolucionPropuesta mantenedorSolucionPropuesta = new MantenedorSolucionPropuesta(this);
        mantenedorSolucionPropuesta.insertSolucionesIniciales();

        MantenedorFalla mantenedorFalla = new MantenedorFalla(this);
        mantenedorFalla.insertFallasIniciales();

        /*MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
        ArrayList<Ticket> tickets = mantenedorTicket.getAll();
        if(!tickets.isEmpty()){
            for(Ticket ticket:tickets){
                Log.i("Ticket", String.valueOf(ticket.getCodigoTicket()) + " " + ticket.getEstado());
            }
        }*/
    }

    public void ingresar(View view) {

        EditText etRut = (EditText) findViewById(R.id.txtRut);
        String rut = etRut.getText().toString();

        boolean ok = validarCampos(rut);
        if(ok) {
            Usuario usuario = getUsuarioByRut(rut);

            Class clase = null;
            Intent intent;

            switch (usuario.getTipoUsuario())
            {
                case "Admin":
                    clase = MainAdmin.class;
                    break;
                case "Docente":
                    clase = MainDocente.class;
                    break;
                case "Tecnico":
                    clase = MainTecnico.class;
                    break;
                case "Administrativo":
                    clase = MainAdministrativo.class;
                    break;
            }
            intent = new Intent(this,clase);
            intent.putExtra("rutUsuario", rut);
            startActivity(intent);
        }

    }

    public void recuperarClave(View view) {

        Intent intent = new Intent(this,RecuperarClave.class);
        startActivity(intent);

    }

    private boolean validarCampos(String rut){

        boolean auxRut = validarRut(rut);

        if (auxRut) {
            boolean clave = validarClave(rut);
            if (clave)
                return true;
        }

        return false;

    }

    private boolean validarRut(String rut){

        TextView tvMensajeRut = (TextView)findViewById(R.id.tvMensajeRut);
        tvMensajeRut.setText("");

        Usuario usuario = getUsuarioByRut(rut);


        if(usuario.getNombre() != null)
            return true;
        else
            if(rut.isEmpty())
                tvMensajeRut.setText("Debe ingresar Rut");
            else
                if(!validarRutExiste(rut))
                    tvMensajeRut.setText("Rut ingresado no válido");

        return false;

    }

    private boolean validarClave(String rut){

        EditText etClave = (EditText) findViewById(R.id.txtClave);
        String clave = etClave.getText().toString();
        TextView tvMensajeClave = (TextView)findViewById(R.id.tvMensajeClave);
        tvMensajeClave.setText("");

        Usuario usuario = getUsuarioByRut(rut);

        if(clave.equals(usuario.getClave()))
            return true;
        else
            if(clave.isEmpty())
                tvMensajeClave.setText("Debe ingresar Clave");
            else
                tvMensajeClave.setText("Clave ingresada no válida");

        return false;
    }

    private boolean validarRutExiste(String rut){//Devuelve Usuario vacio si no lo encuentra
        MantenedorUsuario mantenedor = new MantenedorUsuario(this);
        boolean existe = mantenedor.rutExiste(rut);
        return existe;
    }

    private Usuario getUsuarioByRut(String rut){
        Usuario usuario = new Usuario();
        MantenedorUsuario mantenedor = new MantenedorUsuario(this);
        return mantenedor.getByRut(rut);

    }

}
