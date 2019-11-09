package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {


	/**
	 * waiting until it show (displayed and enabled)
	 * 
	 * @param driver
	 *            - web driver
	 * @param selector
	 *            - the element selector we are waiting for
	 * @param waitInterval
	 *            - the time the driver will wait for the element to appear in the
	 *            DOM tree
	 * @return WebElement selected element
	 */
	public static WebElement waitToBeClickable(WebDriver driver, By selector, int waitInterval) {
		WebElement element = (new WebDriverWait(driver, waitInterval))
				.until(ExpectedConditions.elementToBeClickable(selector));
		return element;
	}

	/**
	 * The method waits for the element with the forwarded selector to appear in the
	 * DOM tree
	 * 
	 * @param driver
	 *            - web driver
	 * @param selector
	 *            - element selector whose presence we are waiting for
	 * @param waitInterval
	 *            - the time the driver will wait for the element to appear in the
	 *            DOM tree
	 * @return WebElement selected element
	 */
	public static WebElement waitForElementPresence(WebDriver driver, By selector, int waitInterval) {
		WebElement element = (new WebDriverWait(driver, waitInterval))
				.until(ExpectedConditions.presenceOfElementLocated(selector));
		return element;
	}

	/**
	 * The method waits for the page title to become equal to the forwarded string
	 * 
	 * @param driver
	 * @param title
	 *            - the title we are waiting for
	 * @param waitInterval
	 *            - the time the driver will wait for the element to appear in the
	 *            DOM tree
	 */
	public static void waitForTitle(WebDriver driver, String title, int waitInterval) {
		(new WebDriverWait(driver, waitInterval)).until(ExpectedConditions.titleIs(title));
	}

}
