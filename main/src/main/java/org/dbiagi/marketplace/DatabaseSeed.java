package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.extern.log4j.Log4j2;
import org.dbiagi.marketplace.entity.*;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Context;
import org.dbiagi.marketplace.repository.*;
import org.dbiagi.marketplace.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Log4j2
public class DatabaseSeed implements ApplicationRunner {

    public static final int USERS = 5;
    public static final int STORES = 5;
    public static final int LISTINGS = 30;

    private final ContextRepository contextRepository;
    private final Faker faker;
    private final StoreRepository storeRepository;
    private final ListingRepository listingRepository;
    private UserService userService;
    private final SettingRepository settingRepository;
    private final CategoryRepository categoryRepository;
    private Slugify slugify;

    private List<Store> stores = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Autowired
    public DatabaseSeed(
        StoreRepository storeRepository,
        UserService userService,
        SettingRepository settingRepository,
        ContextRepository contextRepository,
        ListingRepository listingRepository,
        CategoryRepository categoryRepository,
        Faker faker,
        Slugify slugify) {
        this.storeRepository = storeRepository;
        this.userService = userService;
        this.settingRepository = settingRepository;
        this.contextRepository = contextRepository;
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.faker = faker;
        this.slugify = slugify;
    }

    private void createUsers() {
        for (User.Role role : User.Role.values()) {
            User user = new User();
            user.setRole(role);
            user.setName(role.name());
            user.setUsername(role.name());
            user.setPlainPassword("123");
            user.setEmail(faker.internet().emailAddress());
            user.setStore(stores.get(1));
            user.setEnabled(true);
            user.setExpired(false);

            users.add(user);
        }

        userService.save(users);
    }

    private void createStores() {
        for (int i = 0; i < DatabaseSeed.STORES; i++) {
            Store store = new Store();
            store.setEmail(faker.internet().emailAddress());
            store.setAddress(faker.address().streetAddress());
            store.setCellphone(faker.phoneNumber().cellPhone());
            store.setPhone(faker.phoneNumber().phoneNumber());
            store.setName(faker.lorem().sentence(1));
            store.setNumber(faker.address().streetAddressNumber());
            store.setType(Store.Type.STORE);
            store.setEmail(faker.internet().emailAddress());
            store.setUsers(new ArrayList<>());

            storeRepository.save(store);

            stores.add(store);
        }
    }

    private void createListings() {
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
            category.setSlug(slugify.slugify(category.getName()));

            for (int i = faker.number().numberBetween(0, 3); i > 0; i--) {
                Category child = new Category();
                child.setContext(context);
                child.setName(faker.lorem().word());
                child.setSlug(slugify.slugify(child.getName()));
                category.addChild(child);
            }

            categories.add(category);
        }

        categoryRepository.saveAll(categories);

        ArrayList<Listing> listings = new ArrayList<>();
        for (int i = 0; i < LISTINGS; i++) {
            Listing l = new Listing();
            l.setStore(stores.get(faker.number().numberBetween(1, stores.size() - 1)));
            l.setLongDescription(faker.lorem().sentence(20, 10));
            l.setShortDescription(faker.lorem().sentence());
            l.setTitle(faker.lorem().sentence(2));
            l.setSlug(slugify.slugify(l.getTitle()));
            l.setFeatured(faker.bool().bool());

            for (int j = faker.number().numberBetween(1, 3); j > 0; j--) {
                l.getCategories().add(categories.get(faker.number().numberBetween(1, categoriesTitle.length - 1)));
            }

            listings.add(l);
        }

        listingRepository.saveAll(listings);
    }

    @Override
    public void run(ApplicationArguments args) {
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
