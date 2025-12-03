Feature: Test Product API - CRUD Operations

  Background:
    * url 'http://localhost:8080/api/products'
    * header Content-Type = 'application/json'
    * def productSetup = callonce read('create-product.feature')
    * def productId = productSetup.productId

  Scenario: Get all products
    Given method get
    Then status 200
    And match response[*].id contains productId

  Scenario: Update product price
    * def updatedProduct = { name: 'Laptop', price: 1500.75 }
    Given path productId
    And request updatedProduct
    When method put
    Then status 200
    And match response.price == 1500.75

  Scenario: Verify updated product
    Given path productId
    When method get
    Then status 200
    And match response.price == 1500.75
    And match response.name == 'Laptop'
