package cl.android.trabajo.ctexpress.Mantenedor.MongoDB;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import cl.android.trabajo.ctexpress.Vista.Tickets.GenerarTicket;

/**
 * Created by Elaps-Merlina on 28-11-2017.
 */

public class MantenedorTicketMongoDB extends AsyncTask<String, Void, Void> {

    private String resultado;
    private String method_name;
    private String[] stringArr;
    private Context context;

    public MantenedorTicketMongoDB(Context context){
        this.context = context;
    }
    public void setStringArr(String[] stringArr){
        this.stringArr = stringArr;
    }
    public void setMethod_name(String method_name){
        this.method_name = method_name;
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
            Log.i("Ingresado en WS", "OK");
            WebServiceTicket ws = new WebServiceTicket();

            if(method_name.equals("insertarTicket")) {
                Log.i("Nombre metodo", "insertarTicket");
                resultado = ws.insertarTicket(params);
                Log.i("Resultado insert", resultado);
            }

            /*if(method_name.equals("getCantidadTicket")) {
                resultado = ws.getCantidadTicket();
                int inicioCli = resultado.indexOf("<" + method_name + ">") + (method_name.length() + 2);
                int finCli = resultado.indexOf("</" + method_name + ">");
                final String limpJsonCli = resultado.substring(inicioCli, finCli);
                //---cambiar en caso de usar un motor relacional

                BsonArray auxBsonArray = BsonArray.parse(limpJsonCli);
                final String[] auxStringArr = new String[auxBsonArray.size()];

                Object[] auxArrayObject = auxBsonArray.toArray();

                for (int i = 0; i < auxArrayObject.length; i++)
                {
                    BsonString auxRut = (BsonString)((BsonDocument) auxArrayObject[i]).get("rut");
                    BsonString auxNombre = (BsonString)((BsonDocument) auxArrayObject[i]).get("nombre");

                    auxStringArr[i] = auxRut.getValue() + " " + auxNombre.getValue();
                }
                this.setStringArr(auxStringArr);
            }*/

            if(method_name.equals("getAllTickets")) {

            }

            try {




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
    protected void onPreExecute()
    {
/*            pro.setVisibility(View.VISIBLE);
            btnIngresar.setEnabled(false);
            btnCancelar.setEnabled(false);
            txtRut.setEnabled(false);
            txtContra.setEnabled(false); */
    }

    @Override
    protected void onPostExecute(Void args)
    {
        try {

            switch (resultado){
                case "true":

                    break;
                default:
                    // Toast.makeText(getApplicationContext(), resultado, Toast.LENGTH_LONG).show();
                    break;
            }

        }catch (final Exception ex)
        {
            //  runOnUiThread(new Runnable() {
            //    public void run() {
            //TextView aux = (TextView)findViewById(R.id.txtTest);
            //aux.setText(ex.getMessage());
            //  }
            //});
        }
    }

    private void mensaje(String mensaje){
        Toast.makeText(context, resultado,Toast.LENGTH_SHORT).show();
    }
}
