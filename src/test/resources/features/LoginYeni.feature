@LoginYeniTest
Feature: Login

  Scenario: Kullancinin basarilir bir sekilde sisteme giris yapabilmasi

    Given Uygulamanin cache ini temizle
    And "data@anasayfa login button" tiklanir
    And "data@username" icerisine "sutravetre@biyac.com" verisi girilir
    And "data@password" icerisine "Test*1234" verisi girilir
    And Giris yapmak icin Login butonuna tiklanir


