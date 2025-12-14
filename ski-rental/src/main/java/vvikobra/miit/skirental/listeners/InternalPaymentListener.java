package vvikobra.miit.skirental.listeners;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import vvikobra.events.PaymentCalculatedEvent;

@Component
public class InternalPaymentListener {

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(name = "q.booking.payment", durable = "true"),
                    exchange = @Exchange(name = "payment-fanout", type = "fanout")
            )
    )
    public void handleInternalPayment(PaymentCalculatedEvent event) {
        System.out.printf(
                "INTERNAL PAYMENT LISTENER: received payment result = %.2f", event.finalPrice()
        );
    }
}