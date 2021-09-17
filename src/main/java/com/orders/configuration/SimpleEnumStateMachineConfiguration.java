package com.orders.configuration;

import com.orders.api.resource.OrderEvents;
import com.orders.api.resource.OrderStates;
import lombok.extern.java.Log;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.guard.Guard;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.Collection;

@Log
@Configuration
@EnableStateMachineFactory(name = "factory_config")
public class SimpleEnumStateMachineConfiguration extends StateMachineConfigurerAdapter<OrderStates, OrderEvents> {


    public static final String ORDER_ID = "orderId";

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderStates, OrderEvents> transitions) throws Exception {
        transitions
                .withExternal()
                .source(OrderStates.SUBMITTED).target(OrderStates.PAID).event(OrderEvents.PAY).guard(guardPayTransition())
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.FULLFILLED).event(OrderEvents.FULFILL).action(printStateIdAction())
                .and()
                .withExternal().source(OrderStates.SUBMITTED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrderStates.PAID).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL)
                .and()
                .withExternal().source(OrderStates.FULLFILLED).target(OrderStates.CANCELLED).event(OrderEvents.CANCEL);
    }

    private Action<OrderStates, OrderEvents> printStateIdAction() {
        return ctx -> log.info(String.valueOf(ctx.getTarget().getId()));
    }

    @Override
    public void configure(StateMachineStateConfigurer<OrderStates, OrderEvents> states) throws Exception {
        states.withStates()
                .initial(OrderStates.SUBMITTED).stateEntry(OrderStates.SUBMITTED, stateContext -> {
                    final Long orderId = (Long) stateContext.getExtendedState().getVariables().getOrDefault(ORDER_ID, -1L);
                    log.info("entering submitted state! " + orderId);
                })
                .state(OrderStates.PAID/*, (Collection<? extends Action<OrderStates, OrderEvents>>) countNumbersOfInvokeStatePaid()*/)
                .end(OrderStates.FULLFILLED)
                .end(OrderStates.CANCELLED);
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<OrderStates, OrderEvents> config) throws Exception {
        StateMachineListenerAdapter<OrderStates, OrderEvents> adapter = new StateMachineListenerAdapter<OrderStates, OrderEvents>() {
            @Override
            public void stateChanged(State<OrderStates, OrderEvents> from, State<OrderStates, OrderEvents> to) {
                log.info("state changed from: " + from.getId().name() + " to: " + to.getId().name());

            }
        };
        config.withConfiguration()
                .autoStartup(false)
                .listener(adapter);
        //new StateMachineListener()
    }

    @Bean
    public Guard<OrderStates, OrderEvents> simpleGuard() {
        log.info("Guard Paid event");
        return ctx -> (int) ctx.getMessage()
                .getHeaders()
                .getOrDefault(ORDER_ID, 0) > 0;
    }

    @Bean
    public Guard<OrderStates, OrderEvents> guardPayTransition() {
        return stateContext -> {
            try {
                final Long orderId = (Long) stateContext.getMessage().getHeaders().getOrDefault(ORDER_ID, -1);
                log.info("orderID: " + orderId);
                final String paymentConfirmationNumber = (String) stateContext.getMessage().getHeaders().getOrDefault("paymentConfirmationNumber", -1);
                log.info("paymentConfirmationNumber: " + paymentConfirmationNumber);
                return orderId != -1 && !paymentConfirmationNumber.equals("-1");
            } catch (Exception exception) {
                log.info("blad");
            }
            return false;
        };
    }
}
