package cl.android.trabajo.ctexpress.Mantenedor.MongoDB;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Leonardo on 17-12-2017.
 */

public class MantenedorUsuarioMongoDB extends AsyncTask<String,Void,Void>{
    String resultado;
    String method_name;

    @Override
    protected Void doInBackground(String... params) {
        try {
            Log.i("Do execute", "....");
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://192.168.1.83:90/WebServiceMongoDB.asmx";
            String SOAP_ACTION = "http://tempuri.org/insertarUsuario";
            SoapObject request = null;

            Log.i("Method_Name", method_name);
            if(method_name.equals("insertarUsuario")) {
                request = new SoapObject(NAMESPACE, method_name);
                request.addProperty("rut", params[0]);
                request.addProperty("nombre", params[1]);
                request.addProperty("apellido", params[2]);
                request.addProperty("correo", params[3]);
                request.addProperty("clave", params[4]);
                request.addProperty("tipoUsuario", params[5]);
                request.addProperty("activo", "No");

            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(URL);

            try {

                transportSE.call(SOAP_ACTION, envelope);

                resultado = "Datos Guardados";
            } catch (Exception e) {

                resultado = "Error " + e.getMessage();

            }
        }catch (Exception e){

            resultado = "Error";

        }
        Log.i("Resultado",resultado);
        Log.i("Finish execute","....");
        return null;
    }

    public void setMethod_name(String method_name){
        this.method_name = method_name;
    }
}
