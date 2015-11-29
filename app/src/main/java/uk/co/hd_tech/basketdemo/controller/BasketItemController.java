package uk.co.hd_tech.basketdemo.controller;

import uk.co.hd_tech.basketdemo.data.model.BasketItem;
import uk.co.hd_tech.basketdemo.data.model.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Basket item controller.
 * Dummy class for demo
 */
public class BasketItemController {

    private List<BasketItem> basketItems;

    public BasketItemController() {
        basketItems = new ArrayList<>();
        basketItems.add(new BasketItem("Bacon", 2.45, Unit.PACK));
        basketItems.add(new BasketItem("Sausages", 3.00, Unit.PACK));
        basketItems.add(new BasketItem("Peas", 0.95, Unit.BAG));
        basketItems.add(new BasketItem("Potatoes", 1.50, Unit.BAG));
        basketItems.add(new BasketItem("Carrots", 1.12, Unit.BAG));
        basketItems.add(new BasketItem("Eggs", 2.10, Unit.DOZEN));
        basketItems.add(new BasketItem("Milk", 1.30, Unit.BOTTLE));
        basketItems.add(new BasketItem("Orange Juice", 2.00, Unit.BOTTLE));
        basketItems.add(new BasketItem("Beans", 0.73, Unit.CAN));
        basketItems.add(new BasketItem("Spaghetti", 0.73, Unit.CAN));
        basketItems.add(new BasketItem("Corn", 0.20, Unit.CAN));
    }

    public List<BasketItem> getBasketItems() {
        // TODO connect this to a REST service that provides real basket items
        return basketItems;
    }
}