package ru.ksenia.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.ksenia.NauJava.entity.Pedal;
import ru.ksenia.NauJava.entity.PedalSlot;
import ru.ksenia.NauJava.entity.Setup;
import ru.ksenia.NauJava.enums.PedalTypes;

import java.util.List;

@Repository
public class SetupRepositoryCustomImpl implements SetupRepositoryCustom {
    private final EntityManager entityManager;

    @Autowired
    public SetupRepositoryCustomImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Setup> findAllContainingPedalType(PedalTypes pedalType) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Setup> criteriaQuery = criteriaBuilder.createQuery(Setup.class);
        Root<Setup> setupRoot = criteriaQuery.from(Setup.class);
        Join<Setup, PedalSlot> slotJoin = setupRoot.join("pedalboard");
        Join<PedalSlot, Pedal> pedalJoin = slotJoin.join("pedal");
        Predicate pedalTypePredicate = criteriaBuilder.equal(pedalJoin.get("pedalType"), pedalType);
        criteriaQuery.select(setupRoot).distinct(true).where(pedalTypePredicate);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public boolean existsByUserIdAndName(Long userId, String name) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Setup> setupRoot = criteriaQuery.from(Setup.class);
        Predicate userIdPredicate = criteriaBuilder.equal(setupRoot.get("user").get("id"), userId);
        Predicate namePredicate = criteriaBuilder.equal(setupRoot.get("name"), name);
        criteriaQuery.select(criteriaBuilder.count(setupRoot)).where(criteriaBuilder.and(userIdPredicate, namePredicate));
        Long count = entityManager.createQuery(criteriaQuery).getSingleResult();
        return count != null && count > 0;
    }
}
