package practice.independentTestingSuite.pages.components.navigation;

import net.bsmch.components.PageComponent;
import net.bsmch.findby.Find;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class NavigationBar extends PageComponent {
    @Find(className = "has-drop")
    private List<DropdownMenu> menus;

    public DropdownMenu hoverOnMenu(String menuText) {
        DropdownMenu menu = getMenuByText(menuText);
        new Actions(getDriver()).moveToElement(menu.getContext()).build().perform();

        return menu;
    }

    private DropdownMenu getMenuByText(String menuText) {
        for (DropdownMenu menu : menus) {
            if (menu.getMenuName().toLowerCase().equals(menuText.toLowerCase())) {
                return menu;
            }
        }
        return null;
    }
}
