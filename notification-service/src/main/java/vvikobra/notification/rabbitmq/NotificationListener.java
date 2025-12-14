package vvikobra.notification.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vvikobra.events.BookingCreatedEvent;
import vvikobra.events.BookingPaidEvent;
import vvikobra.events.PaymentCalculatedEvent;
import vvikobra.events.SkipassActivatedEvent;
import vvikobra.events.SkipassUsedEvent;
import vvikobra.notification.websocket.NotificationHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class NotificationListener {

    private static final Logger log = LoggerFactory.getLogger(NotificationListener.class);
    private final NotificationHandler notificationHandler;
    private final ObjectMapper objectMapper = new ObjectMapper().findAndRegisterModules();

    public NotificationListener(NotificationHandler notificationHandler) {
        this.notificationHandler = notificationHandler;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.notifications.booking_created", durable = "true"),
                    exchange = @Exchange(name = "ski-rental-exchange", type = "topic"),
                    key = "booking.created"
            )
    )
    public void handleBookingCreated(BookingCreatedEvent event) {
        log.info("Received BookingCreatedEvent: {}", event);
        sendNotification("BOOKING_CREATED", event);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.notifications.payment_calculated", durable = "true"),
                    exchange = @Exchange(name = "payment-fanout", type = "fanout")
            )
    )
    public void handlePaymentCalculated(PaymentCalculatedEvent event) {
        log.info("Received PaymentCalculatedEvent: {}", event);
        sendNotification("PAYMENT_CALCULATED", event);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.notifications.booking_paid", durable = "true"),
                    exchange = @Exchange(name = "ski-rental-exchange", type = "topic"),
                    key = "booking.paid"
            )
    )
    public void handleBookingPaid(BookingPaidEvent event) {
        log.info("Received BookingPaidEvent: {}", event);
        sendNotification("BOOKING_PAID", event);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.notifications.skipass_activated", durable = "true"),
                    exchange = @Exchange(name = "ski-rental-exchange", type = "topic"),
                    key = "skipass.activated"
            )
    )
    public void handleSkipassActivated(SkipassActivatedEvent event) {
        log.info("Received SkipassActivatedEvent: {}", event);
        sendNotification("SKIPASS_ACTIVATED", event);
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.notifications.skipass_used", durable = "true"),
                    exchange = @Exchange(name = "ski-rental-exchange", type = "topic"),
                    key = "skipass.used"
            )
    )
    public void handleSkipassUsed(SkipassUsedEvent event) {
        log.info("Received SkipassUsedEvent: {}", event);
        sendNotification("SKIPASS_USED", event);
    }

    private void sendNotification(String type, Object payload) {
        try {
            Map<String, Object> message = new HashMap<>();
            message.put("type", type);
            message.put("payload", payload);
            
            String jsonMessage = objectMapper.writeValueAsString(message);
            notificationHandler.broadcast(jsonMessage);
        } catch (JsonProcessingException e) {
            log.error("Error creating JSON notification", e);
        }
    }
}