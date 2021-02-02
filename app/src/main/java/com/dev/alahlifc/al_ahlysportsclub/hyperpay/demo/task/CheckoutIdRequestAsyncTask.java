package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.task;

import android.os.AsyncTask;


/**
 * Represents an async task to request a checkout id from the server.
 */
public class CheckoutIdRequestAsyncTask extends AsyncTask<String, Void, String> {

    private CheckoutIdRequestListener listener;

    public CheckoutIdRequestAsyncTask(CheckoutIdRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
      /*  if (params.length != 2) {
            return null;
        }

        String amount = params[0];
        String currency = params[1];*/

        return requestCheckoutId("", "");
    }

    @Override
    protected void onPostExecute(String checkoutId) {
        if (listener != null) {
            listener.onCheckoutIdReceived(checkoutId);
        }
    }

    private String requestCheckoutId(String amount,
                                     String currency) {
       // String urlString = Constants.BASE_URL + "/token?" ;
              //  "//amount=" + amount +
               // "//&currency=" + currency +
                //"&paymentType=PA" +
                /* store notificationUrl on your server to change it any time without updating the app */
    /*           // "&notificationUrl=http://52.59.56.185:80/notification";
        URL url;
        HttpURLConnection connection = null;
        String checkoutId = null;

        try {*/
//            url = new URL(urlString);
//            connection = (HttpURLConnection) url.openConnection();
//            connection.setConnectTimeout(Constants.CONNECTION_TIMEOUT);
//
//            JsonReader reader = new JsonReader(
//                    new InputStreamReader(connection.getInputStream(), "UTF-8"));
//
//            reader.beginObject();
//
//            while (reader.hasNext()) {
//                if (reader.nextName().equals("checkoutId")) {
//                    checkoutId = reader.nextString();
//
//                    break;
//                }
//            }
//
//            reader.endObject();
//            reader.close();
//
//            Log.d(Constants.LOG_TAG, "Checkout ID: " + checkoutId);
//    /*    } catch (Exception e) {
//            Log.e(Constants.LOG_TAG, "Error: ", e);
//        } finally {
//            if (connection != null) {
//                connection.disconnect();
//            }
//        }*/

        return  "CC68D45620120DE88CD873BFE0C67718.uat01-vm-tx03";

        //  "976036F3D0F032A1B3F3FB5917811DF6.uat01-vm-tx04";
    }
}