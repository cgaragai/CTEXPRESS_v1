package cl.android.trabajo.ctexpress.Adapters;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Modelo.SolucionPropuesta;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Elaps-Merlina on 06-10-2017.
 */

public class AdapterSoluciones extends BaseAdapter {

    protected Activity activity;
    ArrayList<SolucionPropuesta> soluciones = new ArrayList<>();

    public AdapterSoluciones(Activity activity, ArrayList<SolucionPropuesta> soluciones){
        this.activity = activity;
        this.soluciones = soluciones;
        for(SolucionPropuesta solucionPropuesta:soluciones)
            Log.i("SolucionPropuesta",solucionPropuesta.getCodigoSolucion() + " " + solucionPropuesta.getDescripcionSolucion());
    }

    @Override
    public int getCount() {
        return soluciones.size();
    }

    @Override
    public Object getItem(int position) {
        return soluciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.lista_solucion, null);
        }

        SolucionPropuesta solucion = soluciones.get(position);

        TextView tvCodSolucion = v.findViewById(R.id.tvCodSolucion);
        tvCodSolucion.setText(solucion.getCodigoSolucion());

        TextView tvDescripcion = v.findViewById(R.id.tvDetalle);
        tvDescripcion.setText(solucion.getDescripcionSolucion());

        return v;
    }
}
