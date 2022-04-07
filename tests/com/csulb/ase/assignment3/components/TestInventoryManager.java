package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ColorEnum;
import com.csulb.ase.assignment3.models.inventory.Electronics;
import com.csulb.ase.assignment3.models.inventory.Product;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.inventory.Warehouse;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestInventoryManager {
    private InventoryManager inventoryManager;

    @BeforeMethod
    public void setup() throws IOException {
        this.inventoryManager = LoadUtils.loadInventoryManagerFromJson(LoadUtils.PRODUCT_PATH);
    }

    @Test(dataProvider = "add-stereo")
    public void test_CreateStereo_Product_Successful(Product exact) {
        Product actual = this.inventoryManager.createProduct("STR-123", "WAR-1",ProductEnum.STEREO, "Sony",
                "R-S202BL", "RX-V", "Irvine", 5.0, 17.0, 12.0, 14.0,
                null,2022, 5, 0, ColorEnum.BLACK, null, null, 200.0,
                2.0, 1.0, true, true, "4 ohms");
        int current_items = this.inventoryManager.readInventory().getTotal_items();
        int transaction_status = this.inventoryManager.createInventory(actual);
        assert transaction_status == 0;
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-television")
    public void test_CreateTelevision_Product_Successful(Product exact) {
        Product actual = this.inventoryManager.createProduct("TLV-124", "WAR-1", ProductEnum.TELEVISION, "Sony",
                "KD55X80K", "X80K", "Newport", 50.0, 15.0, 30.0, 50.0,
                "LCD",2022, 5, 0, ColorEnum.BLACK, "4K", "60 Hz", null,
                null, null, null, null, null);
        int transaction_status = this.inventoryManager.createInventory(actual);
        assert transaction_status == 0;
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-products")
    public void test_AddProduct_Inventory_Successful(Product exact) {
        int current_items = this.inventoryManager.readInventory().getTotal_items();
        int current_warehouses = this.inventoryManager.readInventory().getTotal_warehouses();
        int transaction_status = this.inventoryManager.createInventory(exact);
        assert transaction_status == 0;
        int actual = this.inventoryManager.readInventory().getTotal_items();
        assert (current_items + 1) == actual;
    }

    @Test(dataProvider = "update-products")
    public void test_ReadUpdate_Inventory_Successful(Product exact) {
        int current_items = this.inventoryManager.readInventory().getTotal_items();
        int current_warehouses = this.inventoryManager.readInventory().getTotal_warehouses();
        int transaction_status = this.inventoryManager.updateInventory(exact);
        assert transaction_status == 0;
        int actual = this.inventoryManager.readInventory().getTotal_items();
        assert current_items == actual;
    }

    @Test(dataProvider = "delete-products")
    public void test_ReadDelete_Inventory_Successful(Product exact) {
        int current_items = this.inventoryManager.readInventory().getTotal_items();
        int current_warehouses = this.inventoryManager.readInventory().getTotal_warehouses();
        int transaction_status = this.inventoryManager.deleteInventory(exact.getId());
        assert transaction_status == 0;
        int actual = this.inventoryManager.readInventory().getTotal_items();
        assert (current_items - 1) == actual;
    }

    @Test(dataProvider = "update-product-quantity")
    public void test_UpdateInventory(String product_id, int quantity) {
        int exact_quantity = this.inventoryManager.findProduct(product_id).getStock_count() + quantity;
        int transaction_status = this.inventoryManager.updateInventory(product_id, quantity);
        int actual_quantity = this.inventoryManager.findProduct(product_id).getStock_count();
        assert transaction_status == 0;
        assert exact_quantity == actual_quantity;
    }

    @DataProvider(name="update-product-quantity")
    public static Object[][] getUpdatedInventoryQuality() {
        return new Object[][] {
                {"TLV-123", 10}
        };
    }

    @Test(dataProvider = "read-warehouses")
    public void test_Read_Warehouse(String warehouse_id) {
        Warehouse warehouse = this.inventoryManager.readWarehouses(warehouse_id);
        assert warehouse != null;
    }

    @DataProvider(name="add-television")
    public static Object[][] getAddedTelevision() {
        return new Object[][] {
                {electronics_television_item("TLV-124", "WAR-1","Sony", "KD55X80K", "X80K",5, 0)}
        };
    }

    @DataProvider(name="add-stereo")
    public static Object[][] getAddedStereo() {
        return new Object[][] {
                {electronics_stereo_item("STR-123", "WAR-1","Sony", "R-S202BL","RX-V", 5, 0)}
        };
    }

    @DataProvider(name="add-products")
    public static Object[][] getAddedProducts() {
        return new Object[][] {
                {electronics_television_item("TLV-123", "WAR-1","Sony", "KD55X80K", "X80K", 5, 0)},
                {electronics_stereo_item("STR-123", "WAR-1","Sony", "R-S202BL","RX-V", 5, 0)}
        };
    }

    @DataProvider(name="update-products")
    public static Object[][] getUpdatedProducts() {
        return new Object[][] {
                {electronics_television_item("TLV-124", "WAR-1","Sony", "KD55X80K", "X81K", 5, 0)}
        };
    }

    @DataProvider(name="delete-products")
    public static Object[][] getDeletedProducts() {
        return new Object[][] {
                {electronics_television_item("STR-234", "WAR-1","Sony", "R-S202BL", "RX-V", 5, 0)}
        };
    }

    @DataProvider(name="read-warehouses")
    public static Object[][] getSavedWarehouses() {
        return new Object[][] {
                {"WAR-1"}
        };
    }

    public static Electronics electronics_television_item(String id, String warehouse_id, String manufacturer, String model, String series, int stock, int sold) {
        return Electronics.builder()
                .id(id)
                .warehouse_id(warehouse_id)
                .warehouse_address("Newport")
                .product_type(ProductEnum.TELEVISION)
                .manufacturer(manufacturer)
                .model_name(model)
                .series(series)
                .height(50.0)
                .width(15.0)
                .depth(30.0)
                .weight(50.0)
                .product_color(ColorEnum.BLACK)
                .year(2022)
                .resolution("4K")
                .display_type("LCD")
                .refresh_type("60 Hz")
                .stock_count(stock)
                .sold_count(sold)
                .build();
    }

    public static Electronics electronics_stereo_item(String id, String warehouse_id, String manufacturer, String model, String series, int stock, int sold) {
        return Electronics.builder()
                .id(id)
                .product_type(ProductEnum.STEREO)
                .warehouse_id(warehouse_id)
                .warehouse_address("Irvine")
                .manufacturer(manufacturer)
                .model_name(model)
                .series(series)
                .year(2022)
                .height(5.0)
                .width(17.0)
                .depth(12.0)
                .weight(14.0)
                .product_color(ColorEnum.BLACK)
                .output_wattage(200.0)
                .channels(2.0)
                .audio_zones(1.0)
                .minimum_impedance("4 ohms")
                .wifi_capable(true)
                .bluetooth_enabled(true)
                .stock_count(stock)
                .sold_count(sold)
                .build();
    }
}
