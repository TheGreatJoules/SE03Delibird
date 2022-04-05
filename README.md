# SE03Delibird
## Advance Software Engineering

---
## Overview

## Deployment
This project was created using maven 3.6.3

* Compile the project 
  * `mvn compile`
* Run all unit tests
  * `mvn test`
* Run a specific unit test classes
  * `mvn test -Dtest=Test1`
* Run a single test method from a test class
  * `mvn test -Dtest=Test1#methodname`
* Run multiple test classes
  * `mvn test -Dtest=Test1,Test2 `


## Implementation

### Models
* Product
  * Stereo
  * Television
* Person
  * Owner 
  * Customer
  * Supplier
  * SalesPerson
* Warehouse
* Inventory
* Invoice
* Order

### Components
* InventoryManager
* InvoiceManager
* WarehouseManager

### Controllers
* OwnerController

### Utils
* GenerateUtils
* LoadUtils