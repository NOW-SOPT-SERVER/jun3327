package org.example;

import org.example.Menu.MenuService;

public class Main {

    public static void main(String[] args) {

        while(true){
            Configuration configuration = new Configuration();
            MenuService menu = configuration.menuService();
            String customerId = menu.beforeLogin();
            menu.afterLogin(customerId);
        }
    }

}