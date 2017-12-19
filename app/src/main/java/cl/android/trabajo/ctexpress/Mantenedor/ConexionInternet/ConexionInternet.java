package cl.android.trabajo.ctexpress.Mantenedor.ConexionInternet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Leonardo on 17-12-2017.
 */

public class ConexionInternet {
    public boolean verificarConexion(Context ctx){
        boolean auxConect = false;
        Log.i("Verificando conexion",".....");
        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null)//existe conexion
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                auxConect = true;
        else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            auxConect = true;
        else auxConect = false;
        Log.i("Conexion",String.valueOf(auxConect));
        return auxConect;
    }
}
