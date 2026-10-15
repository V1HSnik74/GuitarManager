package ru.ksenia.NauJava;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.entity.User;
import ru.ksenia.NauJava.enums.ChainZone;
import ru.ksenia.NauJava.enums.PedalTypes;
import ru.ksenia.NauJava.repository.PedalRepository;
import ru.ksenia.NauJava.repository.PedalSlotRepository;
import ru.ksenia.NauJava.repository.SetupRepository;
import ru.ksenia.NauJava.repository.UserRepository;
import ru.ksenia.NauJava.service.SetupPedalboardService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PedalboardTransactionTest {
    private final SetupPedalboardService setupPedalboardService;
    private final SetupRepository setupRepository;
    private final PedalRepository pedalRepository;
    private final PedalSlotRepository pedalSlotRepository;
    private final UserRepository userRepository;

    @Autowired
    public PedalboardTransactionTest(SetupPedalboardService setupPedalboardService, SetupRepository setupRepository, PedalRepository pedalRepository, PedalSlotRepository pedalSlotRepository, UserRepository userRepository) {
        this.setupPedalboardService = setupPedalboardService;
        this.setupRepository = setupRepository;
        this.pedalRepository = pedalRepository;
        this.pedalSlotRepository = pedalSlotRepository;
        this.userRepository = userRepository;
    }

    private Setup setup;
    private Pedal flanger;
    private Pedal fuzz;
    private Long initialSlotId;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("guitarist");
        user.setLogin("guitarist12345");
        user.setPassword("qwerty");
        user = userRepository.save(user);

        setup = new Setup();
        setup.setName("Meteora");
        setup.setUser(user);
        setup = setupRepository.save(setup);

        flanger = new Pedal();
        flanger.setBrand("boss");
        flanger.setModel("BF-3");
        flanger.setPedalType(PedalTypes.FLANGER);
        flanger.setUser(user);
        flanger = pedalRepository.save(flanger);

        fuzz = new Pedal();
        fuzz.setBrand("boss");
        fuzz.setModel("FZ-5");
        fuzz.setPedalType(PedalTypes.FUZZ);
        fuzz.setUser(user);
        fuzz = pedalRepository.save(fuzz);

        PedalSlot initialSlot = new PedalSlot();
        initialSlot.setSetup(setup);
        initialSlot.setPedal(flanger);
        initialSlot.setOrderIndex(0);
        initialSlot.setChainZone(ChainZone.FX_LOOP);
        initialSlot = pedalSlotRepository.save(initialSlot);
        initialSlotId = initialSlot.getId();
    }

    @AfterEach
    void delete(){
        pedalSlotRepository.deleteAll();
        setupRepository.deleteAll();
        pedalRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void testUpdatePedalboardSuccess() {
        List<Long> newPedalIds = List.of(fuzz.getId(), flanger.getId());
        List<PedalSlot> result = setupPedalboardService.updatePedalChain(setup.getId(), newPedalIds);
        assertEquals(2, result.size());
        assertEquals(fuzz.getId(), result.getFirst().getPedal().getId());
        assertEquals(0, result.getFirst().getOrderIndex());
        assertEquals(flanger.getId(), result.get(1).getPedal().getId());
        assertEquals(1, result.get(1).getOrderIndex());

        List<PedalSlot> slots = (List<PedalSlot>) pedalSlotRepository.findAll();
        assertEquals(2, slots.size());
    }

    @Test
    void testUpdatePedalboardRollback() {
        List<Long> invalidPedalIds = List.of(fuzz.getId(), -1L);
        assertThrows(Exception.class, () -> setupPedalboardService.updatePedalChain(setup.getId(), invalidPedalIds));
        List<PedalSlot> remainingSlots = (List<PedalSlot>) pedalSlotRepository.findAll();
        assertEquals(1, remainingSlots.size());
        assertEquals(initialSlotId, remainingSlots.getFirst().getId());
    }
}
