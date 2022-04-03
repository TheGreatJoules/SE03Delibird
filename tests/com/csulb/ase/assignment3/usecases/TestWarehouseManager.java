package com.csulb.ase.assignment3.usecases;

import com.csulb.ase.assignment3.components.WarehouseManager;
import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.Warehouse;
import com.csulb.ase.assignment3.utils.LoadUtils;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class TestWarehouseManager {
    private static final String PRODUCTS_PATH = "tests/com/csulb/ase/assignment3/data/products.json";
    private WarehouseManager warehouseManager;

    @BeforeMethod
    public void setup() throws IOException {
        this.warehouseManager = new WarehouseManager(LoadUtils.loadProductsFromJson(PRODUCTS_PATH));
    }

    @Test(dataProvider="read-products")
    public void test_FindProduct(String str) {
        Product exact = LoadUtils.getProductFromJson(str);
        Product actual = this.warehouseManager.readProduct(Objects.requireNonNull(exact).getWarehouse_id(), exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "add-products")
    public void test_CreateProduct(String str){
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.warehouseManager.createProduct(exact);
        assert transaction_status == 0;
        Product actual = this.warehouseManager.readProduct(Objects.requireNonNull(exact).getWarehouse_id(), exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider="update-products")
    public void test_UpdateProduct(String str) {
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.warehouseManager.updateProduct(exact);
        assert transaction_status == 0;
        Product actual = this.warehouseManager.readProduct(Objects.requireNonNull(exact).getWarehouse_id(), exact.getId());
        assertThat(actual).isEqualToComparingFieldByField(exact);
    }

    @Test(dataProvider = "read-products")
    public void test_DeleteProduct(String str){
        Product exact = LoadUtils.getProductFromJson(str);
        int transaction_status = this.warehouseManager.deleteProduct(exact);
        assert transaction_status == 0;
        Product actual = this.warehouseManager.readProduct(Objects.requireNonNull(exact).getWarehouse_id(), exact.getId());
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
                {"{\"id\":\"PRO-STR-123\",\"warehouse_id\":\"WAR-USA-CA-1\",\"warehouse_address\":\"Irvine\",\"product_type\":\"STEREO\",\"manufacturer\":\"Sony\",\"model_name\":\"R-S202BL\",\"series\":\"RX-V\",\"height\":5.5,\"width\":17.125,\"depth\":12.625,\"weight\":14.8,\"product_color\":\"BLACK\",\"output_wattage\":200,\"channels\":2,\"audio_zones\":1,\"minimum_impedance\":\"4 ohms\",\"wifi_capable\":false,\"bluetooth_enabled\":true,\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="add-products")
    public static Object[][] getAddedProducts() {
        return new Object[][] {
                {"{\"id\":\"PRO-TV-124\",\"warehouse_id\":\"WAR-USA-CA-1\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"TELEVISION\",\"manufacturer\":\"Sony\",\"model_name\":\"KD55X80K\",\"series\":\"X80K\",\"height\":48.63,\"width\":13.38,\"depth\":30.88,\"weight\":49.9,\"product_color\":\"BLACK\",\"year\":2022,\"resolution\":\"4K\",\"display_type\":\"LCD\",\"refresh_type\":\"60 Hz\",\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="update-products")
    public static Object[][] getUpdateProducts() {
        return new Object[][] {
                {"{\"id\":\"PRO-STR-123\",\"warehouse_id\":\"WAR-USA-CA-1\",\"warehouse_address\":\"Long Beach\",\"product_type\":\"STEREO\",\"manufacturer\":\"Sony\",\"model_name\":\"R-S202BL\",\"series\":\"RX-V\",\"height\":5.5,\"width\":17.125,\"depth\":12.625,\"weight\":14.8,\"product_color\":\"WHITE\",\"output_wattage\":200,\"channels\":2,\"audio_zones\":1,\"minimum_impedance\":\"4 ohms\",\"wifi_capable\":false,\"bluetooth_enabled\":true,\"stock_count\":5,\"sold_count\":0}"}
        };
    }

    @DataProvider(name="read-warehouses")
    public static Object[][] getSavedWarehouse() {
        return new Object[][] {
                {"WAR-USA-CA-1"}
        };
    }

    @DataProvider(name="update-warehouses")
    public static Object[][] getUpdateWarehouse() {
        return new Object[][] {
                {"WAR-USA-CA-1", "11837 Artesia Blvd"}
        };
    }
}
