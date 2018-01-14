package org.dbiagi.marketplace.core.runner;

import com.github.javafaker.Faker;
import org.dbiagi.marketplace.core.entity.Store;
import org.dbiagi.marketplace.core.entity.User;
import org.dbiagi.marketplace.core.importer.VehicleImporter;
import org.dbiagi.marketplace.core.repository.StoreRepository;
import org.dbiagi.marketplace.core.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class DatabaseSeed implements ApplicationRunner {

    private static final int USERS = 20;
    private static final int STORES = 10;
    private Faker faker = new Faker(new Locale("pt-BR"));
    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<Store> stores = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    @Autowired
    private VehicleImporter vehicleImporter;

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        if(!args.containsOption("seed")) {
//            return;
//        }

        createStores();
        createUsers();
        importVehicle();
    }

    private void createUsers() {
        for (int i = 0; i < DatabaseSeed.USERS; i++) {
            User user = new User();
            user.setCellphone(faker.phoneNumber().cellPhone());
            user.setConnected(false);
            user.setName(faker.name().name());
            users.add(user);

            Store store = stores.get(faker.random().nextInt(DatabaseSeed.STORES - 1));
            store.addUser(user);
        }

        userRepository.save(users);
    }

    private void createStores() {
        for (int i = 0; i < DatabaseSeed.STORES; i++) {
            Store store = new Store();
            store.setAddress(faker.address().streetAddress());
            store.setCellphone(faker.phoneNumber().cellPhone());
            store.setPhone(faker.phoneNumber().phoneNumber());
            store.setName(faker.lorem().sentence(1));
            store.setCnpj(faker.number().digits(10));
            store.setType(Store.StoreTypeEnum.STORE);
            store.setNumber(faker.address().streetAddressNumber());
            stores.add(store);
        }

        storeRepository.save(stores);
    }

    private void importVehicle() throws IOException {
        logger.debug("Importing vehicles async");
        ClassPathResource classPathResource = new ClassPathResource("todos-veiculos-utf-8.csv");

        Map<String, String> mapping = new HashMap<>();
        mapping.put("type", "TIPO");
        mapping.put("brand", "MARCA");
        mapping.put("model", "MODELO");
        mapping.put("version", "VERSÃO");

        vehicleImporter.run(mapping, classPathResource.getFile());

    }
}
