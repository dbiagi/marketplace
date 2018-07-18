package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import lombok.extern.java.Log;
import org.dbiagi.marketplace.entity.*;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Context;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.repository.CategoryRepository;
import org.dbiagi.marketplace.repository.ContextRepository;
import org.dbiagi.marketplace.repository.SettingRepository;
import org.dbiagi.marketplace.service.ListingService;
import org.dbiagi.marketplace.service.StoreService;
import org.dbiagi.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Profile({"dev", "test"})
@Log
public class DatabaseSeed implements ApplicationRunner {

    public static final int USERS = 5;
    public static final int STORES = 5;
    public static final int LISTINGS = 30;


    private final ContextRepository contextRepository;
    private final Faker faker;
    private final StoreService storeService;
    private final UserService userService;
    private final ListingService listingService;
    private final SettingRepository settingRepository;
    private final CategoryRepository categoryRepository;

    private List<Store> stores = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Autowired
    public DatabaseSeed(
        StoreService storeService,
        UserService userService,
        SettingRepository settingRepository,
        ContextRepository contextRepository,
        Faker faker,
        ListingService listingService,
        CategoryRepository categoryRepository) {
        this.storeService = storeService;
        this.userService = userService;
        this.settingRepository = settingRepository;
        this.contextRepository = contextRepository;
        this.faker = faker;
        this.listingService = listingService;
        this.categoryRepository = categoryRepository;
    }

    private void createUsers() {
        for (User.Role role : User.Role.values()) {
            User user = User.builder()
                .role(role)
                .name(role.name())
                .username(role.name())
                .plainPassword("123")
                .email(faker.internet().emailAddress())
                .store(stores.get(1))
                .temporalInfo(new TemporalInfo())
                .enabled(true)
                .expired(false)
                .build();

            users.add(user);
        }

        for (int i = 0; i < DatabaseSeed.USERS; i++) {
            User user = User.builder()
                .cellphone(faker.phoneNumber().cellPhone())
                .connected(false)
                .name(faker.name().name())
                .email(faker.internet().emailAddress())
                .username(faker.name().username())
                .plainPassword("123")
                .role(User.Role.ADMIN)
                .store(stores.get(faker.random().nextInt(DatabaseSeed.STORES - 1)))
                .temporalInfo(new TemporalInfo())
                .enabled(true)
                .expired(false)
                .build();

            users.add(user);
        }

        userService.save(users);
    }

    private void createStores() {
        for (int i = 0; i < DatabaseSeed.STORES; i++) {
            Store store = Store.builder()
                .email(faker.internet().emailAddress())
                .address(faker.address().streetAddress())
                .cellphone(faker.phoneNumber().cellPhone())
                .phone(faker.phoneNumber().phoneNumber())
                .name(faker.lorem().sentence(1))
                .number(faker.address().streetAddressNumber())
                .type(Store.Type.STORE)
                .email(faker.internet().emailAddress())
                .users(new ArrayList<>())
                .temporalInfo(new TemporalInfo())
                .build();

            try {
                storeService.save(store);
            } catch (EntityValidationException e) {
                e.printStackTrace();
            }

            stores.add(store);
        }
    }

    private void createListings() throws EntityValidationException {
        String[] categoriesTitle = {
            "Auto", "Beauty", "Food & Dine", "Entertainment", "Health & Care",
            "Restaurant", "Medical", "Music", "Theater"
        };

        Context context = new Context("listing");
        contextRepository.save(context);

        ArrayList<Category> categories = new ArrayList<>();

        for (String categoryTitle : categoriesTitle) {
            Category category = new Category();
            category.setContext(context);
            category.setName(categoryTitle);
            category.setSlug(categoryTitle.toLowerCase().replace(' ', '_'));

            for (int i = faker.number().numberBetween(0, 3); i > 0; i--) {
                Category child = new Category();
                child.setContext(context);
                child.setName(faker.lorem().word());
                child.setSlug(faker.internet().slug());
                category.addChild(child);
            }

            listingService.save(category);
            categories.add(category);
        }

        for (int i = 0; i < LISTINGS; i++) {
            Listing l = new Listing();
            l.setStore(stores.get(faker.number().numberBetween(1, stores.size() - 1)));
            l.setLongDescription(faker.lorem().sentence(20, 10));
            l.setShortDescription(faker.lorem().sentence());
            l.setTitle(faker.lorem().sentence(2));
            l.setSlug(faker.internet().slug() + "_" + i);
            l.setFeatured(faker.bool().bool());

            for (int j = faker.number().numberBetween(1, 3); j > 0; j--) {
                l.addCategory(categories.get(faker.number().numberBetween(1, categoriesTitle.length - 1)));
            }

            listingService.save(l);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Optional<Setting> optional = settingRepository.findByKey("database_version");

        if (optional.isPresent() && "1.0.0".equals(optional.get().getValue())) {
            log.info("Database already have initial data. Skipping seed.");

            return;
        }

        createStores();
        createUsers();
        createListings();

        settingRepository.save(new Setting("database_version", "1.0.0"));
    }
}
