package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.component.RegistrationMailer;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.repository.StoreRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.domain.PageRequest.of;

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

    public void update(Long id, Store updatedStore) throws ResourceNotFoundException, EntityValidationException {
        Store store = find(id);

        store.setEmail(updatedStore.getEmail());
        store.setName(updatedStore.getName());
        store.setCellphone(updatedStore.getCellphone());
        store.setPhone(updatedStore.getPhone());
        store.setAddress(updatedStore.getAddress());
        store.setNeighborhood(updatedStore.getNeighborhood());
        store.setZipCode(updatedStore.getZipCode());
        store.setNumber(updatedStore.getNumber());
        store.setWebsite(updatedStore.getWebsite());
        store.setType(updatedStore.getType());

        validate(store, null);

        storeRepository.save(store);
    }

    public List<Store> findAll(Pageable pageable) {
        return storeRepository.findAllByNameNotNullOrderByName(pageable);
    }

    public Store find(Long id) throws ResourceNotFoundException {
        Optional<Store> optional = storeRepository.findById(id);

        if (!optional.isPresent()) {
            throw new ResourceNotFoundException(id);
        }

        return optional.get();
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Optional<Store> optional = storeRepository.findById(id);

        if (!optional.isPresent()) {
            throw new ResourceNotFoundException(id);
        }

        storeRepository.deleteById(id);
    }
}
