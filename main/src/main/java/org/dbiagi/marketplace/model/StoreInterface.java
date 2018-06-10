package org.dbiagi.marketplace.model;

import java.util.Collection;

public interface StoreInterface {
    String getEmail();

    void setEmail(String email);

    String getCnpj();

    void setCnpj(String cnpj);

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

    int getMaxAttentandsCount();

    void setMaxAttentandsCount(int maxAttendantsCount);

    Type getType();

    void setType(Type type);

    Collection<? extends ListingInterface> getListings();

    void addListing(ListingInterface listing);

    enum Type {
        STORE,
        RESELLER
    }
}
