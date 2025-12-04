Feature: Create product helper

Scenario:
  * def product = { name: 'Laptop', price: 1200.50 }

  Given url baseUrl
  And path '/api/products'
  And header Content-Type = 'application/json'
  And request product
  When method post
  Then status 200

  * def productId = response.id
  * karate.set('productId', productId)
