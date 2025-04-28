package com.svenruppert.expedition.planner.services;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.expedition.planner.services.auth.AuthService;
import com.svenruppert.expedition.planner.services.login.LoginRepository;
import com.svenruppert.expedition.planner.services.login.LoginService;
import com.svenruppert.expedition.planner.services.login.passwords.SaltGenerator;
import com.svenruppert.expedition.planner.services.persistence.DataRoot;
import com.svenruppert.expedition.planner.services.persistence.PersistenceService;
import com.svenruppert.expedition.planner.services.user.UserRepository;
import com.svenruppert.expedition.planner.services.user.UserService;
import com.svenruppert.expedition.planner.views.packing.itemlist.ItemRepository;
import com.svenruppert.expedition.planner.views.packing.itemlist.ItemService;
import com.svenruppert.expedition.planner.views.packing.participants.ParticipantRepository;
import com.svenruppert.expedition.planner.views.packing.participants.ParticipantService;
import com.svenruppert.expedition.planner.views.tour.TourRepository;
import com.svenruppert.expedition.planner.views.tour.TourService;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonRegistry implements HasLogger {

    private static final Map<Class<?>, Object> INSTANCES = new ConcurrentHashMap<>();

    private SingletonRegistry() {
    }

    /**
     * Registriert eine vorhandene Instanz als Singleton.
     */
    public static synchronized <T> void registerInstance(Class<T> clazz, T instance) {
        if (INSTANCES.containsKey(clazz)) {
            throw new IllegalStateException("Instance for " + clazz.getName() + " is already registered.");
        }
        INSTANCES.put(clazz, instance);
    }

    /**
     * Holt eine registrierte Instanz.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getInstance(Class<T> clazz) {
        return (T) INSTANCES.get(clazz);

    }

    public static PersistenceService getOrCreatePersistenceService() {
        if (INSTANCES.containsKey(PersistenceService.class)) {
            return getInstance(PersistenceService.class);
        } else {
            PersistenceService persistenceService = new PersistenceService();
            persistenceService.init();
            registerInstance(PersistenceService.class, persistenceService);
            return persistenceService;
        }
    }


    public static ParticipantRepository getOrCreateParticipantRepository() {
        if (INSTANCES.containsKey(ParticipantRepository.class)) {
            return getInstance(ParticipantRepository.class);
        } else {
            PersistenceService persistenceService = getOrCreatePersistenceService();
            DataRoot dataRoot = persistenceService.getDataRoot();
            ParticipantRepository participantRepository = dataRoot.getParticipantRepository();
            registerInstance(ParticipantRepository.class, participantRepository);
            return participantRepository;
        }
    }

    public static ParticipantService getOrCreateParticipantsService() {
        if (INSTANCES.containsKey(ParticipantService.class)) {
            return getInstance(ParticipantService.class);
        } else {
            ParticipantRepository participantRepository = getOrCreateParticipantRepository();
            ParticipantService participantService = new ParticipantService(participantRepository);
            registerInstance(ParticipantService.class, participantService);
            return participantService;
        }
    }

    private static ItemRepository getOrCreateItemRepository() {
        if (INSTANCES.containsKey(ItemRepository.class)) {
            return getInstance(ItemRepository.class);
        } else {
            PersistenceService persistenceService = getOrCreatePersistenceService();
            DataRoot dataRoot = persistenceService.getDataRoot();
            ItemRepository itemRepository = dataRoot.getItemRepository();
            registerInstance(ItemRepository.class, itemRepository);
            return itemRepository;
        }
    }

    public static ItemService getOrCreateItemService() {
        if (INSTANCES.containsKey(ItemService.class)) {
            return getInstance(ItemService.class);
        } else {
            ItemRepository itemRepository = getOrCreateItemRepository();
            ItemService itemService = new ItemService(itemRepository);
            registerInstance(ItemService.class, itemService);
            return itemService;
        }
    }

    public static AuthService getOrCreateAuthService() {
        if (INSTANCES.containsKey(AuthService.class)) {
            return getInstance(AuthService.class);
        } else {
            LoginService loginService = getOrCreateLoginService();
            UserService userService = getOrCreateUserService();
            AuthService authService = new AuthService(loginService, userService);
            registerInstance(AuthService.class, authService);
            return authService;
        }
    }

    public static UserService getOrCreateUserService() {
        if (INSTANCES.containsKey(UserService.class)) {
            return getInstance(UserService.class);
        } else {
            LoginService loginService = getOrCreateLoginService();
            UserRepository userRepository = getOrCreateUserRepository();
            UserService userService = new UserService(userRepository, loginService);
            registerInstance(UserService.class, userService);
            return userService;
        }
    }

    public static UserRepository getOrCreateUserRepository() {
        if (INSTANCES.containsKey(UserRepository.class)) {
            return getInstance(UserRepository.class);
        } else {
            PersistenceService persistenceService = getOrCreatePersistenceService();
            DataRoot dataRoot = persistenceService.getDataRoot();
            UserRepository userRepository = dataRoot.getUserRepository();
            registerInstance(UserRepository.class, userRepository);
            return userRepository;
        }
    }

    public static LoginService getOrCreateLoginService() {
        if (INSTANCES.containsKey(LoginService.class)) {
            return getInstance(LoginService.class);

        } else {
            LoginRepository loginRepository = getOrCreateLoginRepository();
            LoginService loginService = new LoginService(loginRepository, new SaltGenerator());
            registerInstance(LoginService.class, loginService);
            return loginService;
        }
    }

    public static LoginRepository getOrCreateLoginRepository() {

        if (INSTANCES.containsKey(LoginRepository.class)) {
            return getInstance(LoginRepository.class);
        } else {
            PersistenceService persistenceService = getOrCreatePersistenceService();
            DataRoot dataRoot = persistenceService.getDataRoot();
            LoginRepository loginRepository = dataRoot.getLoginRepository();
            registerInstance(LoginRepository.class, loginRepository);
            return loginRepository;
        }
    }


    public static TourService getOrCreateTourService() {
        if (INSTANCES.containsKey(TourService.class)) {
            return getInstance(TourService.class);

        } else {
            TourRepository tourRepository = getOrCreateTourRepository();
            TourService tourService = new TourService(tourRepository);
            registerInstance(TourService.class, tourService);
            return tourService;
        }
    }

    public static TourRepository getOrCreateTourRepository() {

        if (INSTANCES.containsKey(TourRepository.class)) {
            return getInstance(TourRepository.class);
        } else {
            PersistenceService persistenceService = getOrCreatePersistenceService();
            DataRoot dataRoot = persistenceService.getDataRoot();
            TourRepository tourRepository = dataRoot.getTourRepository();
            registerInstance(TourRepository.class, tourRepository);
            return tourRepository;
        }
    }
}
