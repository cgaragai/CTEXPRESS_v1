package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Leonardo on 06-10-2017.
 */

public class BuscarTicket extends AppCompatActivity {

    private int codigoBuscado;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_ticket);
        setVisibilityLabel(View.INVISIBLE);
        codigoBuscado = -1;
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(codigoBuscado >= 0) {
            EditText buscarTicket = (EditText) findViewById(R.id.txtBuscarTicket);
            buscarTicket.setText(String.valueOf(codigoBuscado));
            verificarTicket();
        }
    }

    private void setVisibilityLabel(int visibility){
        for(TextView auxTextView : getTextViews())
            auxTextView.setVisibility(visibility);
    }

    private ArrayList<TextView> getTextViews(){
        ArrayList<TextView> textViews = new ArrayList<>();
        textViews.add((TextView) findViewById(R.id.lblTicketID));
        textViews.add((TextView) findViewById(R.id.lblTicketUsuario));
        textViews.add((TextView) findViewById(R.id.lblTecnico));
        textViews.add((TextView) findViewById(R.id.lblCodigoEquipo));
        textViews.add((TextView) findViewById(R.id.lblCodigoFalla));
        textViews.add((TextView) findViewById(R.id.lblDetalle));
        textViews.add((TextView) findViewById(R.id.lblEstado));

        return  textViews;
    }

    public void buscarTicket(View view) {
        EditText buscarTicket = (EditText) findViewById(R.id.txtBuscarTicket);
        if (!buscarTicket.getText().toString().isEmpty()){
            codigoBuscado = Integer.valueOf(buscarTicket.getText().toString());
            verificarTicket();
        }else {
            codigoBuscado = -1;
            this.mensaje("Ingrese un campo válido");
        }
    }

    private void verificarTicket(){
        MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
        Ticket ticketBuscado = mantenedorTicket.getByCodigoTicket(codigoBuscado);
        if (ticketBuscado != null){
            setTextOfTextViews(ticketBuscado);
            setVisibilityLabel(View.VISIBLE);
        }else{
            this.mensaje("No existe el ticket buscado");
            setVisibilityLabel(View.INVISIBLE);
        }
    }

    private void setTextOfTextViews(Ticket ticketBuscado){
        ArrayList<TextView> textViews = getTextViews();
        textViews.get(0).setText("Codigo ticket: "+ticketBuscado.getCodigoTicket());
        textViews.get(1).setText("Usuario: "+ticketBuscado.getRutUsuario());
        textViews.get(2).setText("Tecnico: "+ticketBuscado.getRutTecnico());
        textViews.get(3).setText("Codigo Equipo: "+ticketBuscado.getCodigoEquipo());
        textViews.get(4).setText("Codigo Falla: "+ticketBuscado.getCodigoFalla());
        textViews.get(5).setText("Detalle Ticket: "+ticketBuscado.getDetalle());
        textViews.get(6).setText("Estado Ticket: "+ticketBuscado.getEstado());
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
