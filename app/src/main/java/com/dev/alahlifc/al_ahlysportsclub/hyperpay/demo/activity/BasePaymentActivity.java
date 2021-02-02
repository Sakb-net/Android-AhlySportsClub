package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.dev.alahlifc.al_ahlysportsclub.Base.ExtenstionKt;
import com.dev.alahlifc.al_ahlysportsclub.Main.HomeActivity;
import com.dev.alahlifc.al_ahlysportsclub.R;
import com.dev.alahlifc.al_ahlysportsclub.Reset.Resource;
import com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.BasePaymentViewModel;
import com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.common.Constants;
import com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.task.CheckoutIdRequestAsyncTask;
import com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.task.CheckoutIdRequestListener;
import com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.task.PaymentStatusRequestListener;
import com.dev.alahlifc.al_ahlysportsclub.models.mRegisterLogin;
import com.dev.alahlifc.al_ahlysportsclub.models.mResultCallback;
import com.dev.alahlifc.al_ahlysportsclub.store.PrefManager;
import com.google.android.gms.wallet.WalletConstants;
import com.oppwa.mobile.connect.checkout.dialog.CheckoutActivity;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSettings;
import com.oppwa.mobile.connect.checkout.meta.CheckoutSkipCVVMode;

import com.oppwa.mobile.connect.exception.PaymentError;
import com.oppwa.mobile.connect.provider.Connect;
import com.oppwa.mobile.connect.provider.Transaction;
import com.oppwa.mobile.connect.provider.TransactionType;
import kotlin.Unit;
import timber.log.Timber;

import java.io.IOException;


/**
 * Represents a base activity for making the payments with mobile sdk.
 * This activity handles payment callbacks.
 */
