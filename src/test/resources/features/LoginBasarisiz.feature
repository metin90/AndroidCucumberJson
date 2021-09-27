@LoginBasarisizTest
Feature: Login Basarisiz

  Scenario: Kullancinin login olurken hata almasi

    Given Uygulamanin cache ini temizle
    When Login butonuna tikla
    And "sutravetre@biyac.com" kullanici adi girilir
    And "Test*1234" kullanici sifre girilir
    And Giris yapmak icin Login butonuna tiklanir


