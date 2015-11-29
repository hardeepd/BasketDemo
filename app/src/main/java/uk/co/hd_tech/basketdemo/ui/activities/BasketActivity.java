package uk.co.hd_tech.basketdemo.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import uk.co.hd_tech.basketdemo.R;
import uk.co.hd_tech.basketdemo.controller.BasketItemController;
import uk.co.hd_tech.basketdemo.controller.FixerController;
import uk.co.hd_tech.basketdemo.data.model.BasketItem;
import uk.co.hd_tech.basketdemo.data.network.NetworkStatus;
import uk.co.hd_tech.basketdemo.ui.adapter.BasketItemAdapter;
import uk.co.hd_tech.basketdemo.ui.dialog.BasketTotalDialog;

public class BasketActivity extends AppCompatActivity {

    @Bind(R.id.basket_items_list)
    RecyclerView basketItemList;

    private FixerController fixerController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        ButterKnife.bind(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        basketItemList.setLayoutManager(linearLayoutManager);
        basketItemList.setItemAnimator(new DefaultItemAnimator());

        BasketItemController basketItemController = new BasketItemController();

        List<BasketItem> basketItems = basketItemController.getBasketItems();
        BasketItemAdapter basketItemAdapter = new BasketItemAdapter(this, basketItems);
        basketItemList.setAdapter(basketItemAdapter);

        fixerController = FixerController.getInstance();

        if (NetworkStatus.isOnline(this, false)) {
            fixerController.initialiseExchangeRates();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_basket_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.totals:
                new BasketTotalDialog(this, fixerController.getFxRates(), fixerController.getUpdateTime()).showDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}