package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.ColorEnum;
import com.csulb.ase.assignment3.models.Electronics;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.ProductEnum;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class TestWarehouseManager {

    private WarehouseManager warehouseManager;

    @BeforeMethod
    public void setup() throws IOException {
        this.warehouseManager = new WarehouseManager(LoadUtils.loadProductsFromJson(LoadUtils.PRODUCT_PATH));
    }

    @Test(dataProvider="read-products")
    public void test_FindProduct(Product exact) {
        Product actual = this.warehouseManager.readProduct(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-products")
    public void test_CreateProduct(Product exact){
        int transaction_status = this.warehouseManager.createProduct(exact);
        assert transaction_status == 0;
        Product actual = this.warehouseManager.readProduct(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider="update-products")
    public void test_UpdateProduct(Product exact) {
        int transaction_status = this.warehouseManager.updateProduct(exact);
        assert transaction_status == 0;
        Product actual = this.warehouseManager.readProduct(exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "read-products")
    public void test_DeleteProduct(Product exact){
        int transaction_status = this.warehouseManager.deleteProduct(exact.getId());
        assert transaction_status == 0;
        Product actual = this.warehouseManager.readProduct(exact.getId());
        assert actual == null;
    }

    @Test(dataProvider = "read-warehouses")
    public void test_FindWarehouse(String str) {
        Warehouse warehouse = this.warehouseManager.readWarehouse(str);
        assert warehouse != null;
    }

    @Test(dataProvider = "read-warehouses")
    public void test_DeleteWarehouse(String str) {
        int trasaction_status = this.warehouseManager.deleteWarehouse(str);
        assert trasaction_status == 0;
    }

    @Test(dataProvider = "update-warehouses")
    public void test_UpdateWarehouse(String warehouse_id, String address) {
        int trasaction_status = this.warehouseManager.updateWarehouse(warehouse_id, address);
        assert trasaction_status == 0;
    }

    @DataProvider(name="read-products")
    public static Object[][] getSavedProducts() {
        return new Object[][] {
                {electronics_stereo_item("WAR-1:STR-123", "Sony", "R-S202BL", "RX-V", 5, 0)}
        };
    }

    @DataProvider(name="add-products")
    public static Object[][] getAddedProducts() {
        return new Object[][] {
                {electronics_television_item("WAR-1:TLV-123", "Sony", "KD55X80K", "X80K", 5, 0)},
                {electronics_stereo_item("WAR-1:STR-123", "Sony", "R-S202BL","RX-V", 5, 0)}
        };
    }

    @DataProvider(name="update-products")
    public static Object[][] getUpdatedProducts() {
        return new Object[][] {
                {electronics_television_item("WAR-1:TLV-124", "Sony", "KD55X80K", "X81K", 5, 0)}
        };
    }

    @DataProvider(name="read-warehouses")
    public static Object[][] getSavedWarehouse() {
        return new Object[][] {
                {"WAR-1"}
        };
    }

    @DataProvider(name="update-warehouses")
    public static Object[][] getUpdateWarehouse() {
        return new Object[][] {
                {"WAR-1", "11837 Artesia Blvd"}
        };
    }

    public static Electronics electronics_television_item(String id, String manufacturer, String model, String series, int stock, int sold) {
        return Electronics.builder()
                .id(id)
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

    public static Electronics electronics_stereo_item(String id, String manufacturer, String model, String series, int stock, int sold) {
        return Electronics.builder()
                .id(id)
                .product_type(ProductEnum.STEREO)
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
