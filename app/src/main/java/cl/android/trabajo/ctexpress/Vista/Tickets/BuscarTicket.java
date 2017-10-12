package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Leonardo on 06-10-2017.
 */

public class BuscarTicket extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_ticket);
        ocultarLabel();
    }

    private void ocultarLabel(){
        TextView codigoTicket = (TextView) findViewById(R.id.lblTicketID);
        TextView usuario = (TextView) findViewById(R.id.lblTicketUsuario);
        TextView tecnico = (TextView) findViewById(R.id.lblTecnico);
        TextView codigoEquipo = (TextView) findViewById(R.id.lblCodigoEquipo);
        TextView codigoFalla = (TextView) findViewById(R.id.lblCodigoFalla);
        TextView detalle = (TextView) findViewById(R.id.lblDetalle);
        TextView estado = (TextView) findViewById(R.id.lblEstado);

        codigoTicket.setVisibility(View.INVISIBLE);
        usuario.setVisibility(View.INVISIBLE);
        tecnico.setVisibility(View.INVISIBLE);
        codigoEquipo.setVisibility(View.INVISIBLE);
        codigoFalla.setVisibility(View.INVISIBLE);
        detalle.setVisibility(View.INVISIBLE);
        estado.setVisibility(View.INVISIBLE);
    }

    public void buscarTicket(View view) {
        TextView codigoTicket = (TextView) findViewById(R.id.lblTicketID);
        TextView usuario = (TextView) findViewById(R.id.lblTicketUsuario);
        TextView tecnico = (TextView) findViewById(R.id.lblTecnico);
        TextView codigoEquipo = (TextView) findViewById(R.id.lblCodigoEquipo);
        TextView codigoFalla = (TextView) findViewById(R.id.lblCodigoFalla);
        TextView detalle = (TextView) findViewById(R.id.lblDetalle);
        TextView estado = (TextView) findViewById(R.id.lblEstado);
        EditText buscarTicket = (EditText) findViewById(R.id.txtBuscarTicket);
        if (!buscarTicket.getText().toString().isEmpty()){
            MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
            int codigoBuscado = Integer.valueOf(buscarTicket.getText().toString());
            Ticket ticket = mantenedorTicket.getByCodigoTicket(codigoBuscado);
            if (ticket != null){
                codigoTicket.setText("Codigo ticket: "+ticket.getCodigoTicket());
                usuario.setText("Usuario: "+ticket.getRutUsuario());
                tecnico.setText("Tecnico: "+ticket.getRutTecnico());
                codigoEquipo.setText("Codigo Equipo: "+ticket.getCodigoEquipo());
                codigoFalla.setText("Codigo Falla: "+ticket.getCodigoFalla());
                detalle.setText("Detalle Ticket: "+ticket.getDetalle());
                estado.setText("Estado Ticket: "+ticket.getEstado());

                habilitarLabel();
            }
            else{
                this.mensaje("No existe el ticket buscado");
                ocultarLabel();
            }
        }
        else {this.mensaje("Ingrese un campo valido");}
    }

    private void habilitarLabel(){
        TextView codigoTicket = (TextView) findViewById(R.id.lblTicketID);
        TextView usuario = (TextView) findViewById(R.id.lblTicketUsuario);
        TextView tecnico = (TextView) findViewById(R.id.lblTecnico);
        TextView codigoEquipo = (TextView) findViewById(R.id.lblCodigoEquipo);
        TextView codigoFalla = (TextView) findViewById(R.id.lblCodigoFalla);
        TextView detalle = (TextView) findViewById(R.id.lblDetalle);
        TextView estado = (TextView) findViewById(R.id.lblEstado);

        codigoTicket.setVisibility(View.VISIBLE);
        usuario.setVisibility(View.VISIBLE);
        tecnico.setVisibility(View.VISIBLE);
        codigoEquipo.setVisibility(View.VISIBLE);
        codigoFalla.setVisibility(View.VISIBLE);
        detalle.setVisibility(View.VISIBLE);
        estado.setVisibility(View.VISIBLE);
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
