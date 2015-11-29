package uk.co.hd_tech.basketdemo.data.fixer;

import uk.co.hd_tech.basketdemo.data.model.FixerResponse;

import retrofit.Call;
import retrofit.http.GET;

/**
 * Fixer REST API
 */
interface FixerAPI {

    @GET("/latest?base=GBP")
    Call<FixerResponse> getLatestExchangeRates();

}