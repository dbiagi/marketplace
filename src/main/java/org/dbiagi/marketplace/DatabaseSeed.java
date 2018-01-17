package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import org.dbiagi.marketplace.core.entity.*;
import org.dbiagi.marketplace.core.importer.VehicleFeaturesImporter;
import org.dbiagi.marketplace.core.importer.VehicleImporter;
import org.dbiagi.marketplace.core.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class DatabaseSeed implements ApplicationRunner {

    private static final int USERS = 20;
    private static final int STORES = 10;
    private static final int STOCK = 20;
    private static final int FEATURES_PER_VEHICLE = 2;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private Faker faker = new Faker(new Locale("pt-BR"));

    @Autowired
    private StoreRepository storeRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VehicleImporter vehicleImporter;
    @Autowired
    private VehicleFeaturesImporter vehicleFeaturesImporter;
    @Autowired
    private VehicleFeatureRepository vehicleFeatureRepository;
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private VehicleStockRepository vehicleStockRepository;

    private List<Store> stores = new ArrayList<>();
    private List<User> users = new ArrayList<>();

    @Override
    public void run(ApplicationArguments args) throws Exception {
//        if(!args.containsOption("seed")) {
//            return;
//        }

        createStores();
        createUsers();
        importVehicle();
        importVehicleFeatures();
        createVehicleStock();
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

        vehicleImporter.run(classPathResource.getFile(), mapping);

    }

    private void importVehicleFeatures() {
        ClassPathResource classPathResource = new ClassPathResource("vehicle-features.json");
        try {
            vehicleFeaturesImporter.run(classPathResource.getFile());
        } catch (Exception e) {
            logger.error("Error importing vehicle features", e);
        }
    }

    private void createVehicleStock() {
        List<Vehicle> vehicles = vehicleRepository.findAll(new PageRequest(1, 20)).getContent();
        List<VehicleFeature> vehicleFeatures = (List<VehicleFeature>) vehicleFeatureRepository.findAll();

        for (int i = 0; i < STOCK; i++) {
            VehicleStock vehicleStock = new VehicleStock();
            vehicleStock.setColor(faker.color().name());
            vehicleStock.setDiscount(10d);

            vehicleStock.setFabricationYear(faker.number().numberBetween(2010, 2018));
            vehicleStock.setModelYear(vehicleStock.getFabricationYear() - faker.number().numberBetween(0, 1));

            for (int j = 0; j < FEATURES_PER_VEHICLE; j++) {
                vehicleStock.addFeature(vehicleFeatures.get(faker.random().nextInt(20) + 1));
            }

            vehicleStock.setFuel(Vehicle.FuelEnum.FLEX);
            vehicleStock.setNegotiating(false);
            vehicleStock.setMileage(faker.number().numberBetween(0, 1000));
            vehicleStock.setPlate(faker.regexify("[A-Z]{3}-[0-9]{4}"));
            vehicleStock.setObservation(faker.lorem().sentence(5));
            vehicleStock.setVehicle(vehicles.get(faker.number().numberBetween(1, 20)));
            vehicleStock.setQuantity(faker.number().numberBetween(1, 10));
            vehicleStock.setPrice(faker.number().randomDouble(2, 60000, 20000));
            vehicleStock.setStore(stores.get(faker.number().numberBetween(1, STORES)));

            try {
                vehicleStockRepository.save(vehicleStock);
            } catch (Exception e) {
                logger.error("deu ruim", e);
            }
        }
    }
}
