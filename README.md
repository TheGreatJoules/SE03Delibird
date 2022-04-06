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
  * Electronics
    * The products available with their respected advertising information
* Person
  * Owner
    * The root owner of the project entirety
  * Customer
    * The person who request sales
  * Supplier
    * The person owner request supplies
  * SalesPerson
    * The person who assists customer with product purchases
* Warehouse
  * The warehouse contains its own respected list of products available
* Inventory
  * The inventory holds all data of the entire warehouses
* Invoice
  * The invoice is a collective dataset that holds information about the entire sale.
* Order
  * The order is a singular dataset that pertains to a product and its respective parties.

### Components
* InventoryManager
  * 
* InvoiceManager
  * 
* WarehouseManager
  * 

### Controllers
* OwnerController
  * The controller that orchestrates and manages all transactions.

### Utils
* IdentifierUtils
* ExpenseUtils
* LoadUtils