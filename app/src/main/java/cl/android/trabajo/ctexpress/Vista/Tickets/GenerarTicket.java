package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class GenerarTicket extends AppCompatActivity {
    private String rut;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_ticket);
        rut = getIntent().getStringExtra("rutUsuario");
    }

    public void siguienteTicket(View view) {
        Intent intent = new Intent(this,Solucion.class);
        intent.putExtra("s",rut);
        startActivity(intent);
    }
    public boolean tempTicket(){
        boolean ok = false;
        String mensaje = "";
        Spinner piso = (Spinner) findViewById(R.id.spPisoTicket);
        Spinner sala = (Spinner) findViewById(R.id.spSalaTicket);
        Spinner tipoEquipo = (Spinner) findViewById(R.id.spTipoEquipoTicket);
        Spinner codigoEquipo = (Spinner) findViewById(R.id.spCodigoEquipoTicket);
        Spinner falla = (Spinner) findViewById(R.id.spFallaTicket);
        EditText detalle = (EditText) findViewById(R.id.txtDetalleTicket);
        Ticket dtoTicket = new Ticket();

        if (falla.getSelectedItemPosition()!= 0){
            if (!detalle.getText().toString().isEmpty()){
                dtoTicket.setDetalle(detalle.getText().toString());
                dtoTicket.setCodigoEquipo(codigoEquipo.getSelectedItem().toString());
                dtoTicket.setRutUsuario(rut);
                ok = true;
            }
            else{
                mensaje = "El campo detalle no puede ir vacio";
            }
        }
        else{
            mensaje = "Debe seleccionar una falla valida";
        }
        this.mensaje(mensaje);
        return ok;
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
