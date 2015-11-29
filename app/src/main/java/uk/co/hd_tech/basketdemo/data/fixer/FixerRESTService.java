package uk.co.hd_tech.basketdemo.data.fixer;

import uk.co.hd_tech.basketdemo.data.model.FixerResponse;

import retrofit.Call;
import retrofit.Callback;
import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

/**
 * Fixer REST service
 */
public class FixerRESTService {

    private static final String FIXER_URL = "http://api.fixer.io/";

    private FixerAPI fixerAPI;

    public FixerRESTService() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(FIXER_URL)
                .build();

        fixerAPI = retrofit.create(FixerAPI.class);
    }

    public void getLatestExchangeRates(Callback<FixerResponse> callback) {
        Call<FixerResponse> latestExchangeRates = fixerAPI.getLatestExchangeRates();
        latestExchangeRates.enqueue(callback);
    }
}