package ru.ksenia.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.repository.PedalRepository;
import ru.ksenia.NauJava.repository.PedalSlotRepository;
import ru.ksenia.NauJava.repository.SetupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class SetupPedalboardServiceImpl implements  SetupPedalboardService{
    private final SetupRepository setupRepository;
    private final PedalSlotRepository pedalSlotRepository;
    private final PedalRepository pedalRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public SetupPedalboardServiceImpl(
            SetupRepository setupRepository,
            PedalSlotRepository pedalSlotRepository,
            PedalRepository pedalRepository,
            PlatformTransactionManager transactionManager) {
        this.setupRepository = setupRepository;
        this.pedalSlotRepository = pedalSlotRepository;
        this.pedalRepository = pedalRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<PedalSlot> updatePedalChain(Long setupId, List<Long> pedalIds) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            Setup setup = setupRepository.findById(setupId).orElseThrow(() -> new IllegalArgumentException("Сетап не найден"));
            pedalSlotRepository.deleteAllBySetupId(setupId);
            List<PedalSlot> slotsToSave = new ArrayList<>();
            if (pedalIds != null && !pedalIds.isEmpty()) {
                for (int i = 0; i < pedalIds.size(); i++){
                    Long pedalId = pedalIds.get(i);
                    Pedal pedal = pedalRepository.findById(pedalId).orElseThrow(() -> new IllegalArgumentException("Педаль не найдена"));
                    PedalSlot slot = new PedalSlot();
                    slot.setSetup(setup);
                    slot.setPedal(pedal);
                    slot.setOrderIndex(i);
                    slot.setChainZone(pedal.getPedalType().getChainZone());
                    slotsToSave.add(slot);
                }
            }
            Iterable<PedalSlot> savedSlots = pedalSlotRepository.saveAll(slotsToSave);
            List<PedalSlot> result = new ArrayList<>();
            savedSlots.forEach(result::add);
            transactionManager.commit(status);
            return result;
        } catch (Exception e){
            transactionManager.rollback(status);
            throw e;
        }
    }
}
