package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.component.RegistrationMailer;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.repository.StoreRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Service
public class StoreService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    private StoreRepository storeRepository;
    private UserService userService;
    private RegistrationMailer registrationMailer;
    private Validator validator;

    @Autowired
    public StoreService(StoreRepository storeRepository, UserService userService, Validator validator) {
        this.storeRepository = storeRepository;
        this.userService = userService;
        this.validator = validator;
    }

    private void validate(Store store, Class<?> group) throws EntityValidationException {
        Set<ConstraintViolation<Store>> violations = group == null ? validator.validate(store) : validator.validate(store, group);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<Store>().create(violations);
        }
    }

    public Store save(Store store, Class<?> validationGroup) throws EntityValidationException {
        validate(store, validationGroup);

        return save(store);
    }

    public Store save(Store store) throws EntityValidationException {
        validate(store, null);

        storeRepository.save(store);

        store.getUsers().forEach(u -> {
            try {
                userService.save(u);
            } catch (EntityValidationException e) {
                logger.error("Error saving store user", e, u);
            }
        });

        return store;
    }

    public Store update(Long id, HashMap<String, Object> fields) throws ResourceNotFoundException, EntityValidationException {

        Store store = storeRepository.findOne(id);

        if (store == null) {
            throw new ResourceNotFoundException(id);
        }

        fields.forEach((key, value) -> {
            switch (key) {
                case "email":
                    store.setEmail((String) value);
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
                    if (value != null)
                        store.setType(Store.Type.valueOf((String) value));
            }
        });

        validate(store, null);


        return storeRepository.save(store);
    }

    public List<Store> findAll() {
        return findAll(new PageRequest(1, 10));
    }

    public List<Store> findAll(Pageable pageable) {
        return storeRepository.findAllByNameNotNullOrderByName(pageable);
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

    public void delete(Store store) throws ResourceNotFoundException {
        delete(store.getId());
    }
}
