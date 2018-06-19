package org.dbiagi.marketplace.model;

public interface UserInterface {
    String getName();

    void setName(String name);

    UserInterface.Role getRole();

    void setRole(UserInterface.Role role);

    String getEmail();

    void setEmail(String email);

    String getPhone();

    void setPhone(String phone);

    String getCellphone();

    void setCellphone(String cellphone);

    boolean isConnected();

    void setConnected(boolean connected);

    StoreInterface getStore();

    void setStore(StoreInterface store);

    enum Role {
        SUPER_ADMIN,
        ADMIN,
        STORE_OWNER,
        STORE_ATTENDANT
    }
}
