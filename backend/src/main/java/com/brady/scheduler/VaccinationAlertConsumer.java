package com.brady.scheduler;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;

import java.util.logging.Logger;

@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "java:/jms/queue/VaccinationQueue")
        }
)
public class VaccinationAlertConsumer implements MessageListener {

    private static final Logger logger = Logger.getLogger(VaccinationAlertConsumer.class.getName());

    @Override
    public void onMessage(Message message) {
        try {
            if (message instanceof TextMessage textMessage) {
                logger.info(textMessage.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
