package uk.co.hd_tech.basketdemo.controller;

import uk.co.hd_tech.basketdemo.data.fixer.FixerRESTService;
import uk.co.hd_tech.basketdemo.data.model.FXRate;
import uk.co.hd_tech.basketdemo.data.model.FixerResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import timber.log.Timber;

/**
 * FX rate controller
 * Created by hardeep on 28/11/2015.
 */
public class FixerController {

    public interface UpdateListener {
        void onUpdated();
    }

    private static final String DATE_FORMAT = "HH:mm:ss dd/M/yyyy";

    private static FixerController instance = new FixerController();

    private FixerRESTService fixerRESTService;
    private List<FXRate> fxRates;
    private String updateTime = "-";
    private Throwable restError;

    private boolean isLoading;
    private UpdateListener updateListener;

    public static FixerController getInstance() {
        return instance;
    }

    public FixerController() {
        fixerRESTService = new FixerRESTService();
    }

    public void setUpdateListener(UpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    private void kickListener() {
        if (updateListener != null) {
            updateListener.onUpdated();
        }
    }

    public void initialiseExchangeRates() {
        if (fxRates == null) {
            requestLatestExchangeRates();
        }
    }

    public void requestLatestExchangeRates() {
        Timber.d("Requesting FX rates");
        isLoading = true;
        fixerRESTService.getLatestExchangeRates(fixerResponseCallback);
        kickListener();
    }

    public boolean isLoading() {
        return isLoading;
    }

    public List<FXRate> getFxRates() {
        return fxRates;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public Throwable getRestError() {
        return restError;
    }

    private Callback<FixerResponse> fixerResponseCallback = new Callback<FixerResponse>() {

        @Override
        public void onResponse(Response<FixerResponse> response, Retrofit retrofit) {
            fxRates = modifyResponse(response.body()).getFxRates();
            updateTime = response.body().getDate();
            isLoading = false;
            Timber.d("Got FX rates");
            kickListener();
        }

        @Override
        public void onFailure(Throwable t) {
            isLoading = false;
            fxRates = null;
            updateTime = "-";
            restError = t;
            Timber.d("Error requesting FX rates: " + t.getMessage());
            kickListener();
        }
    };

    /**
     * Converts unwieldy fixer rate data to a list of java Currencies and fx rates
     * Overrides the timestamp with the time the data was received
     * Adds an additional base rate to allow selection the original currency
     *
     * @param fixerResponse
     * @return Modified fixer response
     */
    private FixerResponse modifyResponse(FixerResponse fixerResponse) {
        List<FXRate> fxRates = getFXRates(fixerResponse.getRates().any());

        FXRate baseFXRate = new FXRate();
        baseFXRate.setCurrency(Currency.getInstance(fixerResponse.getBase()));
        baseFXRate.setRate(1);
        fxRates.add(baseFXRate);

        fixerResponse.setFxRates(fxRates);
        fixerResponse.setRates(null);

        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        String date = format.format(new Date());

        fixerResponse.setDate(date);

        return fixerResponse;
    }

    private List<FXRate> getFXRates(Map<String, Double> rates) {

        List<FXRate> fxRates = new ArrayList<>();

        if (!rates.isEmpty()) {
            Set<Map.Entry<String, Double>> entries = rates.entrySet();
            for (Map.Entry<String, Double> entry : entries) {
                Currency currency = Currency.getInstance(entry.getKey());

                FXRate fxRate = new FXRate();
                fxRate.setCurrency(currency);
                fxRate.setRate(entry.getValue());
                fxRates.add(fxRate);
            }
        }
        return fxRates;
    }
}