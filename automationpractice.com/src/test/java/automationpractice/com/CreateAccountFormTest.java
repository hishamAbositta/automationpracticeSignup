package automationpractice.com;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import automationpractice.com.pageObject.Account;
import automationpractice.com.pageObject.CreateAccount;
import automationpractice.com.pageObject.CreateAccountForm;
import automationpractice.com.pageObject.Homepage;
import utils.EmailsGenerator;
import utils.ExelDataProvider;

public class CreateAccountFormTest {

	private WebDriver driver;
	private ExelDataProvider excel;

	private Homepage homepage;
	private CreateAccount createAccount;
	private CreateAccountForm createAccountForm;
	
	private Account account;

	@BeforeClass
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
		driver = new ChromeDriver();

		homepage = new Homepage(driver);
		createAccount = new CreateAccount(driver);
		createAccountForm = new CreateAccountForm(driver);
		excel= new ExelDataProvider();
		
		account = new Account(driver);

		String baseUrl = "http://automationpractice.com/index.php";
		driver.manage().window().maximize();
		driver.get(baseUrl);
	}

	@AfterClass
	public void closeAll() {
		account.getAccountLogout().click();
		driver.quit();
	}

	@Test(priority = 1)
	public void authenticationPage() {
		homepage.getSignInBtn().click();

		Assert.assertTrue(createAccount.getCreateAccountForm().isDisplayed());
		Assert.assertTrue(createAccount.getCreatAccountEmailField().isDisplayed());
		Assert.assertTrue(createAccount.getCreateAccountBtn().isDisplayed());
		
	}

	@Test(priority = 2)
	public void authenticationPageEmailField() {
		
		// Without email
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailErrorMessage().isDisplayed());

		// Wrong email format (hisham89ct, hisham89ct@gmail ...)
		createAccount.setCreateAccountEmailField(excel.getStringData("sheet1", 0, 0));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailErrorMessage().isDisplayed());
		Assert.assertTrue(createAccount.getEmailFieldHighlightedRed().isDisplayed());

		// Registered email
		createAccount.setCreateAccountEmailField(excel.getStringData("sheet1", 1, 0));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccount.getEmailBeenRegistered().isDisplayed());

		// Correct email
		createAccount.setCreateAccountEmailField(excel.getStringData("sheet1", 2, 0));
		createAccount.getCreateAccountBtn().click();

		Assert.assertTrue(createAccountForm.getAccountCreationForm().isDisplayed());
	}

	@Test(priority = 3)
	public void personalInfoFields() {
		// With values
		createAccountForm.setCustomerFirstNameField(excel.getStringData("sheet1", 0, 0));
		createAccountForm.setCustomerLastNameField(excel.getStringData("sheet1", 3, 0));
		createAccountForm.setCustomerEmailField(excel.getStringData("sheet1", 4, 0));
		createAccountForm.setCustomerPasswordField(excel.getStringData("sheet1", 5, 0));

		createAccountForm.getAccountCreationForm().click();

		Assert.assertTrue(createAccountForm.getFirstNameHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailHighlightedGreen().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordHighlightedGreen().isDisplayed());

		// Without values
		createAccountForm.setCustomerFirstNameField("");
		createAccountForm.setCustomerLastNameField("");
		createAccountForm.setCustomerEmailField("");
		createAccountForm.setCustomerPasswordField("");

		createAccountForm.getAccountCreationForm().click();

		Assert.assertTrue(createAccountForm.getFirstNameHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailHighlightedRed().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordHighlightedRed().isDisplayed());
	}

	@Test(priority = 4)
	public void requiredFieldsEmpty() {
		createAccountForm.getAddressAliasField().clear();
		createAccountForm.setCustomerEmailField("");
		createAccountForm.selectCountry("-");
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getPhoneNumberError().isDisplayed());
		Assert.assertTrue(createAccountForm.getLastNameError().isDisplayed());
		Assert.assertTrue(createAccountForm.getFirstNameError().isDisplayed());
		Assert.assertTrue(createAccountForm.getEmailRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCountryRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getAddressRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getAddressAliasRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCityRequiredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getCountryUnselectedError().isDisplayed());

		createAccountForm.selectCountry(excel.getStringData("sheet1", 6, 0));
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getStateRequredError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
	}

	@Test(priority = 5)
	public void requiredFieldsInputFormat() throws Exception {
		// Wrong format
		createAccountForm.setCustomerEmailField(excel.getStringData("sheet1", 4, 0));
		createAccountForm.setCustomerPasswordField(excel.getStringData("sheet1", 11, 0));
		createAccountForm.setPostalCodeField(excel.getStringData("sheet1", 8, 0));
		createAccountForm.setHomePhoneField(excel.getStringData("sheet1", 9, 0));
		createAccountForm.setMobilePhoneField(excel.getStringData("sheet1", 10, 0));

		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getEmailInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
		Assert.assertTrue(createAccountForm.getHomePhoneInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getMobilePhoneInvalidError().isDisplayed());

		// Correct format
		createAccountForm.setCustomerEmailField(excel.getStringData("sheet1", 2, 0));
		createAccountForm.setCustomerPasswordField(excel.getStringData("sheet1", 5, 0));
		createAccountForm.setPostalCodeField(excel.getStringData("sheet1", 12, 0));
		createAccountForm.setHomePhoneField(excel.getStringData("sheet1", 13, 0));
		createAccountForm.setMobilePhoneField(excel.getStringData("sheet1", 14, 0));

		Assert.assertTrue(createAccountForm.getEmailInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPasswordInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getPostalCodeError().isDisplayed());
		Assert.assertTrue(createAccountForm.getHomePhoneInvalidError().isDisplayed());
		Assert.assertTrue(createAccountForm.getMobilePhoneInvalidError().isDisplayed());
	}

	@Test(priority = 6)
	public void createAccountSuccessfully() {
		// Required fields filled
		createAccountForm.setCustomerFirstNameField(excel.getStringData("sheet1", 0, 0));
		createAccountForm.setCustomerLastNameField(excel.getStringData("sheet1", 3, 0));
		createAccountForm.setCustomerPasswordField(excel.getStringData("sheet1", 5, 0));
		createAccountForm.selectCustomerDateOfBirthDay(excel.getIntData("sheet1", 15, 0));
		createAccountForm.selectCustomerDateOfBirthMonth(excel.getIntData("sheet1", 16, 0));
		createAccountForm.selectCustomerDateOfBirthYear("2000");
		createAccountForm.setAddressField(excel.getStringData("sheet1", 18, 0));
		createAccountForm.setCityField(excel.getStringData("sheet1", 19, 0));
		createAccountForm.selectState(excel.getIntData("sheet1", 20, 0));
		createAccountForm.setPostalCodeField("21000");
		createAccountForm.setHomePhoneField("56");
		createAccountForm.setMobilePhoneField("55");
		createAccountForm.setAddressAliasField(excel.getStringData("sheet1", 21, 0));
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.getEmailBeenRegistered().isDisplayed());

		createAccountForm.setCustomerEmailField(EmailsGenerator.getNextEmail());
		createAccountForm.setCustomerPasswordField("tester123");
		createAccountForm.getRegisterBtn().click();

		Assert.assertTrue(createAccountForm.successfullyCreatedAccount().isDisplayed());
	}
}