@SuppressLint("Registered")
public class BasePaymentActivity extends BaseActivity
        implements CheckoutIdRequestListener, PaymentStatusRequestListener {

    private static final String STATE_RESOURCE_PATH = "STATE_RESOURCE_PATH";

    private BasePaymentViewModel viewModel;

    protected String resourcePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            resourcePath = savedInstanceState.getString(STATE_RESOURCE_PATH);
        }

        viewModel = ViewModelProviders.of(this).get(BasePaymentViewModel.class);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);

        /* Check if the intent contains the callback scheme. */
        if (resourcePath != null && hasCallbackScheme(intent)) {
            requestPaymentStatus(resourcePath);
        }
    }

    /**
     * Returns <code>true</code> if the Intent contains one of the predefined schemes for the app.
     *
     * @param intent the incoming intent
     * @return <code>true</code> if the Intent contains one of the predefined schemes for the app;
     *         <code>false</code> otherwise
     */
    protected boolean hasCallbackScheme(Intent intent) {
        String scheme = intent.getScheme();

        return getString(R.string.checkout_ui_callback_scheme).equals(scheme) ||
                getString(R.string.payment_button_callback_scheme).equals(scheme) ||
                getString(R.string.custom_ui_callback_scheme).equals(scheme);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(STATE_RESOURCE_PATH, resourcePath);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /* Override onActivityResult to get notified when the checkout process is done. */
        if (requestCode == CheckoutActivity.REQUEST_CODE_CHECKOUT) {
            switch (resultCode) {
                case CheckoutActivity.RESULT_OK:
                    Log.e("case okkkk","okkkkkkkkkkkkkk");

                    /* Transaction completed. */
                    Transaction transaction = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_TRANSACTION);

                    resourcePath = data.getStringExtra(CheckoutActivity.CHECKOUT_RESULT_RESOURCE_PATH);

                    Log.e("case okkkk","res path "+resourcePath);

                    /* Check the transaction type. */
                    if (transaction.getTransactionType() == TransactionType.SYNC) {
                        /* Check the status of synchronous transaction. */
                        requestPaymentStatus(resourcePath);

                    } else {
                        /* Asynchronous transaction is processed in the onNewIntent(). */
                        hideProgressDialog();
                    }

                    break;
                case CheckoutActivity.RESULT_CANCELED:
                    hideProgressDialog();
                    showAlertDialog(R.string.error_message+
                           "++++++++++++++++++++");
                    
                    break;
                case CheckoutActivity.RESULT_ERROR:
                    hideProgressDialog();

                    PaymentError error = data.getParcelableExtra(
                            CheckoutActivity.CHECKOUT_RESULT_ERROR);

                    Log.e("erorrrfffff","fffffff+");
                    showAlertDialog("inside  CheckoutActivity.RESULT_ERROR"+R.string.error_message+
                    "--"+error.getErrorInfo()+" "+error.getErrorMessage()+"--"+error.getErrorCode());

            }
        }
    }

    protected void requestCheckoutId(String callbackScheme) {
        showProgressDialog(R.string.progress_message_checkout_id);

        new CheckoutIdRequestAsyncTask(this)
                .execute("","");
    }

    @Override
    public void onCheckoutIdReceived(String checkoutId) {
        hideProgressDialog();

        if (checkoutId == null) {
            showAlertDialog(R.string.error_message);
        }
    }

    @Override
    public void onErrorOccurred() {
        hideProgressDialog();
        showAlertDialog(R.string.error_message);
    }

    @Override
    public void onPaymentStatusReceived(String paymentStatus) {
        hideProgressDialog();

        if ("OK".equals(paymentStatus)) {
            showAlertDialog(R.string.message_successful_payment);

            return;
        }

        showAlertDialog(R.string.message_unsuccessful_payment);
    }

    protected void requestPaymentStatus(String resourcePath) {
        //Toast.makeText(getApplicationContext(),"validate from server .... ",Toast.LENGTH_LONG).show();

        mRegisterLogin.Data user = PrefManager.INSTANCE.getUser();
        Timber.e(""+user.toString());
        if (user != null)
        viewModel.ResultCallBackCheck(""+user.getAccess_token(),""+CheckoutUIActivity.Checkout_id
                ,PrefManager.INSTANCE.getLanguage().toString()).observe(this, new Observer<Resource<mResultCallback>>() {
                    @Override
            public void onChanged(Resource<mResultCallback> mResultCallbackResource) {

                        if (mResultCallbackResource instanceof Resource.Progress){

                            Resource.Progress loading = (Resource.Progress) mResultCallbackResource;
                            if (loading.getLoading()){
                                //showProgressDialog("Validate");
                                showProgressDialog(R.string.progress_message_payment_status);
                            }
                            else{
                                hideProgressDialog();
                            }
                            }

                      else if(mResultCallbackResource instanceof Resource.Success){

                            Resource.Success data =( Resource.Success) mResultCallbackResource ;

                           // Toast.makeText(getApplicationContext(),""+
                             //       ((mResultCallback)data.getData()).getData().getMesagePay(),Toast.LENGTH_LONG).show();


                            if (((mResultCallback)data.getData()).getData().getOkPay()==1){
                                ExtenstionKt.showPaymentSuccessDialog(BasePaymentActivity.this,
                                        ""+ ((mResultCallback)data.getData()).getData().getMesagePay(),
                                        positive ->{
                                            positive.dismiss();

                                            Intent intent =new Intent(BasePaymentActivity.this , HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            return Unit.INSTANCE;
                                        });
                                }
                                else {


                                ExtenstionKt.showPaymentFailDialog(BasePaymentActivity.this,
                                        ""+ ((mResultCallback)data.getData()).getData().getMesagePay(),
                                        positive ->{
                                            positive.dismiss();

                                            Intent intent =new Intent(BasePaymentActivity.this , HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            return Unit.INSTANCE;
                                        });






                            }

                        }


                         else if (mResultCallbackResource instanceof Resource.Failure){


                            Resource.Failure e =( Resource.Failure) mResultCallbackResource ;
                            if ( e.getE() instanceof IOException){
                                Toast.makeText(getApplicationContext(),getString(R.string.need_internet),Toast.LENGTH_LONG).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),getString(R.string.something_wrong),Toast.LENGTH_LONG).show();
                            }

                        }





            }
        });


       /// showProgressDialog(R.string.progress_message_payment_status);
       /// new PaymentStatusRequestAsyncTask(this).execute(resourcePath);
    }

    /**
     * Creates the new instance of {@link CheckoutSettings}
     * to instantiate the {@link CheckoutActivity}.
     *
     * @param checkoutId the received checkout id
     * @return the new instance of {@link CheckoutSettings}
     */
    protected CheckoutSettings createCheckoutSettings(String checkoutId, String callbackScheme) {
        return new CheckoutSettings(checkoutId, Constants.Config.PAYMENT_BRANDS,
                Connect.ProviderMode.TEST)
                .setSkipCVVMode(CheckoutSkipCVVMode.FOR_STORED_CARDS)
                .setWindowSecurityEnabled(false)
                .setShopperResultUrl(
                        callbackScheme
                     //   "http://alahliclub.sakb.net/v1/product/paymentcallback"
                      //  callbackScheme + "://callback"

                );
        // "3056dca4e3d45e25e702bc49b875ceb4"
              //  .setGooglePayPaymentDataRequest(getGooglePayRequest());
    }

   /* private PaymentDataRequest getGooglePayRequest() {
        return GooglePayHelper.preparePaymentDataRequestBuilder(
                Constants.Config.AMOUNT,
                Constants.Config.CURRENCY,
                Constants.MERCHANT_ID,
                getPaymentMethodsForGooglePay(),
                getDefaultCardNetworksForGooglePay()
        ).build();
    }*/

    private Integer[] getPaymentMethodsForGooglePay() {
        return new Integer[] {
                WalletConstants.PAYMENT_METHOD_CARD,
                WalletConstants.PAYMENT_METHOD_TOKENIZED_CARD
        };
    }

    private Integer[] getDefaultCardNetworksForGooglePay() {
        return new Integer[] {
                WalletConstants.CARD_NETWORK_VISA,
                WalletConstants.CARD_NETWORK_MASTERCARD,
                WalletConstants.CARD_NETWORK_AMEX
        };
    }
}
