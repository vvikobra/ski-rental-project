package vvikobra.miit.skirental.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "ski-rental-exchange";
    public static final String ROUTING_KEY_BOOKING_CREATED = "booking.created";
    public static final String ROUTING_KEY_BOOKING_PAID = "booking.paid";
    public static final String ROUTING_KEY_SKIPASS_ACTIVATED = "skipass.activated";
    public static final String ROUTING_KEY_SKIPASS_USED = "skipass.used";
    public static final String PAYMENT_FANOUT = "payment-fanout";

    @Bean
    public TopicExchange skiRentalExchange() {
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public FanoutExchange paymentFanoutExchange() {
        return new FanoutExchange(PAYMENT_FANOUT, true, false);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter(new ObjectMapper().findAndRegisterModules());
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
//                                         Jackson2JsonMessageConverter messageConverter) {
//
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(messageConverter);
//
//        template.setMandatory(true);
//
//        template.setConfirmCallback((correlationData, ack, cause) -> {
//            if (!ack) {
//                System.err.println("NACK: message delivery failed: " + cause);
//            }
//        });
//
//        return template;
//    }
}