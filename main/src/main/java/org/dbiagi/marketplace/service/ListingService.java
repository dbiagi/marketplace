package org.dbiagi.marketplace.service;

import org.dbiagi.marketplace.dto.ListingDTO;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.exception.EntityValidationExceptionFactory;
import org.dbiagi.marketplace.exception.ResourceNotFoundException;
import org.dbiagi.marketplace.repository.CategoryRepository;
import org.dbiagi.marketplace.repository.ListingRepository;
import org.dbiagi.marketplace.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ListingService {
    private ListingRepository repository;

    private CategoryRepository categoryRepository;
    private TagRepository tagRepository;

    private Validator validator;

    private EntityManager entityManager;

    @Autowired
    public ListingService(
        ListingRepository repository,
        CategoryRepository categoryRepository,
        TagRepository tagRepository, Validator validator,
        EntityManager entityManager) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.tagRepository = tagRepository;
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

    public ListingDTO save(ListingDTO dto) throws EntityValidationException {
        Listing listing = dtoToEntity(new Listing(), dto);

        save(listing);

        dto.setId(listing.getId());

        return dto;
    }

    public Listing find(long id) throws ResourceNotFoundException {
        Listing listing = repository.find(id);

        if (listing == null) {
            throw new ResourceNotFoundException(id);
        }

        return listing;
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

    public void update(Long id, ListingDTO updatedListing) throws ResourceNotFoundException, EntityValidationException {
        Listing listing = dtoToEntity(find(id), updatedListing);

        Set<ConstraintViolation<Listing>> violations = validator.validate(listing);

        if (!violations.isEmpty()) {
            throw new EntityValidationExceptionFactory<Listing>().create(violations);
        }

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
    
    private Listing dtoToEntity(Listing listing, ListingDTO dto) {
        listing.setTitle(dto.getTitle());
        listing.setFeatured(dto.isFeatured());
        listing.setSlug(dto.getSlug());
        listing.setShortDescription(dto.getShortDescription());
        listing.setLongDescription(dto.getLongDescription());
        listing.setActive(dto.isActive());
        listing.setStore(dto.getStore());

        if(!dto.getCategories().isEmpty()) {
            listing.setCategories(new HashSet<>(
                categoryRepository.findByIdIn(dto.getCategories())
            ));
        }

        if(!dto.getTags().isEmpty()) {
            listing.setTags(new HashSet<>(
                tagRepository.findByIdIn(dto.getTags())
            ));
        }

        return listing;
    }
}
