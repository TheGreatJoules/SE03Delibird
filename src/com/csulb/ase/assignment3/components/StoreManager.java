package com.csulb.ase.assignment3.components;

import com.csulb.ase.assignment3.models.Product;
import com.csulb.ase.assignment3.models.RegionEnum;
import com.csulb.ase.assignment3.models.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class StoreManager implements Warehouse {
    private String id;
    private String name;
    private String address;
    private RegionEnum regionEnum;
    private List<Product> productList;

    public StoreManager(String id, String name, String address, RegionEnum regionEnum, List<Product> productList) {
        this.id = id;
        this.name = address;
        this.regionEnum = regionEnum;
        this.productList = productList != null ? productList : new ArrayList<>();
    }

    // Implement all find methods

    @Override
    public void CreateProduct() {

    }

    @Override
    public void ReadProduct() {

    }

    @Override
    public void UpdateProduct() {

    }

    @Override
    public void DeleteProduct() {

    }
}
