package com.csulb.ase.assignment3.usecases;

import com.csulb.ase.assignment3.components.InventoryManager;
import com.csulb.ase.assignment3.models.ColorEnum;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.Stereo;
import com.csulb.ase.assignment3.models.Television;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestInventoryManager {
    private static final String PRODUCTS_PATH = "tests/com/csulb/ase/assignment3/data/products.json";
    private InventoryManager inventoryManager;

    @BeforeMethod
    public void setup() throws IOException {
        this.inventoryManager = LoadUtils.loadInventoryManagerFromJson(PRODUCTS_PATH);
    }

    @Test(dataProvider = "add-stereo")
    public void test_CreateStereo_Product(String str) {
        Product exact = LoadUtils.getProductFromJson(str);
        Product actual = Stereo.builder()
                .id("PRO-STR-123:WAR-1")
                .warehouse_address("Irvine")
                .product_type(ProductEnum.STEREO)
                .manufacturer("Sony")
                .model_name("R-S202BL")
                .series("RX-V")
                .height(5.5)
                .width(17.125)
                .depth(12.625)
                .weight(14.8)
                .product_color(ColorEnum.BLACK)
                .output_wattage(200)
                .channels(2)
                .audio_zones(1)
                .minimum_impedance("4 ohms")
                .wifi_capable(false)
                .bluetooth_enabled(true)
                .stock_count(5)
                .sold_count(0)
                .build();
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-television")
    public void test_CreateTelevision_Product(String str) {
        Product exact = LoadUtils.getProductFromJson(str);
        Product actual = Television.builder()
                .id("PRO-TV-124:WAR-1")
                .warehouse_address("Long Beach")
                .product_type(ProductEnum.TELEVISION)
                .manufacturer("Sony")
                .model_name("KD55X80K")
                .series("X80K")
                .height(48.63)
                .width(13.38)
                .depth(30.88)
                .weight(49.9)
                .product_color(ColorEnum.BLACK)
                .year(2022)
                .resolution("4K")
                .display_type("LCD")
                .refresh_type("60 Hz")
                .stock_count(5)
                .sold_count(0)
                .build();
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-products")
    public void test_ReadCreate_Inventory(String str) {
        int current_items = this.inventoryManager.readInventory().getTotal_items();
        int current_warehouses = this.inventoryManager.readInventory().getTotal_warehouses();
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.inventoryManager.createInventory(exact);
        assert transaction_status == 0;
        int actual = this.inventoryManager.readInventory().getTotal_items();
        assert (current_items + 1) == actual;
    }

    @Test(dataProvider = "update-products")
    public void test_ReadUpdate_Inventory(String str) {
        int current_items = this.inventoryManager.readInventory().getTotal_items();
        int current_warehouses = this.inventoryManager.readInventory().getTotal_warehouses();
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.inventoryManager.updateInventory(exact);
        assert transaction_status == 0;
        int actual = this.inventoryManager.readInventory().getTotal_items();
        assert current_items == actual;
    }

    @Test(dataProvider = "delete-products")
    public void test_ReadDelete_Inventory(String str) {
        int current_items = this.inventoryManager.readInventory().getTotal_items();
        int current_warehouses = this.inventoryManager.readInventory().getTotal_warehouses();
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.inventoryManager.deleteInventory(exact.getId());
        assert transaction_status == 0;
        int actual = this.inventoryManager.readInventory().getTotal_items();
        assert (current_items - 1) == actual;
    }

    @Test(dataProvider = "read-warehouses")
    public void test_Read_Warehouse(String warehouse_id) {
        Warehouse warehouse = this.inventoryManager.readWarehouses(warehouse_id);
        assert warehouse != null;
    }

    @DataProvider(name="add-television")
    public static Object[][] getAddedTelevision() {
        return new Object[][] {
                {"{\"id\":\"PRO-TV-124:WAR-1\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"TELEVISION\",\"manufacturer\":\"Sony\",\"model_name\":\"KD55X80K\",\"series\":\"X80K\",\"height\":48.63,\"width\":13.38,\"depth\":30.88,\"weight\":49.9,\"product_color\":\"BLACK\",\"year\":2022,\"resolution\":\"4K\",\"display_type\":\"LCD\",\"refresh_type\":\"60 Hz\",\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="add-stereo")
    public static Object[][] getAddedStereo() {
        return new Object[][] {
                {"{\"id\":\"PRO-STR-123:WAR-1\",\"warehouse_address\":\"Irvine\",\"product_type\":\"STEREO\",\"manufacturer\":\"Sony\",\"model_name\":\"R-S202BL\",\"series\":\"RX-V\",\"height\":5.5,\"width\":17.125,\"depth\":12.625,\"weight\":14.8,\"product_color\":\"BLACK\",\"output_wattage\":200,\"channels\":2,\"audio_zones\":1,\"minimum_impedance\":\"4 ohms\",\"wifi_capable\":false,\"bluetooth_enabled\":true,\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="add-products")
    public static Object[][] getAddedProducts() {
        return new Object[][] {
                {"{\"id\":\"PRO-TV-124:WAR-1\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"TELEVISION\",\"manufacturer\":\"Sony\",\"model_name\":\"KD55X80K\",\"series\":\"X80K\",\"height\":48.63,\"width\":13.38,\"depth\":30.88,\"weight\":49.9,\"product_color\":\"BLACK\",\"year\":2022,\"resolution\":\"4K\",\"display_type\":\"LCD\",\"refresh_type\":\"60 Hz\",\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="update-products")
    public static Object[][] getUpdatedProducts() {
        return new Object[][] {
                {"{\"id\":\"PRO-TV-124:WAR-1\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"TELEVISION\",\"manufacturer\":\"Sony\",\"model_name\":\"KD55X80K\",\"series\":\"X80K\",\"height\":48.63,\"width\":13.38,\"depth\":30.88,\"weight\":49.9,\"product_color\":\"BLACK\",\"year\":2022,\"resolution\":\"4K\",\"display_type\":\"LCD\",\"refresh_type\":\"60 Hz\",\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="delete-products")
    public static Object[][] getDeletedProducts() {
        return new Object[][] {
                {"{\"id\":\"PRO-STR-123:WAR-1\",\"warehouse_address\":\"Irvine\",\"product_type\":\"STEREO\",\"manufacturer\":\"Sony\",\"model_name\":\"R-S202BL\",\"series\":\"RX-V\",\"height\":5.5,\"width\":17.125,\"depth\":12.625,\"weight\":14.8,\"product_color\":\"BLACK\",\"output_wattage\":200,\"channels\":2,\"audio_zones\":1,\"minimum_impedance\":\"4 ohms\",\"wifi_capable\":false,\"bluetooth_enabled\":true,\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="read-warehouses")
    public static Object[][] getSavedWarehouses() {
        return new Object[][] {
                {"WAR-1"}
        };
    }
}
