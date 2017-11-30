package cl.android.trabajo.ctexpress.Mantenedor.MongoDB;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.HttpsTransportSE;

/**
 * Created by Elaps-Merlina on 25-11-2017.
 */

public class ConexionMongoDB extends AsyncTask<String, Void, Void> {

    String resultado;
    String method_name;

    public void setMethod_name(String method_name){
        this.method_name = method_name;
    }

    @Override
    protected void onPreExecute(){

    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://localhost/WebServiceMongoDB.asmx";
            String SOAP_ACTION = "http://tempuri.org/insertarTicket";
            SoapObject request = null;

            if(method_name.equals("insertarTicket")) {
                request = new SoapObject(NAMESPACE, method_name);
                request.addProperty("codTicket", params[0]);
                request.addProperty("rutUsuario", params[1]);
                request.addProperty("codFalla", params[2]);
                request.addProperty("codTipoEquipo", params[3]);
                request.addProperty("codEquipo", params[4]);
                request.addProperty("codSala", params[5]);
                request.addProperty("detalle", params[6]);
                request.addProperty("rutTecnico", params[7]);
                request.addProperty("estado", params[8]);
                request.addProperty("fecha", params[9]);
                request.addProperty("observacion", params[10]);
            }

            /*if(method_name.equals("getCantidadTicket")) {
                SoapObject request = new SoapObject(NAMESPACE, method_name);
                request.getProperty(0);
            }*/

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

    @Override
    protected void onPostExecute(Void args){

    }

    public int getCantidadTickets(){
        return 0;
    }
}
