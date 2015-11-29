package uk.co.hd_tech.basketdemo;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import uk.co.hd_tech.basketdemo.controller.BasketController;

import static org.junit.Assert.assertEquals;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
//@RunWith(RobolectricGradleTestRunner.class)
//@Config(constants = BuildConfig.class)
public class BasketControllerUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_hasNoItems() {
        BasketController basketController = new BasketController();
        Assert.assertFalse(basketController.hasItems());
    }
}