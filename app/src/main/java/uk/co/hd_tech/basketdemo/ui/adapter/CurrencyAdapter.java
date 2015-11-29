package uk.co.hd_tech.basketdemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.hd_tech.basketdemo.R;
import uk.co.hd_tech.basketdemo.data.model.FXRate;

import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Currency adapter for currency selection
 */
public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.ViewHolder> {

    private static final String CURRENCY_DISPLAY = "%s (%s)";

    public interface CurrencySelectionListener {
        void onCurrencySelected(FXRate fxRate);
    }

    private Context context;
    private List<FXRate> fxRates;
    private CurrencySelectionListener listener;

    private SparseBooleanArray selectedItems;

    public CurrencyAdapter(Context context, List<FXRate> fxRates, CurrencySelectionListener listener) {
        this.context = context;
        this.fxRates = fxRates;
        this.listener = listener;

        selectedItems = new SparseBooleanArray(fxRates.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_currency_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CurrencyAdapter.ViewHolder holder, int position) {
        FXRate fxRate = fxRates.get(position);

        String currencyDisplayName = fxRate.getCurrency().getDisplayName(Locale.getDefault());
        String currencyCode = fxRate.getCurrency().getSymbol(Locale.getDefault());

        holder.currencyView.setText(String.format(CURRENCY_DISPLAY, currencyDisplayName, currencyCode));
        holder.row.setSelected(selectedItems.get(position, false));
    }

    public void setData(List<FXRate> fxRates) {
        this.fxRates = fxRates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fxRates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Bind(R.id.currency_row)
        View row;

        @Bind(R.id.currency)
        TextView currencyView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();

            FXRate fxRate = fxRates.get(adapterPosition);
            listener.onCurrencySelected(fxRate);

            for (int i = 0; i < fxRates.size(); i++) {
                if (selectedItems.get(i, false)) {
                    selectedItems.delete(i);
                    row.setSelected(false);
                }
            }

            selectedItems.put(adapterPosition, true);
            row.setSelected(true);
        }
    }
}
