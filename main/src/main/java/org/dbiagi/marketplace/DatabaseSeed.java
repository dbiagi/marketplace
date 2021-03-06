package org.dbiagi.marketplace;

import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import lombok.extern.log4j.Log4j2;
import org.dbiagi.marketplace.entity.Account;
import org.dbiagi.marketplace.entity.Listing;
import org.dbiagi.marketplace.entity.Setting;
import org.dbiagi.marketplace.entity.classification.Category;
import org.dbiagi.marketplace.entity.classification.Context;
import org.dbiagi.marketplace.exception.EntityConstraintValidationException;
import org.dbiagi.marketplace.repository.rest.CategoryRestRepository;
import org.dbiagi.marketplace.repository.rest.ContextRepository;
import org.dbiagi.marketplace.repository.rest.ListingRepository;
import org.dbiagi.marketplace.repository.rest.SettingRepository;
import org.dbiagi.marketplace.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;

@Component
@Log4j2
public class DatabaseSeed implements ApplicationRunner {
    private static final int LISTINGS = 30;
    private static final int ACCOUNTS = 10;

    private final ContextRepository contextRepository;
    private final Faker faker;
    private final ListingRepository listingRepository;
    private final SettingRepository settingRepository;
    private final CategoryRestRepository categoryRepository;
    private AccountService accountService;
    private Slugify slugify;
    private LinkedList<Account> accounts;

    @Autowired
    public DatabaseSeed(
        AccountService accountService,
        SettingRepository settingRepository,
        ContextRepository contextRepository,
        ListingRepository listingRepository,
        CategoryRestRepository categoryRepository,
        Faker faker,
        Slugify slugify) {
        this.accountService = accountService;
        this.settingRepository = settingRepository;
        this.contextRepository = contextRepository;
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.faker = faker;
        this.slugify = slugify;
    }

    private void createAccounts() {
        accounts = new LinkedList<>();
        for (var i = 0; i < ACCOUNTS; i++) {
            Account account = new Account();
            account.setRole(Account.Role.ROLE_USER);
            account.setName(faker.name().name());
            account.setUsername(faker.name().username());
            account.setPlainPassword("123");
            account.setEmail(faker.internet().emailAddress());
            account.setEnabled(true);
            account.setExpired(false);
            accounts.add(account);
        }

        accountService.save(accounts);
    }

    private Account createAdminAccount() throws EntityConstraintValidationException {
        Account account = new Account();
        account.setRole(Account.Role.ROLE_USER);
        account.setName("Administrator");
        account.setUsername("admin");
        account.setPlainPassword("123");
        account.setEmail(faker.internet().emailAddress());
        account.setEnabled(true);
        account.setExpired(false);
        accounts.add(account);

        return accountService.save(account);
    }

    private void createListings(Account listingsOwner) {
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
            l.setLongDescription(faker.lorem().sentence(20, 10));
            l.setShortDescription(faker.lorem().sentence());
            l.setTitle(faker.lorem().sentence(2));
            l.setSlug(slugify.slugify(l.getTitle()));
            l.setFeatured(faker.bool().bool());
            l.setOwner(listingsOwner);
            for (int j = faker.number().numberBetween(1, 3); j > 0; j--) {
                l.getCategories().add(categories.get(faker.number().numberBetween(1, categoriesTitle.length - 1)));
            }

            listings.add(l);
        }

        listingRepository.saveAll(listings);
    }

    @Override
    public void run(ApplicationArguments args) throws EntityConstraintValidationException {
        Optional<Setting> optional = settingRepository.findByKey("database_version");

        if (optional.isPresent() && "1.0.0".equals(optional.get().getValue())) {
            log.info("Database already have initial data. Skipping seed.");

            return;
        }

        createAccounts();
        var admin = createAdminAccount();
        createListings(admin);

        settingRepository.save(new Setting("database_version", "1.0.0"));
    }
}
