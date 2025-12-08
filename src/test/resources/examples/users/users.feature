Feature: Test Product API - CRUD Operations (CI + Load Safe)

Background:
  * url baseUrl
  * header Content-Type = 'application/json'

  # ✅ Detect load mode
  * def isLoad = karate.properties['load'] == 'true'
  * karate.log(isLoad ? '⚡ Load mode: per-user product' : '🧪 CI mode: shared product')
  * def productSetup =
  """
  (function() {
    if (isLoad) {
      return karate.call('create-product.feature');
    } else {
      return karate.callSingle('create-product.feature'); // <-- CORRECTED
    }
  })()
  """

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
