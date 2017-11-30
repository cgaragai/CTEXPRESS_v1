package cl.android.trabajo.ctexpress.Mantenedor.MongoDB;

import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by Elaps-Merlina on 29-11-2017.
 */

public class WebServiceTicket {

    //----Modificar
    private static String NAMESPACE = "http://tempuri.org/";
    private static String URL = "http://192.168.1.83/WebServiceMongoDB.asmx";
    private static String TOKEN = "hqcRzb987vi2Tdl/h1mz0w==";
    //----modificar
    public static String getCantidadTicket() {
        //--Modificar
        String nomMetodo = "getCantidadTicket";
        String soapAction = NAMESPACE + nomMetodo;
        //Modificar
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, nomMetodo);
        // Property which holds input parameters
        /*PropertyInfo tokenWS = new PropertyInfo();
        tokenWS.setName("token");
        tokenWS.setValue(TOKEN);
        tokenWS.setType(String.class);
        request.addProperty(tokenWS);*/

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try
        {
            androidHttpTransport.debug = true;

            //Log.d("dump Request: " ,androidHttpTransport.requestDump);
            //Log.d("dump response: " ,androidHttpTransport.responseDump);
            // Invoke web service
            androidHttpTransport.call(soapAction, envelope);
            // Get the response
            SoapObject response = (SoapObject) envelope.bodyIn;
            /*SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable*/
            resTxt = androidHttpTransport.responseDump;
        }
        catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = e.toString();
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String insertarTicket(String[] params) {
        Log.i("Metodo", "Insertar");
        //--Modificar
        String nomMetodo = "insertarTicket";
        String soapAction = NAMESPACE + nomMetodo;
        //Modificar
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, nomMetodo);
        // Property which holds input parameters
        /*PropertyInfo tokenWS = new PropertyInfo();
        tokenWS.setName("token");
        tokenWS.setValue(TOKEN);
        tokenWS.setType(String.class);
        request.addProperty(tokenWS);*/
        request.addProperty("codTicket", params[0]);
        request.addProperty("rutUsuario", params[1]);
        request.addProperty("codFalla", params[2]);
        request.addProperty("codTipoEquipo", params[3]);
        request.addProperty("codEquipo", params[4]);
        request.addProperty("codSala", params[5]);
        request.addProperty("detalle", params[6]);
        request.addProperty("estado", params[7]);
        request.addProperty("fecha", params[8]);
        request.addProperty("observacion", params[9]);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE transportSE = new HttpTransportSE(URL);
        try
        {
            transportSE.call(soapAction, envelope);

            resTxt = "Ticket guardado";
        }
        catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = e.toString();
        }
        //Return resTxt to calling object
        return resTxt;
    }

    public static String getAllTickets() {
        //--Modificar
        String nomMetodo = "getAllTickets";
        String soapAction = NAMESPACE + nomMetodo;
        //Modificar
        String resTxt = null;
        // Create request
        SoapObject request = new SoapObject(NAMESPACE, nomMetodo);
        // Property which holds input parameters
        PropertyInfo tokenWS = new PropertyInfo();
        tokenWS.setName("token");
        tokenWS.setValue(TOKEN);
        tokenWS.setType(String.class);
        request.addProperty(tokenWS);

        // Create envelope
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        //Set envelope as dotNet
        envelope.dotNet = true;
        // Set output SOAP object
        envelope.setOutputSoapObject(request);
        // Create HTTP call object
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        try
        {
            androidHttpTransport.debug = true;

            //Log.d("dump Request: " ,androidHttpTransport.requestDump);
            //Log.d("dump response: " ,androidHttpTransport.responseDump);
            // Invoke web service
            androidHttpTransport.call(soapAction, envelope);
            // Get the response
            SoapObject response = (SoapObject) envelope.bodyIn;
            /*SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            // Assign it to resTxt variable static variable*/
            resTxt = androidHttpTransport.responseDump;
        }
        catch (Exception e)
        {
            //Print error
            e.printStackTrace();
            //Assign error message to resTxt
            resTxt = e.toString();
        }
        //Return resTxt to calling object
        return resTxt;
    }

}
