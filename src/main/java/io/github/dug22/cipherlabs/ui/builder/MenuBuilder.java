package io.github.dug22.cipherlabs.ui.builder;

import io.github.dug22.cipherlabs.ui.task.CipherLabsRunnable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MenuBuilder {

    public static class Builder {

        private Menu menu;


        public Builder createMenu(String menuTitle) {
            this.menu = new Menu(menuTitle);
            return this;
        }

        public Builder addSubMenu(Menu subMenu) {
            menu.getItems().add(subMenu);
            return this;
        }

        public Builder addMenuItem(String itemName, CipherLabsRunnable task) {
            MenuItem menuItem = new MenuItem(itemName);
            menuItem.setOnAction((_) -> task.run());
            menu.getItems().add(menuItem);
            return this;
        }

        public Menu build() {
            return menu;
        }
    }
}
