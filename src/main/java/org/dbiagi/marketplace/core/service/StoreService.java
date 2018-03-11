package org.dbiagi.marketplace.core.service;

import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class StoreService {

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

    public Store update(Long id, HashMap<String, Object> fields) throws ResourceNotFoundException {

        Store store = storeRepository.findOne(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

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
                    if(value != null)
                        store.setType(Store.StoreTypeEnum.valueOf((String) value));
            }
        });

        return storeRepository.save(store);
    }

    public List<Store> findAll() {
        return storeRepository.findAllByNameNotNullOrderByName();
    }

    public Store find(Long id) throws ResourceNotFoundException {
        Store store = storeRepository.findOne(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

        return store;
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Store store = storeRepository.findOne(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

        storeRepository.delete(id);
    }


}
