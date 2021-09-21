@SearchProducts
Feature: Como usuario de los videojuegos y amante de la tecnología
	Deseo realizar la búsqueda de distintos productos y obtener la información de estos
	Para Poder analizarla y así ir tomando decisiones de que cosas debo comprar

  Scenario: Navegar al sitio
    Given Ingreso al sitio web de zmart
    Then Verifico que aparezca el logo
    
  @End
  Scenario: Realizar la búsqueda de los productos deseados
  	Given Se realiza la busqueda de cada uno de dichos productos capturando los datos requeridos
  	When Se revisa que se encuentren los productos deseados
  	Then Se registran los datos capturados