package org.selophane.elements.widget;

import java.util.ArrayList;
import java.util.List;

import net.bsmch.support.ElementFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ElementImpl;

/**
 * Table wrapper.
 */
public class TableImpl extends ElementImpl implements Table {
	/**
	 * Creates a Table for a given WebElement.
	 * 
	 * @param element
	 *            element to wrap up
	 */
	public TableImpl(WebElement element) {
		super(element);
	}

	@Override
	public int getRowCount() {
		return getRows().size();
	}

	@Override
	public int getColumnCount() {

		return findElement(By.cssSelector("tr")).findElements(
				By.cssSelector("*")).size();
		// Would ideally do:
		// return findElements(By.cssSelector("tr:first-of-type > *"));
		// however HTMLUnitDriver does not support CSS3
	}

	@Override
	public List<Element> findCellsWith(String text) {
		final By locator = By.xpath("descendant::td[contains(text(), \"" + text + "\")]");
		return ElementFinder.proxyAll(getWrappedElement(), locator);
	}

	@Override
	public Element findCellWith(String text) {
		return findCellsWith(text).get(0);
	}

	@Override
	public List<Element> findRowsWith(String text) {
		final By locator = By.xpath("descendant::tr[contains(string(), \"" + text + "\")]");
		return ElementFinder.proxyAll(getWrappedElement(), locator);
	}

	@Override
	public Element findRowWith(String text) {
		return findRowsWith(text).get(0);
	}

	@Override
	public WebElement getCellAtIndex(int rowIndex, int colIndex) {
		// Get the row at the specified index
		WebElement row = getRows().get(rowIndex);

		List<WebElement> cells;

		// Cells are most likely to be td tags
		if ((cells = row.findElements(By.tagName("td"))).size() > 0) {
			return cells.get(colIndex);
		}
		// Failing that try th tags
		else if ((cells = row.findElements(By.tagName("th"))).size() > 0) {
			return cells.get(colIndex);
		} else {
			final String error = String
					.format("Could not find cell at row: %s column: %s",
							rowIndex, colIndex);
			throw new RuntimeException(error);
		}
	}

	/**
	 * Gets all rows in the table in order header > body > footer
	 * 
	 * @return list of row WebElements
	 */
	private List<WebElement> getRows() {
		List<WebElement> rows = new ArrayList<>();
		
		// Header rows
		rows.addAll(findElements(By.cssSelector("thead tr")));
		
		// Body rows
		rows.addAll(findElements(By.cssSelector("tbody tr")));
		
		// Footer rows
		rows.addAll(findElements(By.cssSelector("tfoot tr")));

		return rows;
	}

}
