package cl.android.trabajo.ctexpress.Vista.Reportes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cl.android.trabajo.ctexpress.Mantenedor.MantenedorTicket;
import cl.android.trabajo.ctexpress.Modelo.Ticket;
import cl.android.trabajo.ctexpress.R;
import cl.android.trabajo.ctexpress.Vista.Tickets.Solucion;

/**
 * Created by Leonardo on 06-10-2017.
 */

public class Estadisticas extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private Spinner principal,detalle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);

        this.principal = (Spinner) findViewById(R.id.spTipoBusquedaEstadistica);
        this.detalle = (Spinner) findViewById(R.id.spDetalleBusquedaEstadistica);


        cargarSpinnerPrincipal();
        detalle.setEnabled(false);

        principal.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String tipoBusqueda = String.valueOf(adapterView.getItemAtPosition(i));
        Spinner spinner = (Spinner) findViewById(R.id.spDetalleBusquedaEstadistica);
        ArrayAdapter<String> adapter;
        switch (tipoBusqueda){
            case "Ticket":
                String[] detalleTicket ={"Seleccione","Abierto","Asignado","AutoSolucionado","Solucionado"};
                adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,detalleTicket);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                this.detalle.setEnabled(true);
                break;
            case "Docente":
                String[] detalleDocente = {"Seleccione","Cantidad Ticket Creados"};
                adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,detalleDocente);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                this.detalle.setEnabled(true);
                break;
            case "Tecnico":
                String[] detalleTecnico = {"Seleccione","Cantidad Ticket Asignados"};
                adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,detalleTecnico);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                this.detalle.setEnabled(true);
                break;
            default:
                this.detalle.setEnabled(false);
                this.detalle.setAdapter(null);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void obtenerEstadisticas(View view) {
        String posPrincipal = "";
        String posDetalle = "";
        ListView auxListView = (ListView) findViewById(R.id.listEstadistica);
        if (principal.getSelectedItemPosition()>0){
            posPrincipal = this.principal.getSelectedItem().toString();
            posDetalle = this.detalle.getSelectedItem().toString();
        }
        switch (posPrincipal){
            case "Ticket":
                if (detalle.getSelectedItemPosition()>0){
                    MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
                    List<Ticket> ticketList = mantenedorTicket.getAllByEstado(posDetalle);
                    String[] lista = new String[ticketList.size()];
                    Iterator iter = ticketList.iterator();
                    int pos = 0;
                    if (ticketList.size() > 0) {
                        while (iter.hasNext()){
                            Ticket ticket = new Ticket();
                            ticket = (Ticket) iter.next();
                            lista[pos] ="Ticket ID: "+ticket.getCodigoTicket()+ " Usuario:" + ticket.getRutUsuario() + " Estado: " + ticket.getEstado();
                            pos++;
                        }
                        auxListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista));
                    }
                }
                else{this.mensaje("Seleccione un detalle valido");}
                break;
            case "Docente":
                if (detalle.getSelectedItemPosition()>0){
                    MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
                    ArrayList<String> docenteList = mantenedorTicket.getAllByProfesor();
                    String[] lista = new String[docenteList.size()];
                    if (docenteList.size() > 0) {
                        for (int i = 0; i < docenteList.size(); i++)
                            lista[i] = docenteList.get(i);
                        auxListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista));
                    }
                }
                else{this.mensaje("Seleccione un detalle valido");}
                break;
            case "Tecnico":
                if (detalle.getSelectedItemPosition()>0){
                    MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
                    ArrayList<String> tecnicoList = mantenedorTicket.getAllByTecnico();
                    String[] lista = new String[tecnicoList.size()];
                    if (tecnicoList.size() > 0) {
                        for (int i = 0; i < tecnicoList.size(); i++)
                            lista[i] = tecnicoList.get(i);
                        auxListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista));
                    }
                }
                else{this.mensaje("Seleccione un detalle valido");}
                break;
            default:
                this.mensaje("Seleccione un campo valido");
                break;
        }
    }

    private void cargarSpinnerPrincipal(){
        Spinner spinner = (Spinner) findViewById(R.id.spTipoBusquedaEstadistica);
        String[] tipoEquipo = {"Seleccione","Ticket","Docente","Tecnico"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,tipoEquipo);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void mensaje(String mensaje){
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
