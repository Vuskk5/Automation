package org.selophane.elements.widget;

import org.openqa.selenium.WebElement;
import org.selophane.elements.base.Element;
import org.selophane.elements.base.ImplementedBy;

import java.util.List;

/**
 * Table functionality.
 */
@ImplementedBy(TableImpl.class)
public interface Table extends Element {

	/**
     * Gets the number of rows in the table
     * @return int equal to the number of rows in the table
     */
    int getRowCount();

    /**
     * Gets the number of columns in the table
     * @return int equal to the number of rows in the table
     */
    int getColumnCount();

    /**
     * Gets all cells containing 'text'
     * @param text The text to find
     * @return A list populated by all cells
     */
    List<Element> findCellsWith(String text);

    /**
     * Gets all rows containing 'text'
     * @param text The text to find
     * @return A list populated by all cells
     */
    List<Element> findRowsWith(String text);

    /**
     * Gets the first cell containing 'text'
     * @param text The text to find
     * @return The first matching element
     */
    Element findCellWith(String text);

    /**
     * Gets the first row containing 'text'
     * @param text The text to find
     * @return The first matching element
     */
    Element findRowWith(String text);

    /**
     * Gets the WebElement of the cell at the specified index
     * @param rowIdx The zero based index of the row
     * @param colIdx The zero based index of the column
     * @return the WebElement of the cell at the specified index
     */
    WebElement getCellAtIndex(int rowIdx, int colIdx); 
}
