package cl.android.trabajo.ctexpress.Vista.Tickets;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
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
import cl.android.trabajo.ctexpress.Mantenedor.MantenedorUsuario;
import cl.android.trabajo.ctexpress.Modelo.Equipo;
import cl.android.trabajo.ctexpress.Modelo.Sala;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Main.MainDocente;

/**
 * Created by Leonardo on 30-09-2017.
 */

public class GenerarTicket extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private String rut, anterior;
    private Spinner piso,sala,codigoEquipo,tipoEquipo, falla, estado, rutTecnico;
    private TextView rutDocente;
    private Ticket ticketLocal;
    private boolean autoseleccionado, cargarDatos;
    private int conteoSpinner;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generar_ticket);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        rut = getIntent().getStringExtra("rutUsuario");
        anterior = getIntent().getStringExtra("anterior");

        conteoSpinner = 0;
        this.piso = (Spinner) findViewById(R.id.spPisoTicket);
        this.sala = (Spinner) findViewById(R.id.spSalaTicket);
        this.codigoEquipo = (Spinner) findViewById(R.id.spCodigoEquipoTicket);
        this.tipoEquipo = (Spinner) findViewById(R.id.spTipoEquipoTicket);
        this.falla = (Spinner) findViewById(R.id.spFallaTicket);

        this.estado = (Spinner) findViewById(R.id.spEstadoTicket);
        this.rutTecnico = (Spinner) findViewById(R.id.spRutTecnicoTicket);
        this.rutDocente = (TextView) findViewById(R.id.tvRutDocenteTicket);

        this.piso.setOnItemSelectedListener(this);
        this.sala.setOnItemSelectedListener(this);
        this.tipoEquipo.setOnItemSelectedListener(this);
        this.codigoEquipo.setOnItemSelectedListener(this);
        this.falla.setOnItemSelectedListener(this);
        this.estado.setOnItemSelectedListener(this);

        cargarSpinners();
        ticketLocal = (Ticket) getIntent().getSerializableExtra("Ticket");

        if(rut.equals("")) {
            cambiarEstado(View.VISIBLE,true);
            cargarDatos = true;
            autoseleccionado = true;
            this.rutTecnico.setOnItemSelectedListener(this);
        }else{
            cambiarEstado(View.INVISIBLE,false);
        }
    }

    public void siguienteTicket(View view) {
        Ticket ticket = tempTicket();
        if(rut.equals("")){
            if(ticket != null){
                mensaje("Ticket actualizado");
            }else{
                mensaje("Error al actualizar ticket");
            }
        } else {
            if (ticket.getCodigoTicket() > 0) {
                if (ticket.getEstado().equals("Abierto")) {
                    cargarSpinnerPiso();
                    mensaje("Ticket generado");
                } else {
                    Intent intent = new Intent(this, Solucion.class);
                    intent.putExtra("Ticket", ticket);
                    startActivity(intent);
                }
            } else {
                mensaje("Error al crear ticket");
            }
        }
    }

    public Ticket tempTicket(){
        Ticket ticket = new Ticket();
        String codigoEquipo = String.valueOf(this.codigoEquipo.getSelectedItem());
        EditText etDetalle = (EditText) findViewById(R.id.txtDetalleTicket);

        MantenedorTicket negocioTicket = new MantenedorTicket(this);
        ticket.setRutUsuario(rut);
        ticket.setTipoEquipo(String.valueOf(tipoEquipo.getSelectedItem()));
        ticket.setCodigoSala(String.valueOf(sala.getSelectedItem()));
        ticket.setCodigoFalla(getSoloIdFalla(String.valueOf(this.falla.getSelectedItem())));
        if(!codigoEquipo.equals("Desconocido"))ticket.setCodigoEquipo(codigoEquipo);
        ticket.setDetalle(etDetalle.getText().toString());

        if(rut.equals("")) {
            ticket.setEstado(String.valueOf(estado.getSelectedItem()));
            ticket.setRutTecnico(String.valueOf(rutTecnico.getSelectedItem()));
            ticket.setCodigoTicket(ticketLocal.getCodigoTicket());
            ticket.setRutUsuario(rutDocente.getText().toString());

            boolean updateOK = negocioTicket.update(ticket);
            if(!updateOK) ticket = null;
        }else {
            if(falla.getSelectedItemPosition() == 1)
                ticket.setEstado("Abierto");
            else
                ticket.setEstado("Creado");
            int id = negocioTicket.insert(ticket);
            if (id > -1) ticket.setCodigoTicket(id);
        }

        return ticket;
    }

    private void cargarSpinners(){
        cargarSpinnerPiso();
        if(rut.equals("")){
            cargarSpinnerEstado();
            cargarSpinnerRutTecnico();
        }
    }


    private void cargarSpinnerPiso(){
        MantenedorSala negocioSala = new MantenedorSala(this);
        ArrayList<Integer> pisos = negocioSala.getAllPiso();
        ArrayList<String> opciones = new ArrayList<>();
        for(int piso: pisos)
            opciones.add(String.valueOf(piso));
        piso.setAdapter(getArrayAdapter(opciones, ""));
    }

    private void cargarSpinnerSala(String piso){
        MantenedorSala mantenedorSala = new MantenedorSala(this);
        sala.setAdapter(getArrayAdapter(mantenedorSala.getCodigoSalaByPiso(Integer.parseInt(piso)), ""));
    }

    private void cargarSpinnerTipoEquipo(String codigoSala){
        MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
        tipoEquipo.setAdapter(getArrayAdapter(mantenedorEquipo.getAllTipoEquipoByCodigoSala(codigoSala), ""));
    }

    private void cargarSpinnerCodigoEquipo(String codigoSala, String tipoEquipo){
        MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
        codigoEquipo.setAdapter(getArrayAdapter(mantenedorEquipo.getCodByCodSalaAndTipoEquipo(codigoSala, tipoEquipo), "Desconocido"));
    }

    private void cargarSpinnerEstado(){
        ArrayList<String> opciones = new ArrayList<>();
        opciones.add("Creado");
        opciones.add("Autosolucionado");
        opciones.add("Abierto");
        opciones.add("Asignado");
        opciones.add("Confirmación");
        opciones.add("Solucionado");
        estado.setAdapter(getArrayAdapter(opciones, ""));
    }

    private void cargarSpinnerRutTecnico(){
        MantenedorUsuario mantenedorUsuario = new MantenedorUsuario(this);
        rutTecnico.setAdapter(getArrayAdapter(mantenedorUsuario.getAllRutOfTipo("Tecnico"),"No Asignado"));
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        int cantidadEnabled = 0;
        String itemSelected = String.valueOf(adapterView.getItemAtPosition(position));
        switch (adapterView.getId()){
            case R.id.spPisoTicket:
                if(conteoSpinner < 7 || conteoSpinner == 14 || !rut.equals("")) {
                    if (itemSelected.equals("Seleccione")) {
                        ++cantidadEnabled;
                    } else {
                        MantenedorSala negocioSala = new MantenedorSala(this);
                        this.sala.setAdapter(getArrayAdapter(negocioSala.getCodigoSalaByPiso(Integer.parseInt(itemSelected)), ""));
                        this.sala.setEnabled(true);
                    }
                    cantidadEnabled += 3;
                }
                break;

            case R.id.spSalaTicket:
                if(conteoSpinner < 7 || conteoSpinner == 14 || !rut.equals("")) {
                    if (itemSelected.equals("Seleccione")) {
                        ++cantidadEnabled;
                    } else {
                        MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
                        this.tipoEquipo.setAdapter(getArrayAdapter(mantenedorEquipo.getAllTipoEquipoByCodigoSala(itemSelected), ""));
                        this.tipoEquipo.setEnabled(true);
                    }
                    cantidadEnabled += 2;
                }
                break;

            case R.id.spTipoEquipoTicket:
                if(conteoSpinner < 7 || conteoSpinner == 14 || !rut.equals("")) {
                    if (itemSelected.equals("Seleccione")) {
                        ++cantidadEnabled;
                    } else {
                        MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
                        this.codigoEquipo.setAdapter(getArrayAdapter(mantenedorEquipo.getCodByCodSalaAndTipoEquipo(String.valueOf(this.sala.getSelectedItem()), itemSelected), "Desconocido"));
                        this.codigoEquipo.setEnabled(true);
                    }
                    ++cantidadEnabled;
                }
                break;

            case R.id.spCodigoEquipoTicket:
                if(conteoSpinner < 7 || conteoSpinner == 14 || !rut.equals("")) {
                    if (itemSelected.equals("Seleccione")) {
                        ++cantidadEnabled;
                    } else {
                        this.falla.setAdapter(getArrayAdapter(null, ""));
                        this.falla.setEnabled(true);
                    }
                }
                break;

            case R.id.spFallaTicket:
                if(falla.getSelectedItemPosition() > 0)
                    cantidadEnabled = -1;
                break;
            case R.id.spEstadoTicket:
                if(position != 1 && position != 5 && !autoseleccionado){
                    setSpinnerSelection(estado, ticketLocal.getEstado());
                    mensaje("Solo puede seleccionar Autosolucionado o Solucionado");
                }
                autoseleccionado = false;
                break;
            case R.id.spRutTecnicoTicket:
                if(!itemSelected.equals(ticketLocal.getRutTecnico()))
                    if(position == 0) {
                        autoseleccionado = true;
                        estado.setSelection(2);
                    }else if(ticketLocal.getRutTecnico() == null || ticketLocal.getRutTecnico().equals("")) {
                        autoseleccionado = true;
                        estado.setSelection(3);
                    }
                break;
        }
        setSpinnerEnabled(cantidadEnabled);
        if(rut.equals("") && conteoSpinner == 14 && !autoseleccionado && !cargarDatos){
            enabledSiguienteButton(ticketEditado());
        }

        if(conteoSpinner == 7 && cargarDatos){
            cargarDatos();
            cargarDatos = false;
        }

        if(conteoSpinner < 14) ++conteoSpinner;
    }

    private ArrayAdapter<String> getArrayAdapter(ArrayList<String> datos, String otroDato){
        if(!otroDato.isEmpty()) datos.add(0, otroDato);
        if(datos == null){
            MantenedorFalla mantenedorFalla = new MantenedorFalla(this);
            datos = mantenedorFalla.getCodFallaAndDescripcion();
        }
        if(!rut.equals("")) datos.add(0, "Seleccione");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void setSpinnerEnabled(int cantidadEnabled){
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
                if(!rut.equals(""))enabledSiguienteButton(false);
                break;
            default:
                enabledSiguienteButton(true);
        }
    }

    private void enabledSiguienteButton(boolean enabled){
        Button siguiente = (Button) findViewById(R.id.btnSigTicket);
        siguiente.setEnabled(enabled);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void eliminar(View view) {
        MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
        boolean deleteOK = mantenedorTicket.delete(ticketLocal.getCodigoTicket());
        if(deleteOK){
            Intent intent = new Intent(this, VerListaTickets.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Main", "MainAdmin");
            intent.putExtra("estado", "");
            intent.putExtra("rutUsuario", "");
            startActivity(intent);
        }else{
            mensaje("Error al eliminar ticket");
        }
    }

    private void cargarDatos(){
        if(ticketLocal == null){
            mensaje("Error al cargar datos del ticket");
        }else{
            MantenedorEquipo mantenedorEquipo = new MantenedorEquipo(this);
            MantenedorSala mantenedorSala = new MantenedorSala(this);

            Equipo auxEquipo = mantenedorEquipo.getByCodigoEquipo(ticketLocal.getCodigoEquipo());
            Sala auxSala = mantenedorSala.getByCodigoSala(ticketLocal.getCodigoSala());

            setSpinnerSelection(piso, String.valueOf(auxSala.getPiso()));
            cargarSpinnerSala(String.valueOf(auxSala.getPiso()));

            setSpinnerSelection(sala, auxSala.getCodigoSala());
            cargarSpinnerTipoEquipo(auxSala.getCodigoSala());

            setSpinnerSelection(tipoEquipo, ticketLocal.getTipoEquipo());
            cargarSpinnerCodigoEquipo(auxSala.getCodigoSala(), ticketLocal.getTipoEquipo());

            setSpinnerSelection(codigoEquipo, auxEquipo.getCodigoEquipo());

            setSpinnerSelection(falla, String.valueOf(ticketLocal.getCodigoFalla()));

            autoseleccionado = true;
            setSpinnerSelection(estado, ticketLocal.getEstado());

            rutDocente.setText(ticketLocal.getRutUsuario());

            autoseleccionado = true;
            setSpinnerSelection(rutTecnico, ticketLocal.getRutTecnico());

            EditText etDetalle = (EditText) findViewById(R.id.txtDetalleTicket);
            etDetalle.setText(ticketLocal.getDetalle());
        }
    }

    private void setSpinnerSelection(Spinner spinner, String seleccion){
        for(int i=0; i<spinner.getCount();i++) {
            String itemSpinner = String.valueOf(spinner.getItemAtPosition(i));
            if(spinner == falla) itemSpinner = String.valueOf(getSoloIdFalla(itemSpinner));

            if(itemSpinner.equals(seleccion)){
                spinner.setSelection(i);
                break;
            }
        }
    }

    private int getSoloIdFalla(String codAndDetalleFalla){
        return Integer.parseInt(codAndDetalleFalla.split("-")[0]);
    }

    private void cambiarEstado(int v, boolean enable){
        Button btnEliminar = (Button) findViewById(R.id.btnEliminar);
        btnEliminar.setEnabled(enable);
        btnEliminar.setVisibility(v);

        this.estado.setVisibility(v);
        TextView tvEstado = (TextView) findViewById(R.id.tvEstadoTicket);
        tvEstado.setVisibility(v);

        Button btnSiguiente = (Button) findViewById(R.id.btnSigTicket);
        if(enable)btnSiguiente.setText("Actualizar");

        this.rutTecnico.setVisibility(v);
        TextView tvRutTecnico = (TextView) findViewById(R.id.tvRutTecnico);
        tvRutTecnico.setVisibility(v);

        this.rutDocente.setVisibility(v);
        TextView tvRutDocente = (TextView) findViewById(R.id.tvRutDocente);
        tvRutDocente.setVisibility(v);
    }

    private boolean ticketEditado(){//Comparar entre ticketLocal con el temporal
        MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
        return mantenedorTicket.compararTickets(ticketLocal, tempTicket());
    }

    @Override
    public void onBackPressed() {
        if(anterior.equals("MainDocente")){
            Intent intent = new Intent(this, MainDocente.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("rutUsuario", rut);
            startActivity(intent);
        }else if(anterior.equals("VerListaTickets")){
            Intent intent = new Intent(this, VerListaTickets.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Main", "MainAdmin");
            intent.putExtra("estado", "");
            intent.putExtra("rutUsuario", "");
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean ok = false;
        if(item.getItemId() == android.R.id.home){
            ok = true;
            onBackPressed();
        }
        return ok;
    }
}
