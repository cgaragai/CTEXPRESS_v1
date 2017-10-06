package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorEquipo;
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
        //cargarSpinnerTipoEquipo();
    }

    /*private void cargarSpinnerTipoEquipo(){
        Spinner spinner = (Spinner) findViewById(R.id.spTipoEquipoTicket);
        String[] tipoEquipo = {"Seleccione","Aire Acondicionado","Computador","Proyector","Televisor"};
        spinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoEquipo));
    }*/

    private void cargarSpinnerPiso(){
        MantenedorSala negocioSala = new MantenedorSala(this);
        //Cambios
        ArrayList<Integer> pisos = negocioSala.getAllPiso();
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("Seleccione");
        for(int piso: pisos)
            opciones.add(String.valueOf(piso));
        ArrayAdapter<String> adapterPiso = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,opciones);
        //
        adapterPiso.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        piso.setAdapter(adapterPiso);
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        Log.i("onItemSelected", "OK");
        switch (adapterView.getId()){
            case R.id.spPisoTicket:
                Log.i("spPisoTicket", "OK");
                //Cambios
                String piso = String.valueOf(adapterView.getItemAtPosition(position));
                Log.i("Numero piso", piso);
                if(piso.equals("Seleccione")){
                    this.sala.setEnabled(false);
                    this.sala.setAdapter(null);
                }else {
                    MantenedorSala negocioSala = new MantenedorSala(this);
                    ArrayList<String> opciones = negocioSala.getCodigoSalaByPiso(Integer.parseInt(piso));
                    opciones.add(0, "Seleccione");
                    ArrayAdapter<String> adapterSala = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
                    //
                    adapterSala.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    this.sala.setAdapter(adapterSala);
                    //Cambios
                    this.sala.setEnabled(true);
                }
                this.tipoEquipo.setEnabled(false);
                this.tipoEquipo.setAdapter(null);
                this.codigoEquipo.setEnabled(false);
                this.codigoEquipo.setAdapter(null);

                //
                break;

            case R.id.spSalaTicket:
                Log.i("spSalaTicket", "OK");
                //Cambios
                String codSala = String.valueOf(adapterView.getItemAtPosition(position));
                Log.i("Sala", codSala);
                if(codSala.equals("Seleccione")){
                    this.tipoEquipo.setEnabled(false);
                    this.tipoEquipo.setAdapter(null);
                }else {
                    MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
                    ArrayList<String> opciones = mantenedorEquipo.getAllTipoEquipoByCodigoSala(codSala);
                    opciones.add(0, "Seleccione");
                    ArrayAdapter<String> adapterTipoEquipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
                    adapterTipoEquipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    this.tipoEquipo.setAdapter(adapterTipoEquipo);
                    this.tipoEquipo.setEnabled(true);
                }
                this.codigoEquipo.setEnabled(false);
                this.codigoEquipo.setAdapter(null);
                break;

            case R.id.spTipoEquipoTicket:
                Log.i("spTipoEquipoTicket", "OK");
                String tipoEquipo = String.valueOf(adapterView.getItemAtPosition(position));
                Log.i("Tipo equipo", tipoEquipo);
                if(tipoEquipo.equals("Seleccione")){
                    this.codigoEquipo.setEnabled(false);
                    this.codigoEquipo.setAdapter(null);
                }else {
                    MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
                    Log.i("Sala Seleccionada", String.valueOf(this.sala.getSelectedItem()));
                    ArrayList<String> opciones = mantenedorEquipo.getCodByCodSalaAndTipoEquipo(String.valueOf(this.sala.getSelectedItem()), tipoEquipo);
                    opciones.add(0, "Seleccione");
                    ArrayAdapter<String> adapterCodigoEquipo = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, opciones);
                    adapterCodigoEquipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    this.codigoEquipo.setAdapter(adapterCodigoEquipo);
                    this.codigoEquipo.setEnabled(true);
                }
                break;
                //
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
