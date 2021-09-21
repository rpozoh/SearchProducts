package Home;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.Assert;

import com.opencsv.CSVWriter;

import DTO.ProductDTO;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.Constants;
import utils.Endpoints;
import utils.Files;
import utils.WD;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class HomeSteps {
	
	static WebDriver driver;
	static Wait<WebDriver> wait;
	List<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
	Integer fileContentLength = 0;
	
	public HomeSteps() {
		driver = WD.driver;
		if(driver == null) {
			driver = WD.setupDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = new FluentWait<WebDriver>(driver)
				.withTimeout(30, TimeUnit.SECONDS)
				.pollingEvery(2, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
	}

	/**
	 * Método que inicia el escenario ingresando al sitio de zmart
	 * Se usa la etiqueta Given de gherkin que indica el inicio de la estructura gherkin
	 */
	@Given("^Ingreso al sitio web de zmart$")
	public void ingreso_al_sitio_web_de_zmart() {
		System.out.println("HomeSteps.ingreso_al_sitio_web_de_zmart: INICIO");
		driver.get(Endpoints.HOME_ZMART);
		driver.manage().window().maximize();
		System.out.println("HomeSteps.ingreso_al_sitio_web_de_zmart: FIN");
	}
	
	/**
	 * Metódo para verificar que aparezca el logo de zmart
	 * Se usa la etiqueta Then que según la estructura de gherkin es la final y es usada para la verificación del escenario
	 */
	@Then("^Verifico que aparezca el logo$")
	public void verifico_que_aparezca_el_logo() {
		System.out.println("HomeSteps.verifico_que_aparezca_el_logo: INICIO");
		Assert.assertTrue(HomeObjects.imgZmartLogo(wait).isDisplayed());
		System.out.println("HomeSteps.verifico_que_aparezca_el_logo: FIN");
	}
	
	/**
	 * Método para comenzar a leer la lista de los productos que se desean buscar
	 * Luego realizar la búsqueda de dichos productos y capturar su información
	 * Para este caso se usa un nuevo escenario y por ello comienza con la etiqueta Given
	 * En el archivo los nombres de los productos van separados por ; en una sola línea
	 */
	@Given("^Se realiza la busqueda de cada uno de dichos productos capturando los datos requeridos$")
	public void se_realiza_la_busqueda_de_cada_uno_de_dichos_productos_capturando_los_datos_requeridos() {
		System.out.println("HomeSteps.se_recorre_la_lista_de_los_productos_deseados: INICIO");
		String fileContent = getInputFileContent();
		String[] productsName = separateInputFileContent(fileContent);
		fileContentLength = productsName.length;
		searchProducts(productsName);
		System.out.println("HomeSteps.se_recorre_la_lista_de_los_productos_deseados: FIN");
	}
	
	/**
	 * Método para verificar si se encontraron o no todos los productos
	 */
	@When("^Se revisa que se encuentren los productos deseados$")
	public void se_revisa_que_se_encuentren_los_productos_deseados() {
		System.out.println("HomeSteps.se_revisa_que_se_encuentren_los_productos_desea: INICIO");
		if(fileContentLength == productsDTO.size()) {
			System.out.println("Se encontraron todos los productos");
		} else {			
			System.out.println("Algunos de los productos no se encontraron, se debe revisar");
		}
		System.out.println("HomeSteps.se_revisa_que_se_encuentren_los_productos_deseados: FIN");
	}
	
	/**
	 * Método para tomar construir la estructura de los datos capturados
	 * Se escribe el archivo con la estructura constuida
	 */
	@Then("^Se registran los datos capturados$")
	public void se_registran_los_datos_capturados() {
		System.out.println("HomeSteps.se_registran_los_datos_capturados: INICIO");
		File file = Files.createFile("output_");
		generateCSV(file);
		System.out.println("HomeSteps.se_registran_los_datos_capturados: FIN");
	}
	
	/**
	 * Método para leer el archivo de entrada
	 * @return products: String que captura el contenido del archivo
	 */
	public String getInputFileContent() {
		BufferedReader br = null;
		String products = "";
		try {
			br = new BufferedReader(new FileReader(Files.getInputFile(Constants.INPUT_FILE)));
			products = br.readLine();
		} catch(IOException ioex) {
			System.out.println("Error en la lectura del archivo " + ioex.getCause());
		}
		return products;
	}
	
	/**
	 * Metódo para separar el contenido del archivo de entrada
	 * @param fileContent: Se entrega el contenido del archivo que se va a separar
	 * @return separatedFileContent: Arreglo con los nombres de los productos
	 */
	public String[] separateInputFileContent(String fileContent) {
		String[] separatedFileContent = fileContent.split(Constants.CSV_SEPARATOR);
		return separatedFileContent;
	}
	
	/**
	 * Método para realizar la búsqueda en el sitio con cada uno de los nombres de los productos
	 * @param productsName: se le entrega el arreglo con todos los nombres
	 * Se va a recorrer el arreglo para buscar cada producto
	 */
	public void searchProducts(String[] productsName) {
		for(String product : productsName) {
			HomeObjects.inputSearch(wait).sendKeys(product);
			HomeObjects.buttonSearch(wait).click();
			if(!HomeObjects.divNotFoundProducts(wait).getText().equalsIgnoreCase(Constants.NOT_PRODUCTS_FOUND)) {				
				HomeObjects.aFirstProductDetails(wait).click();
				captureData();
			}
		}
	}
	
	/**
	 * Método para capturar la información requerida de cada uno de los productos
	 * Dicha información se guarda en un objeto ProductDTO para ser utilizada posteriormente
	 */
	public void captureData() {
		ProductDTO productDTO = new ProductDTO();
		productDTO.setName(HomeObjects.hProductName(wait).getText());
		productDTO.setSku(HomeObjects.spanSku(wait).getText());
		if(!verifyAvailability()) {
			productDTO.setStoreAvailability(false);
			productDTO.setWebAvailability(false);
		} else {
			HomeObjects.divCheckStock(wait).click();
			utils.WaitElements.waitForEnabledElement(wait, HomeObjects.tdWebStock(wait));
			productDTO.setStoreAvailability(verifyStoreAvailability());
			productDTO.setWebAvailability(verifyWebAvailability());
		}
		productsDTO.add(productDTO);
	}
	
	/**
	 * Método para verificar si este disponibilidad del producto búscado
	 * @return Boolean: devuelve un boolean que representa la disponibilidad del producto
	 */
	public Boolean verifyAvailability() {
		if(HomeObjects.divProductAvailable(wait).getText().trim().contains(Constants.PRODUCT_AVAILABLE)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Método para revisar si el producto está disponible para la compra en tiendas
	 * Para este caso se definio el campo SANTIAGO CENTRO como compra en tienda
	 * @return Boolean: devuelve un boolean que representa la disponibilidad del producto para compra en tienda
	 */
	public Boolean verifyStoreAvailability() {
		if(HomeObjects.tdStoreStock(wait, Constants.STORE).getText().equalsIgnoreCase(Constants.AVAILABILITY)) { 
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Método para revisar si el producto está disponible para la compra por internet
	 * Para este caso se definio el campo A DOMICILIO como compra web
	 * @return Boolean: devuelve un boolean que representa la disponibilidad del producto para compra vía web
	 */
	public Boolean verifyWebAvailability() {
		if(HomeObjects.tdWebStock(wait).getText().equalsIgnoreCase(Constants.AVAILABILITY)) { 
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Método para generar el csv con la información de los productos
	 * @param file: Nombre y ruta del archivo que se va a generar
	 */
	public void generateCSV(File file) {
		try {			
			FileWriter fileWriter = new FileWriter(file);
			CSVWriter writer = new CSVWriter(fileWriter, ';',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END) ;
			writer.writeAll(generateData());
			writer.close();
		} catch (IOException ioex) {
			System.out.println("Error en la generación del archivo " + ioex.getCause());
		}
	}
	
	/**
	 * Método para tomar el objeto construido en la búsqueda de los productos y guardarlo para enviarlos al archivo
	 * @return data: Se devuelve la estructura para escribir el archivo
	 */
	public List<String[]> generateData() {
		List<String[]> data = new ArrayList<String[]>();
		data.add(new String[] {"Nombre Producto","SKU","Disponibilidad en Tienda","Disponibilidad Web"});
		for(ProductDTO product : productsDTO) {
			data.add(new String[] {StringUtils.stripAccents(product.getName()),product.getSku(),product.getStoreAvailability().toString(),product.getWebAvailability().toString()});
		}
		return data;
	}
}
