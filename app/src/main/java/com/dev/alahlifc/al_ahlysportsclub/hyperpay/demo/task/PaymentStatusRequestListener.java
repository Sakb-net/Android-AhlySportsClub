package com.dev.alahlifc.al_ahlysportsclub.hyperpay.demo.task;


public interface PaymentStatusRequestListener {

    void onErrorOccurred();
    void onPaymentStatusReceived(String paymentStatus);
}
