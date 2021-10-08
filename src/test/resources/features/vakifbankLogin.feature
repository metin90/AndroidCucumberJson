@LoginIOS
Feature: Login

  Scenario: Kullancinin basarilir bir sekilde sisteme giris yapabilmasi

    * "login@notificationPermission" butonunu en fazla "2" sn bekle, var ise tikla
    * "login@developmentSDKLibraryMessage" butonunu en fazla "2" sn bekle, var ise tikla
    * "login@skipSlide" butonunu en fazla "2" sn bekle, var ise tikla
    * "login@locationPermisson" butonunu en fazla "2" sn bekle, var ise tikla
    * "login@username" icerisine "445012377841" verisi girilir
    * "login@password" icerisine "qq112233" verisi girilir
    * "login@DevamButton" tiklanir


