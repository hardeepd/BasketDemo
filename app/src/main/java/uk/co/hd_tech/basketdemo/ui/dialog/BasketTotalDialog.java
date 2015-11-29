package uk.co.hd_tech.basketdemo.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.hd_tech.basketdemo.R;
import uk.co.hd_tech.basketdemo.controller.BasketController;
import uk.co.hd_tech.basketdemo.controller.FixerController;
import uk.co.hd_tech.basketdemo.data.model.FXRate;
import uk.co.hd_tech.basketdemo.data.network.NetworkStatus;
import uk.co.hd_tech.basketdemo.ui.adapter.CurrencyAdapter;

/**
 * Basket total dialog. Plus currency selection for basket total
 */
public class BasketTotalDialog {

    private static final String UPDATED_AT = "Updated at %s";
    private static final String TOTALS = "Total %s (%s)";

    @Bind(R.id.basket_total)
    TextView basketTotal;

    @Bind(R.id.updated_time)
    TextView updatedAtTime;

    @Bind(R.id.currency_list)
    RecyclerView currencyList;

    private Context context;
    private List<FXRate> fxRates;
    private String refreshTime;
    private FXRate selectedFxRate;
    private FXRate baseFxRate;

    private CurrencyAdapter currencyAdapter;
    private NumberFormat numberFormat;

    private BasketController basketController;
    private FixerController fixerController;

    public BasketTotalDialog(Context context, List<FXRate> fxRates, String refreshTime) {
        this.context = context;
        this.fxRates = fxRates;
        this.refreshTime = refreshTime;

        if (fxRates == null || fxRates.size() == 0) {
            baseFxRate = new FXRate();
            baseFxRate.setCurrency(Currency.getInstance("GBP"));
            baseFxRate.setRate(1);

            fxRates = new ArrayList<>();
            fxRates.add(baseFxRate);
        } else {
            baseFxRate = fxRates.get(fxRates.size() - 1);
        }

        basketController = BasketController.getInstance();
        fixerController = FixerController.getInstance();
        fixerController.setUpdateListener(updateListener);

        currencyAdapter = new CurrencyAdapter(context, fxRates, currencySelectionListener);

        numberFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        numberFormat.setRoundingMode(RoundingMode.CEILING);
    }

    public void showDialog() {

        MaterialDialog.Builder dialogBuilder = new MaterialDialog.Builder(context);

        dialogBuilder.customView(R.layout.dialog_basket_total, false);
        dialogBuilder.autoDismiss(false);
        dialogBuilder.title("Basket Total");
        dialogBuilder.positiveText("Dismiss");
        dialogBuilder.onPositive(new MaterialDialog.SingleButtonCallback() {

            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                materialDialog.dismiss();
            }
        });

        dialogBuilder.neutralText("Refresh Exchange Rates");
        dialogBuilder.onNeutral(new MaterialDialog.SingleButtonCallback() {
            @Override
            public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
                refreshExchangeRates();
            }
        });

        dialogBuilder.dismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                fixerController.setUpdateListener(null);
            }
        });

        MaterialDialog dialog = dialogBuilder.build();
        ButterKnife.bind(this, dialog);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
        currencyList.setLayoutManager(layoutManager);
        currencyList.setAdapter(currencyAdapter);

        updateBasketTotal(baseFxRate);
        updateRefreshTime(refreshTime);

        dialog.show();
    }

    private void refreshExchangeRates() {
        if (NetworkStatus.isOnline(context, true)) {
            fixerController.requestLatestExchangeRates();
        }
    }

    private FixerController.UpdateListener updateListener = new FixerController.UpdateListener() {
        @Override
        public void onUpdated() {
            if (fixerController.isLoading()) {
                // TODO show loading spinner
            } else {
                if (fixerController.getFxRates() == null) {
                    new NetworkFailureDialog(context, fixerController.getRestError().getLocalizedMessage()).showDialog();
                } else {
                    fxRates = fixerController.getFxRates();
                    currencyAdapter.setData(fxRates);

                    updateRefreshTime(fixerController.getUpdateTime());
                    updateBasketTotal(getFXRate(selectedFxRate));
                }
            }
        }
    };

    private void updateRefreshTime(String refreshTime) {
        updatedAtTime.setText(String.format(UPDATED_AT, refreshTime));
    }

    private CurrencyAdapter.CurrencySelectionListener currencySelectionListener = new CurrencyAdapter.CurrencySelectionListener() {

        @Override
        public void onCurrencySelected(FXRate fxRate) {
            selectedFxRate = fxRate;
            updateBasketTotal(selectedFxRate);
            updateBasketTotal(fxRate);
        }
    };

    private void updateBasketTotal(FXRate fxRate) {
        Currency fxRateCurrency = fxRate.getCurrency();

        numberFormat.setMaximumFractionDigits(fxRateCurrency.getDefaultFractionDigits());
        numberFormat.setCurrency(fxRateCurrency);

        String currencyCode = fxRateCurrency.getCurrencyCode();
        String formattedCurrency = numberFormat.format(basketController.getConvertedTotal(fxRate.getRate()));

        basketTotal.setText(String.format(TOTALS, formattedCurrency, currencyCode));
    }

    private FXRate getFXRate(FXRate fxRate) {
        if (fxRate == null) {
            return baseFxRate;
        }

        String currencyCode = fxRate.getCurrency().getCurrencyCode();

        for (FXRate rate : fxRates) {
            if (rate.getCurrency().getCurrencyCode().equals(currencyCode)) {
                return rate;
            }
        }
        return baseFxRate;
    }
}
