package cl.android.trabajo.ctexpress.Mantenedor.MongoDB;

import android.os.AsyncTask;

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
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://192.168.0.22:90/WebServiceMongoDB.asmx";
            String SOAP_ACTION = "http://tempuri.org/insertarUsuario";
            SoapObject request = null;

            if(method_name.equals("insertarUsuario")) {
                request = new SoapObject(NAMESPACE, method_name);
                request.addProperty("rut", params[0]);
                request.addProperty("nombre", params[1]);
                request.addProperty("apellido", params[2]);
                request.addProperty("correo", params[3]);
                request.addProperty("clave", params[4]);
                request.addProperty("tipoUsuario", params[5]);

            }

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;

            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(URL);

            try {

                transportSE.call(SOAP_ACTION, envelope);


            } catch (Exception e) {

                resultado = "Error";

            }

            resultado = "Datos Guardados";
        }catch (Exception e){

            resultado = "Error";

        }

        return null;
    }
}
