package cl.android.trabajo.ctexpress.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import cl.android.trabajo.ctexpress.Modelo.Usuario;
import cl.android.trabajo.ctexpress.R;

/**
 * Created by Elaps-Merlina on 04-10-2017.
 */

public class AdapterUsuarios extends BaseAdapter {

    protected Activity activity;
    ArrayList<Usuario> usuarios = new ArrayList<>();

    public AdapterUsuarios(Activity activity, ArrayList<Usuario> usuarios){
        this.activity = activity;
        this.usuarios = usuarios;
    }


    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
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
            v = inf.inflate(R.layout.lista_usuarios, null);
        }

        Usuario usuario = usuarios.get(position);

        TextView tvRut = (TextView) v.findViewById(R.id.tvRut);
        tvRut.setText(usuario.getRut());

        TextView tvNombreCompleto = (TextView) v.findViewById(R.id.tvNombreCompleto);
        tvNombreCompleto.setText(usuario.getNombre() + " " + usuario.getApellido());

        TextView tvCargo = (TextView) v.findViewById(R.id.tvCargo);
        tvCargo.setText(usuario.getTipoUsuario());

        return v;
    }
}
