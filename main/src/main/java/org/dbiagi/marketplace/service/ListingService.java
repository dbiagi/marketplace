package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.repository.ListingCategoryRepository;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Component
public class ListingService {
    private ListingRepository repository;

    private ListingCategoryRepository categoryRepository;

    private Validator validator;

    private EntityManager entityManager;

    @Autowired
    public ListingService(ListingRepository repository, ListingCategoryRepository categoryRepository, Validator validator, EntityManager entityManager) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.validator = validator;
        this.entityManager = entityManager;
    }

    public Listing save(Listing listing) throws EntityValidationException {
        Set<ConstraintViolation<Listing>> violations = validator.validate(listing);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<Listing>().create(violations);
        }

        return repository.save(listing);
    }

    public Page<Listing> findAll(int page, int size) {
        return repository.findAll(new PageRequest(page, size));
    }

    public Listing find(long id) {
        return repository.findOne(id);
    }

    public Category save(Category category) throws EntityValidationException {
        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<Category>().create(violations);
        }

        return categoryRepository.save(category);
    }

    public Page<Category> getCategories(int page, int size) {
        return categoryRepository.findAll(new PageRequest(page, size));
    }

    public void update(Long id, HashMap<String, Object> fields) throws ResourceNotFoundException, EntityValidationException {
        Listing listing = find(id);

        if (listing == null) {
            throw new ResourceNotFoundException(id);
        }

        fields.forEach((field, value) -> {
            switch (field) {
                case "title":
                    listing.setTitle((String) value);
                    break;
                case "longDescription":
                    listing.setLongDescription((String) value);
                    break;
                case "shortDescription":
                    listing.setShortDescription((String) value);
                    break;
                case "slug":
                    listing.setSlug((String) value);
                case "active":
                    listing.setActive((boolean) value);
            }
        });

        save(listing);
    }

    public void delete(Long id) throws ResourceNotFoundException {
        Listing listing = repository.findOne(id);

        if (listing == null) {
            throw new ResourceNotFoundException(id);
        }

        repository.delete(listing);
    }

    public List<Listing> findFeatured(int page, int size) {
        return repository.findAllFeatured(new PageRequest(page, size));
    }
}
