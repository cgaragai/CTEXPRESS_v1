package cl.android.trabajo.ctexpress.Mantenedor.MongoDB;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.ArrayList;

/**
 * Created by Elaps-Merlina on 19-11-2017.
 */

public class MantenedorSolucionPropuestaWS extends AsyncTask<String, Void, Void> {

    String resultado = "";
    //antes de la ejecucion
    @Override
    protected void onPreExecute(){
        //ejemplo ejecutar barra de estado, desabilitar algunos campos
    }

    //Ejecucion
    @Override
    protected Void doInBackground(String... params) {
        try{
            String NAMESPACE = "http://tempuri.org/";
            String URL = "http://localhost/WebServiceMongoDB.asmx";
            String METHOD_NAME = "insertar";
            String SOAP_ACTION = "http://tempuri.org/insertar";

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("columnas", params[0].toString());
            request.addProperty("valores", params[1].toString());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transportSE = new HttpTransportSE(URL);

            try{
                transportSE.call(SOAP_ACTION,envelope);
                //se procesa el resultado devuelto
                resultado = "Datos guardados";
            }catch (Exception e){
                resultado = "Error";
            }
        }catch (Exception e){
            resultado = "Error";
        }
        return null;
    }

    //Despues de la ejecucion
    @Override
    protected void onPostExecute(Void args){
        //cerrar barra de progreso, volver a habilitar campos
    }
}
