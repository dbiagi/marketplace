package org.dbiagi.marketplace.model;

public interface StoreInterface {
    String getEmail();

    void setEmail(String email);

    String getName();

    void setName(String name);

    String getAddress();

    void setAddress(String address);

    String getNeighborhood();

    void setNeighborhood(String neighborhood);

    String getNumber();

    void setNumber(String number);

    String getZipCode();

    void setZipCode(String zipCode);

    String getWebsite();

    void setWebsite(String website);

    String getPhone();

    void setPhone(String phone);

    String getCellphone();

    void setCellphone(String cellphone);

    Type getType();

    void setType(Type type);

    enum Type {
        STORE,
        RESELLER
    }
}
