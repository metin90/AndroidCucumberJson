@LoginYeniTest
Feature: Login

  Scenario: Kullancinin basarilir bir sekilde sisteme giris yapabilmasi

    Given Uygulamanin cache ini temizle
    And "anasayfa login button" tiklanir
    And username icerisine sutravetre@biyac.com verisi girilir
    And password icerisine Test*1234 verisi girilir
    And Giris yapmak icin Login butonuna tiklanir


