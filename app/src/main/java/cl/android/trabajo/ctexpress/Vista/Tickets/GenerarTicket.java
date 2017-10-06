package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorEquipo;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorFalla;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorSala;
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class GenerarTicket extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String rut;
    private Spinner piso,sala,codigoEquipo,tipoEquipo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_ticket);
        rut = getIntent().getStringExtra("rutUsuario");
        this.piso = (Spinner) findViewById(R.id.spPisoTicket);
        this.sala = (Spinner) findViewById(R.id.spSalaTicket);
        this.codigoEquipo = (Spinner) findViewById(R.id.spCodigoEquipoTicket);
        this.tipoEquipo = (Spinner) findViewById(R.id.spTipoEquipoTicket);

        cargarSpinners();

        this.piso.setOnItemSelectedListener(this);
        this.sala.setOnItemSelectedListener(this);

    }

    public void siguienteTicket(View view) {
        Intent intent = new Intent(this,Solucion.class);
        startActivity(intent);
    }
    public Ticket tempTicket(){
        Ticket ticket  = new Ticket();
        String mensaje = "";
        Spinner codigoEquipo = (Spinner) findViewById(R.id.spCodigoEquipoTicket);
        Spinner falla = (Spinner) findViewById(R.id.spFallaTicket);
        EditText detalle = (EditText) findViewById(R.id.txtDetalleTicket);
        Ticket dtoTicket = new Ticket();
        MantenedorTicket negocioTicket = new MantenedorTicket(this);

        if (falla.getSelectedItemPosition()!= 0){
            if (!detalle.getText().toString().isEmpty()){
                dtoTicket.setDetalle(detalle.getText().toString());
                dtoTicket.setCodigoEquipo(codigoEquipo.getSelectedItem().toString());
                dtoTicket.setRutUsuario(rut);
                dtoTicket.setEstado("Creado");
                dtoTicket.setCodigoFalla(Integer.parseInt(String.valueOf(falla.getSelectedItemId())));

                negocioTicket.insert(dtoTicket);
            }
            else{
                mensaje = "El campo detalle no puede ir vacio";
            }
        }
        else{
            mensaje = "Debe seleccionar una falla valida";
        }
        this.mensaje(mensaje);
        ticket = dtoTicket;
        return ticket;
    }

    private void cargarSpinners(){
        cargarSpinnerPiso();
        cargarSpinnerTipoEquipo();
    }

    private void cargarSpinnerTipoEquipo(){
        Spinner spinner = (Spinner) findViewById(R.id.spTipoEquipoTicket);
        String[] tipoEquipo = {"Seleccione","Aire Acondicionado","Computador","Proyector","Televisor"};
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoEquipo));
    }

    private void cargarSpinnerPiso(){
        MantenedorSala negocioSala = new MantenedorSala(this);
        ArrayAdapter<Integer> adapterPiso = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,negocioSala.getAllPiso());
        adapterPiso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        piso.setAdapter(adapterPiso);
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

        switch (adapterView.getId()){
            case R.id.spPisoTicket:
                MantenedorSala negocioSala = new MantenedorSala(this);
                ArrayAdapter<String> adapterPiso = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,negocioSala.getByPiso(position));
                adapterPiso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                this.sala.setAdapter(adapterPiso);
                break;
            case R.id.spSalaTicket:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
