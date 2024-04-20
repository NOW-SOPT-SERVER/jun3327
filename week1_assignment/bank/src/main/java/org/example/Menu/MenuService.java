package org.example.Menu;

public interface MenuService {
    String beforeLogin();
    void afterLogin(String customerId);
}
