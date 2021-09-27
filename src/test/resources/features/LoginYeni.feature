@LoginYeniTest
Feature: Login

  Scenario: Kullancinin basarilir bir sekilde sisteme giris yapabilmasi

    Given Uygulamanin cache ini temizle
    When Login butonuna tikla
    And username icerisine sutravetre@biyac.com verisi girilir
    And password icerisine Test*1234 verisi girilir
    And Giris yapmak icin Login butonuna tiklanir


