package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import org.dbiagi.marketplace.core.entity.Setting;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.repository.SettingRepository;
import org.dbiagi.marketplace.core.service.StoreService;
import org.dbiagi.marketplace.core.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DatabaseSeed implements ApplicationRunner {

    public static final int USERS = 20;
    public static final int STORES = 10;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Faker faker;
    private final StoreService storeService;
    private final UserService userService;
    private final SettingRepository settingRepository;

    private List<Store> stores = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Autowired
    public DatabaseSeed(StoreService storeService, UserService userService, SettingRepository settingRepository, Faker faker) {
        this.storeService = storeService;
        this.userService = userService;
        this.settingRepository = settingRepository;
        this.faker = faker;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String version = settingRepository.findValueByKey("database_version");

        if ("1.0.0".equals(version)) {
            logger.info("Database already have initial data. Skiping seed.");

            return;
        }

        createStores();
        createUsers();

        settingRepository.save(new Setting("database_version", "1.0.0"));
    }

    private void createUsers() {
        User diego = new User();
        diego.setRole(User.Role.SUPER_ADMIN);
        diego.setName("Diego");
        diego.setUsername("diego");
        diego.setPlainPassword("123");
        diego.setEmail("diego@invalid.com");
        diego.setStore(stores.get(1));
        users.add(diego);

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
            store.setType(Store.StoreTypeEnum.STORE);
            store.setNumber(faker.address().streetAddressNumber());
            store.setEmail(faker.internet().emailAddress());
            storeService.register(store);
            stores.add(store);
        }
    }
}
