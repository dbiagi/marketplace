package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.Setting;
import org.dbiagi.marketplace.entity.Store;
import org.dbiagi.marketplace.entity.User;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Context;
import org.dbiagi.marketplace.exception.EntityValidationException;
import org.dbiagi.marketplace.repository.ContextRepository;
import org.dbiagi.marketplace.repository.SettingRepository;
import org.dbiagi.marketplace.service.ListingService;
import org.dbiagi.marketplace.service.StoreService;
import org.dbiagi.marketplace.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile({"dev", "test"})
public class DatabaseSeed implements ApplicationRunner {

    public static final int USERS = 5;
    public static final int STORES = 5;
    public static final int LISTINGS = 10;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ContextRepository contextRepository;
    private final Faker faker;
    private final StoreService storeService;
    private final UserService userService;
    private final ListingService listingService;
    private final SettingRepository settingRepository;

    private List<Store> stores = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Autowired
    public DatabaseSeed(
            StoreService storeService,
            UserService userService,
            SettingRepository settingRepository,
            ContextRepository contextRepository,
            Faker faker,
            ListingService listingService
    ) {
        this.storeService = storeService;
        this.userService = userService;
        this.settingRepository = settingRepository;
        this.contextRepository = contextRepository;
        this.faker = faker;
        this.listingService = listingService;
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
            users.add(user);
        }

        for (int i = 0; i < DatabaseSeed.USERS; i++) {
            User user = new User();
            user.setCellphone(faker.phoneNumber().cellPhone());
            user.setConnected(false);
            user.setName(faker.name().name());
            user.setEmail(faker.internet().emailAddress());
            user.setUsername(faker.name().username());
            user.setPlainPassword("123");
            users.add(user);
            user.setRole(User.Role.ADMIN);

            Store store = stores.get(faker.random().nextInt(DatabaseSeed.STORES - 1));
            store.addUser(user);
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
            store.setCnpj(faker.number().digits(10));
            store.setType(Store.Type.STORE);
            store.setNumber(faker.address().streetAddressNumber());
            store.setEmail(faker.internet().emailAddress());

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
            l.setSlug(faker.internet().slug());

            for (int j = faker.number().numberBetween(0, 2); j > 0; j--) {
                l.addCategory(categories.get(faker.number().numberBetween(1, categoriesTitle.length - 1)));
            }

            listingService.save(l);
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String version = settingRepository.findValueByKey("database_version");

        if ("1.0.0".equals(version)) {
            logger.info("Database already have initial data. Skipping seed.");

            return;
        }

        createStores();
        createUsers();
        createListings();

        settingRepository.save(new Setting("database_version", "1.0.0"));
    }
}
