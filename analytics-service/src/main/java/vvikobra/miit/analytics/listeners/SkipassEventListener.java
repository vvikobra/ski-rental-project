package vvikobra.miit.analytics.listeners;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import vvikobra.events.SkipassActivatedEvent;
import vvikobra.events.SkipassUsedEvent;
import vvikobra.miit.analytics.service.AnalyticsService;

import java.io.IOException;

@Component
public class SkipassEventListener {

    private static final Logger log = LoggerFactory.getLogger(SkipassEventListener.class);
    private static final String EXCHANGE_NAME = "ski-rental-exchange";
    private static final String ACTIVATED_QUEUE = "skipass-activated-queue";
    private static final String USED_QUEUE = "skipass-used-queue";
    private static final String DLX = "ski-rental-dlx";
    private final AnalyticsService analyticsService;

    public SkipassEventListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = ACTIVATED_QUEUE,
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = DLX),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.skipass.activated")
                            }),
                    exchange = @Exchange(name = EXCHANGE_NAME, type = "topic"),
                    key = "skipass.activated"
            )
    )
    public void handleSkipassActivatedEvent(
            @Payload SkipassActivatedEvent event,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws IOException {
        try {
            log.info("Received SkipassActivatedEvent: {}", event);
            analyticsService.onSkipassActivated();
            channel.basicAck(tag, false);

        } catch (Exception e) {
            log.error("Failed to process SkipassActivatedEvent: {}", event, e);
            channel.basicNack(tag, false, false);
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(
                            name = USED_QUEUE,
                            durable = "true",
                            arguments = {
                                    @Argument(name = "x-dead-letter-exchange", value = DLX),
                                    @Argument(name = "x-dead-letter-routing-key", value = "dlq.skipass.used")
                            }),
                    exchange = @Exchange(name = EXCHANGE_NAME, type = "topic"),
                    key = "skipass.used"
            )
    )
    public void handleSkipassUsedEvent(
            @Payload SkipassUsedEvent event,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag
    ) throws IOException {
        try {
            log.info("Received SkipassUsedEvent: {}", event);
            analyticsService.onSkipassUsed(event.usedAt());
            channel.basicAck(tag, false);

        } catch (Exception e) {
            log.error("Failed to process SkipassUsedEvent: {}", event, e);
            channel.basicNack(tag, false, false);
        }
    }
}