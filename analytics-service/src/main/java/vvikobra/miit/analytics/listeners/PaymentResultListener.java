package vvikobra.miit.analytics.listeners;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vvikobra.events.PaymentCalculatedEvent;
import vvikobra.miit.analytics.service.AnalyticsService;

@Component
public class PaymentResultListener {

    private final AnalyticsService analyticsService;

    public PaymentResultListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.booking.payment.log", durable = "true"),
                    exchange = @Exchange(name = "payment-fanout", type = "fanout")
            )
    )
    public void handlePayment(PaymentCalculatedEvent event) {
        analyticsService.onPaymentCalculated(event.finalPrice());
        System.out.printf(
                "PAYMENT EVENT LOG: total price = %.2f\n", event.finalPrice()
        );
    }
}