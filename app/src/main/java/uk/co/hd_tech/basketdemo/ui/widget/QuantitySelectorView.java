package uk.co.hd_tech.basketdemo.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import uk.co.hd_tech.basketdemo.R;

/**
 * Quantity picker for basket items.
 * Allows add/remove by button & direct text entry. Limited to 9999 items
 */
public class QuantitySelectorView extends LinearLayout {

    public interface OnQuantityChangedListener {
        void onQuantityChanged(int quantity);
    }

    @Bind(R.id.add_item)
    View addItem;

    @Bind(R.id.remove_item)
    View removeItem;

    @Bind(R.id.quantity)
    EditText quantityEditText;

    private int quantity;
    private OnQuantityChangedListener listener;

    public QuantitySelectorView(Context context) {
        super(context);
        init(context);
    }

    public QuantitySelectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public QuantitySelectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setSaveEnabled(true);

        View view = LayoutInflater.from(context).inflate(R.layout.view_quantity_selector, this);
        ButterKnife.bind(this, view);

        quantity = 0;
        updateTextView();

        quantityEditText.addTextChangedListener(textWatcher);
    }

    public void setOnQuantityChangedListener(OnQuantityChangedListener listener) {
        this.listener = listener;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        updateTextView();
    }

    public void incrementQuantity() {
        quantity++;
        updateTextView();
    }

    public void decrementQuantity() {
        if (quantity == 0) {
            return;
        }
        quantity--;
        updateTextView();
    }

    private void updateTextView() {
        removeItem.setEnabled(quantity != 0);
        quantityEditText.setText(String.valueOf(quantity));
    }

    @OnClick(R.id.add_item)
    void onIncrement() {
        incrementQuantity();
    }

    @OnClick(R.id.remove_item)
    void onDecrement() {
        decrementQuantity();
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // nada
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // nada
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().equals("")) {
                quantity = 0;
            } else {
                try {
                    quantity = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    quantity = 0;
                }
            }

            removeItem.setEnabled(quantity != 0);

            if (listener != null) {
                listener.onQuantityChanged(quantity);
            }
        }
    };
}