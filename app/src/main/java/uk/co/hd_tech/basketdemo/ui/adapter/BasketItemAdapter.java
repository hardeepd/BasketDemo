package uk.co.hd_tech.basketdemo.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import uk.co.hd_tech.basketdemo.R;
import uk.co.hd_tech.basketdemo.controller.BasketController;
import uk.co.hd_tech.basketdemo.data.model.BasketItem;
import uk.co.hd_tech.basketdemo.ui.widget.QuantitySelectorView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Basket item adapter for basket item selection
 */
public class BasketItemAdapter extends RecyclerView.Adapter<BasketItemAdapter.ViewHolder> {

    private Context context;
    private List<BasketItem> basketItems;
    private BasketController basketController;

    private NumberFormat numberFormat;

    public BasketItemAdapter(Context context, List<BasketItem> basketItems) {
        this.context = context;
        this.basketItems = basketItems;

        numberFormat = NumberFormat.getCurrencyInstance(Locale.UK);
        basketController = BasketController.getInstance();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_basket_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(BasketItemAdapter.ViewHolder holder, int position) {
        BasketItem basketItem = basketItems.get(position);

        holder.name.setText(basketItem.getName());
        holder.price.setText(numberFormat.format(basketItem.getPrice()));
        holder.quantity.setText(context.getString(basketItem.getUnit().getStringId()));

        holder.quantitySelector.setQuantity(basketController.getQuantityForItem(basketItem));
    }

    @Override
    public int getItemCount() {
        return basketItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements QuantitySelectorView.OnQuantityChangedListener {

        @Bind(R.id.item_name)
        TextView name;

        @Bind(R.id.item_price)
        TextView price;

        @Bind(R.id.item_quantity)
        TextView quantity;

        @Bind(R.id.quantity_selector)
        QuantitySelectorView quantitySelector;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            quantitySelector.setOnQuantityChangedListener(this);
        }

        @Override
        public void onQuantityChanged(int quantity) {
            BasketItem basketItem = basketItems.get(getAdapterPosition());
            basketController.setQuantityForItem(basketItem, quantity);
        }
    }
}
