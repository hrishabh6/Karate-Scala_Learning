Feature: Test Product API - CRUD Operations

Background:
  * url baseUrl
  * header Content-Type = 'application/json'
  * def productSetup = callonce read('create-product.feature')
  * def productId = productSetup.productId

Scenario: Get all products
  Given path '/api/products'
  When method get
  Then status 200
  And match response[*].id contains productId

Scenario: Update product price
  * def updatedProduct = { name: 'Laptop', price: 1500.75 }

  Given path '/api/products', productId
  And request updatedProduct
  When method put
  Then status 200
  And match response.price == 1500.75

Scenario: Verify updated product
  Given path '/api/products', productId
  When method get
  Then status 200
  And match response.price == 1500.75
  And match response.name == 'Laptop'
