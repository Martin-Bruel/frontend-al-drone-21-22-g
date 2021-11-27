Feature: launch delivery
  Ce scénario met en oeuvre le lancement d'une livraison

 Scenario: Assignations drones-colis et livraison
    Given un conducteur, 3 drones, 3 colis et sa tablette
    When  le conducteur demande les assignations
    Then il y a 3 assignations
    When le conducteur lance le drone avec son colis
    Then le drone part effectuer sa livraison
    Then  le colis est livré
    When  le conducteur demande les assignations
    Then il y a 2 assignations


  Scenario: Livraison du colis malgré déconnexion
    Given un conducteur, 3 drones, 3 colis et sa tablette
    When  le conducteur demande les assignations
    And le conducteur lance le drone avec son colis
    Then le camion perd la connexion avec le drone
    And le drone n'est pas localisable
    When le camion retrouve la connexion avec le drone
    Then  le colis est livré


