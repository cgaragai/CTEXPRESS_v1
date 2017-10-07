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
                String[] detalleTicket ={"Seleccione","Activo","Cerrado", "Solucion"};
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
                String[] detalleTecnico = {"Seleccione","Ticket Asignados"};
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
        String posPrincipal = this.principal.getSelectedItem().toString();
        int posDetalle = this.detalle.getSelectedItemPosition();
        ListView auxListView = (ListView) findViewById(R.id.listEstadistica);

        switch (posPrincipal){
            case "Ticket":
                MantenedorTicket mantenedorTicket = new MantenedorTicket(this);
                switch (posDetalle){
                    case 0:
                        this.mensaje("Escoja un detalle valido");
                        break;
                    case 1:
                        ArrayList<Ticket> auxList = mantenedorTicket.getAllByEstado("Creado");
                        String[] lista = new String[auxList.size()];
                        if (auxList.size() > 0) {
                            for (int i = 0; i < auxList.size(); i++)
                                lista[i] = "Codigo :" + auxList.get(i).getCodigoTicket() + " Tecnico:" + auxList.get(i).getRutTecnico() + " Estado: " + auxList.get(i).getEstado();
                            auxListView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista));
                        }
                        break;
                    case 2:
                        ArrayList<Ticket> auxList2 = mantenedorTicket.getAllByEstado("Cerrado");
                        String[] lista2 = new String[auxList2.size()];
                        if (auxList2.size() > 0)
                            for (int i=0;i < auxList2.size();i++)
                                lista2[i] = "Codigo :"+ auxList2.get(i).getCodigoTicket() + " Tecnico:"+ auxList2.get(i).getRutTecnico()+" Estado: "+ auxList2.get(i).getEstado();
                        auxListView.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista2));
                        break;
                    case 3:
                        ArrayList<Ticket> auxList3 = mantenedorTicket.getAllByEstado("Solucion");
                        String[] lista3 = new String[auxList3.size()];
                        if (auxList3.size() > 0)
                            for (int i=0;i < auxList3.size();i++)
                                lista3[i] = "Codigo :"+ auxList3.get(i).getCodigoTicket() + " Tecnico:"+ auxList3.get(i).getRutTecnico()+" Estado: "+ auxList3.get(i).getEstado();
                        auxListView.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,lista3));
                        break;
                }
                break;
            case "Docente":
                switch (posDetalle){
                    case 0:
                        this.mensaje("Seleccione un detalle valido");
                        break;
                    case 1:

                        break;
                }
                break;
            case "Tecnico":
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
