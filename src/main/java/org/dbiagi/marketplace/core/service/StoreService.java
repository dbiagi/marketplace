package org.dbiagi.marketplace.core.service;

import org.dbiagi.marketplace.core.entity.BaseEntity;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreService implements EntityService {

    private StoreRepository storeRepository;

    private UserService userService;

    private RegistrationMailer registrationMailer;

    @Autowired
    public StoreService(StoreRepository storeRepository, UserService userService, RegistrationMailer registrationMailer) {
        this.storeRepository = storeRepository;
        this.userService = userService;
        this.registrationMailer = registrationMailer;
    }

    public Store save(Store store) {
        storeRepository.save(store);

        store.getUsers().forEach(userService::save);

        return store;
    }

    public Store update(Long id, HashMap<String, Object> fields) {
        Store store = storeRepository.findOne(id);

        fields.forEach((key, value) -> {
            switch (key) {
                case "email":
                    store.setEmail((String) value);
                    break;
                case "cnpj":
                    store.setCnpj((String) value);
                    break;
                case "name":
                    store.setName((String) value);
                    break;
                case "address":
                    store.setAddress((String) value);
                    break;
                case "neighborhood":
                    store.setNeighborhood((String) value);
                    break;
                case "number":
                    store.setNumber((String) value);
                    break;
                case "zipCode":
                    store.setZipCode((String) value);
                    break;
                case "website":
                    store.setWebsite((String) value);
                    break;
                case "phone":
                    store.setPhone((String) value);
                    break;
                case "cellphone":
                    store.setCellphone((String) value);
                    break;
                case "type":
                    store.setType(Store.StoreTypeEnum.valueOf((String) value));
            }
        });

        return storeRepository.save(store);
    }

    public List<Store> findAll() {
        return storeRepository.findAllByNameNotNullOrderByName();
    }

    public Store find(Long id) {
        return storeRepository.findOne(id);
    }

    @Override
    public BaseEntity save(BaseEntity entity) {
        return null;
    }

    @Override
    public void delete(BaseEntity entity) {

    }

    public void delete(Store store) {
        storeRepository.delete(store);
    }

    public void delete(Long id) {
        storeRepository.delete(id);
    }

    @Override
    public BaseEntity update(Long id, Map<String, Object> values) {
        return null;
    }


}
