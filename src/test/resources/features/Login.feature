@LoginTest
Feature: Login

  Scenario: Kullancinin basarilir bir sekilde sisteme giris yapabilmasi

    Given Uygulamanin cache ini temizle
    When Login butonuna tikla
    And "sutravetre@biyac.com" kullanici adi girilir
    And "Test*1234" kullanici sifre girilir
    And Giris yapmak icin Login butonuna tiklanir


