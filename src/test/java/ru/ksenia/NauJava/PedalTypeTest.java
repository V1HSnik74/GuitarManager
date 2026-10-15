package ru.ksenia.NauJava;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.ksenia.NauJava.dao.SetupRepositoryCustom;
import ru.ksenia.NauJava.entity.*;
import ru.ksenia.NauJava.enums.ChainZone;
import ru.ksenia.NauJava.enums.PedalTypes;
import ru.ksenia.NauJava.repository.PedalRepository;
import ru.ksenia.NauJava.repository.PedalSlotRepository;
import ru.ksenia.NauJava.repository.SetupRepository;
import ru.ksenia.NauJava.repository.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PedalTypeTest {
    private final SetupRepository setupRepository;
    private final SetupRepositoryCustom setupRepositoryCustom;
    private final UserRepository userRepository;
    private final PedalRepository pedalRepository;
    private final PedalSlotRepository pedalSlotRepository;

    @Autowired
    public PedalTypeTest(
            SetupRepository setupRepository,
            SetupRepositoryCustom setupRepositoryCustom,
            UserRepository userRepository,
            PedalRepository pedalRepository,
            PedalSlotRepository pedalSlotRepository) {
        this.setupRepository = setupRepository;
        this.setupRepositoryCustom = setupRepositoryCustom;
        this.userRepository = userRepository;
        this.pedalRepository = pedalRepository;
        this.pedalSlotRepository = pedalSlotRepository;
    }

    private Setup setup;
    private User user;
    private Pedal pedal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("guitarist");
        user.setLogin("guitarist12345");
        user.setPassword("qwerty");
        user = userRepository.save(user);

        pedal = new Pedal();
        pedal.setBrand("Ibanez");
        pedal.setModel("TubeScreamer");
        pedal.setPedalType(PedalTypes.OVERDRIVE);
        pedal.setUser(user);
        pedal = pedalRepository.save(pedal);

        setup = new Setup();
        setup.setName("SOAD");
        setup.setUser(user);
        setup = setupRepository.save(setup);

        PedalSlot slot = new PedalSlot();
        slot.setSetup(setup);
        slot.setPedal(pedal);
        slot.setOrderIndex(0);
        slot.setChainZone(ChainZone.PRE_AMP);
        pedalSlotRepository.save(slot);
    }

    @Test
    void testFindAllContainingPedalTypeJPQL() {
        List<Setup> results = setupRepository.findAllContainingPedalType(PedalTypes.OVERDRIVE);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(s -> s.getId().equals(setup.getId())));
        List<Setup> emptyResult = setupRepository.findAllContainingPedalType(PedalTypes.FUZZ);
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    void testFindAllContainingPedalTypeCriteria() {
        List<Setup> results = setupRepositoryCustom.findAllContainingPedalType(PedalTypes.OVERDRIVE);
        assertNotNull(results);
        assertFalse(results.isEmpty());
        assertTrue(results.stream().anyMatch(s -> s.getId().equals(setup.getId())));
        List<Setup> emptyResult = setupRepositoryCustom.findAllContainingPedalType(PedalTypes.FUZZ);
        assertTrue(emptyResult.isEmpty());
    }

    @Test
    void testSetupExistsByUserIdAndNameJPQL() {
        boolean exists = setupRepository.existsByUserIdAndName(user.getId(), "SOAD");
        assertTrue(exists);
        boolean notExists = setupRepository.existsByUserIdAndName(user.getId(), "Hypocrisy");
        assertFalse(notExists);
    }

    @Test
    void testSetupExistsByUserIdAndNameCriteria() {
        boolean exists = setupRepositoryCustom.existsByUserIdAndName(user.getId(), "SOAD");
        assertTrue(exists);
        boolean notExists = setupRepositoryCustom.existsByUserIdAndName(user.getId(), "Hypocrisy");
        assertFalse(notExists);
    }

}
