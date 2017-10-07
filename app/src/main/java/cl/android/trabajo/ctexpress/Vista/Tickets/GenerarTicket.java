package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

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
    private Spinner piso,sala,codigoEquipo,tipoEquipo, falla, estado, rutTecnico;
    private TextView rutDocente;
    private Ticket ticketLocal;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_ticket);
        rut = getIntent().getStringExtra("rutUsuario");
        this.piso = (Spinner) findViewById(R.id.spPisoTicket);
        this.sala = (Spinner) findViewById(R.id.spSalaTicket);
        this.codigoEquipo = (Spinner) findViewById(R.id.spCodigoEquipoTicket);
        this.tipoEquipo = (Spinner) findViewById(R.id.spTipoEquipoTicket);
        this.falla = (Spinner) findViewById(R.id.spFallaTicket);

        this.estado = (Spinner) findViewById(R.id.spEstadoTicket);
        this.rutTecnico = (Spinner) findViewById(R.id.spRutTecnicoTicket);
        this.rutDocente = (TextView) findViewById(R.id.tvRutDocenteTicket);

        cargarSpinnerPiso();

        this.piso.setOnItemSelectedListener(this);
        this.sala.setOnItemSelectedListener(this);
        this.tipoEquipo.setOnItemSelectedListener(this);
        this.codigoEquipo.setOnItemSelectedListener(this);
        this.falla.setOnItemSelectedListener(this);

        if(rut.equals("")) {
            Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
            btnEliminar.setEnabled(true);
            btnEliminar.setVisibility(View.VISIBLE);

            this.estado.setOnItemSelectedListener(this);
            TextView tvEstado = (TextView) findViewById(R.id.tvEstadoTicket);
            tvEstado.setVisibility(View.VISIBLE);

            Button btnSiguiente = (Button) findViewById(R.id.btnSigTicket);
            btnSiguiente.setText("Actualizar");

            this.rutTecnico.setVisibility(View.VISIBLE);
            TextView tvRutTecnico = (TextView) findViewById(R.id.tvRutTecnico);
            tvRutTecnico.setVisibility(View.VISIBLE);

            this.rutDocente.setVisibility(View.VISIBLE);
            TextView tvRutDocente = (TextView) findViewById(R.id.tvRutDocente);
            tvRutDocente.setVisibility(View.VISIBLE);
        }

        ticketLocal = (Ticket) getIntent().getSerializableExtra("Ticket");

    }

    public void siguienteTicket(View view) {
        Ticket ticket = tempTicket();
        if(ticket.getCodigoTicket() > 0) {
            if(ticket.getEstado().equals("Abierto")){
                cargarSpinnerPiso();
                mensaje("Ticket generado");
            }else {
                Intent intent = new Intent(this, Solucion.class);
                intent.putExtra("Ticket", ticket);
                startActivity(intent);
            }
        }else{
            mensaje("Error al crear ticket");
        }
    }
    public Ticket tempTicket(){
        Ticket ticket = new Ticket();
        String codigoEquipo = String.valueOf(this.codigoEquipo.getSelectedItem());
        String codAndDetalleFalla = String.valueOf(this.falla.getSelectedItem());
        EditText etDetalle = (EditText) findViewById(R.id.txtDetalleTicket);

        MantenedorTicket negocioTicket = new MantenedorTicket(this);
        ticket.setRutUsuario(rut);

        String[] codAndDetalle = codAndDetalleFalla.split("-");

        ticket.setCodigoFalla(Integer.parseInt(codAndDetalle[0]));
        if(!codigoEquipo.equals("Desconocido"))ticket.setCodigoEquipo(codigoEquipo);
        ticket.setDetalle(etDetalle.getText().toString());

        if(falla.getSelectedItemPosition() == 1)
            ticket.setEstado("Abierto");
        else
            ticket.setEstado("Creado");
        int id = negocioTicket.insert(ticket);
        if(id > -1) ticket.setCodigoTicket(id);

        return ticket;
    }


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
        int cantidadEnabled = 0;
        switch (adapterView.getId()){
            case R.id.spPisoTicket:
                String piso = String.valueOf(adapterView.getItemAtPosition(position));
                if(piso.equals("Seleccione")){
                    ++cantidadEnabled;
                }else {
                    MantenedorSala negocioSala = new MantenedorSala(this);
                    this.sala.setAdapter(getArrayAdapter(negocioSala.getCodigoSalaByPiso(Integer.parseInt(piso)),""));
                    this.sala.setEnabled(true);
                }
                cantidadEnabled += 3;
                break;

            case R.id.spSalaTicket:
                String codSala = String.valueOf(adapterView.getItemAtPosition(position));
                if(codSala.equals("Seleccione")){
                    ++cantidadEnabled;
                }else {
                    MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
                    this.tipoEquipo.setAdapter(getArrayAdapter(mantenedorEquipo.getAllTipoEquipoByCodigoSala(codSala),""));
                    this.tipoEquipo.setEnabled(true);
                }
                cantidadEnabled += 2;
                break;

            case R.id.spTipoEquipoTicket:
                String tipoEquipo = String.valueOf(adapterView.getItemAtPosition(position));
                if(tipoEquipo.equals("Seleccione")){
                    ++cantidadEnabled;
                }else {
                    MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
                    this.codigoEquipo.setAdapter(getArrayAdapter(mantenedorEquipo.getCodByCodSalaAndTipoEquipo(String.valueOf(this.sala.getSelectedItem()), tipoEquipo), "Desconocido"));
                    this.codigoEquipo.setEnabled(true);
                }
                ++cantidadEnabled;
                break;

            case R.id.spCodigoEquipoTicket:
                String codigoEquipo = String.valueOf(adapterView.getItemAtPosition(position));
                if(codigoEquipo.equals("Seleccione")){
                    ++cantidadEnabled;
                }else {
                    this.falla.setAdapter(getArrayAdapter(null, ""));
                    this.falla.setEnabled(true);
                }
                break;

            case R.id.spFallaTicket:
                if(falla.getSelectedItemPosition() > 0)
                    cantidadEnabled = -1;
                break;

        }
        setSpinnerEnabled(cantidadEnabled);
    }

    private ArrayAdapter<String> getArrayAdapter(ArrayList<String> datos, String otroDato){
        if(!otroDato.isEmpty()) datos.add(0, otroDato);
        if(datos == null){
            MantenedorFalla mantenedorFalla = new MantenedorFalla(this);
            datos = mantenedorFalla.getCodFallaAndDescripcion();
        }
        datos.add(0, "Seleccione");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void setSpinnerEnabled(int cantidadEnabled){
        Button siguiente = (Button) findViewById(R.id.btnSigTicket);
        switch(cantidadEnabled){
            case 4:
                this.sala.setEnabled(false);
                this.sala.setAdapter(null);
            case 3:
                this.tipoEquipo.setEnabled(false);
                this.tipoEquipo.setAdapter(null);
            case 2:
                this.codigoEquipo.setEnabled(false);
                this.codigoEquipo.setAdapter(null);
            case 1:
                this.falla.setEnabled(false);
                this.falla.setAdapter(null);
            case 0:
                if(!rut.equals(""))siguiente.setEnabled(false);
                break;
            default:
                siguiente.setEnabled(true);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void eliminar(View view) {
    }
}
