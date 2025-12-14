package vvikobra.miit.analytics.listeners;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import vvikobra.events.BookingCreatedEvent;
import vvikobra.events.BookingPaidEvent;
import vvikobra.miit.analytics.service.AnalyticsService;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class BookingEventListener {
    private static final Logger log = LoggerFactory.getLogger(BookingEventListener.class);
    private static final String EXCHANGE_NAME = "ski-rental-exchange";
    private static final String CREATED_QUEUE = "booking-created-queue";
    private static final String PAID_QUEUE = "booking-paid-queue";
    private static final String DLX = "ski-rental-dlx";

    private final Set<UUID> processedPaidBookings = ConcurrentHashMap.newKeySet();
    private final AnalyticsService analyticsService;

    public BookingEventListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = CREATED_QUEUE,
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = DLX),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.booking.created")
                            }),
                    exchange = @Exchange(name = EXCHANGE_NAME, type = "topic"),
                    key = "booking.created"
            )
    )
    public void handleBookingCreatedEvent(
            @Payload BookingCreatedEvent event,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws IOException {
        try {
            log.info("Received BookingCreatedEvent: {}", event);
            analyticsService.onBookingCreated(
                    event.totalCost(),
                    event.startDate(),
                    event.endDate(),
                    event.equipmentTypes()
            );
            channel.basicAck(tag, false);
        } catch (Exception e) {
            log.error("Failed to process BookingCreatedEvent: {}", event, e);
            channel.basicNack(tag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = PAID_QUEUE,
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = DLX),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.booking.paid")
                            }),
                    exchange = @Exchange(name = EXCHANGE_NAME, type = "topic"),
                    key = "booking.paid"
            )
    )
    public void handleBookingPaidEvent(
            @Payload BookingPaidEvent event,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws IOException {

        try {
            if (!processedPaidBookings.add(UUID.fromString(event.bookingId()))) {
                log.warn("Duplicate BookingPaidEvent received for bookingId: {}", event.bookingId());
                channel.basicAck(tag, false);
                return;
            }
            analyticsService.onBookingPaid();
            log.info("Received BookingPaidEvent: {}", event);

            channel.basicAck(tag, false);

        } catch (Exception e) {
            log.error("Failed to process BookingPaidEvent: {}. Message moved to DLQ.", event, e);

            channel.basicNack(tag, false, false);
        }
    }
}