package uk.co.hd_tech.basketdemo.controller;

import uk.co.hd_tech.basketdemo.data.model.BasketItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Basket item controller.
 * Adds & removes basket items. Calculates basket total given an exchange rate
 */
public class BasketController {

    private static BasketController instance = new BasketController();

    private HashMap<BasketItem, Integer> basket;

    public static BasketController getInstance() {
        return instance;
    }

    public BasketController() {
        basket = new HashMap<>();
    }

    public boolean hasItems() {
        return !basket.isEmpty();
    }

    public void setQuantityForItem(BasketItem basketItem, int quantity) {
        basket.put(basketItem, quantity);
    }

    public int getQuantityForItem(BasketItem basketItem) {
        if (basket.containsKey(basketItem)) {
            return basket.get(basketItem);
        }
        return 0;
    }

    private double getBasketTotal() {

        double basketTotal = 0;

        if (!hasItems()) {
            return 0;
        }

        Set<Map.Entry<BasketItem, Integer>> lineItems = basket.entrySet();
        for (Map.Entry<BasketItem, Integer> lineItem : lineItems) {
            int quantity = lineItem.getValue();
            double price = lineItem.getKey().getPrice();

            double lineItemTotal = price * quantity;
            basketTotal += lineItemTotal;
        }

        return basketTotal;
    }

    public double getConvertedTotal(double fxRate) {
        return getBasketTotal() * fxRate;
    }
}