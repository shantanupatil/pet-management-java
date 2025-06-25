package com.brady.scheduler;

import com.brady.entities.owner.OwnerEntity;
import com.brady.entities.pet.PetEntity;
import jakarta.annotation.Resource;
import jakarta.ejb.Schedule;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;

import jakarta.jms.JMSContext;
import jakarta.jms.Queue;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Singleton
@Startup
public class VaccinationCheckScheduler {

    @PersistenceContext(unitName = "bradyPU")
    private EntityManager em;

    @Resource(lookup = "java:/jms/queue/VaccinationQueue")
    private Queue vaccinationQueue;

    @Inject
    private JMSContext jmsContext;

    @Schedule(hour = "*", minute = "*", persistent = false)
    public void checkVaccinationDue() {
        System.out.println("Running vaccination due check..");
        LocalDateTime oneHourAgo = LocalDateTime.now().minusHours(1);

        List<PetEntity> pets = em.createQuery(
                        "SELECT p FROM PetEntity p JOIN p.vaccinations v " +
                                "WHERE v.vaccinationDate = (" +
                                "SELECT MAX(v2.vaccinationDate) FROM p.vaccinations v2) " +
                                "AND v.vaccinationDate < :oneHourAgo", PetEntity.class)
                .setParameter("oneHourAgo", oneHourAgo)
                .getResultList();

        for (PetEntity pet : pets) {
            OwnerEntity owner = pet.getOwner();
            String message = String.format("Pet: %s, Owner Phone: %s",
                    pet.getName(),
                    owner != null ? owner.getTelephone() : "No phone number found.");
            jmsContext.createProducer().send(vaccinationQueue, message);
        }
    }
}
