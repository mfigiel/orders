package com.orders.services;

import com.orders.api.resource.OrderEvents;
import com.orders.api.resource.OrderStates;
import com.orders.api.resource.UpdateStateOrderApi;
import com.orders.api.resource.exception.StateNotFoundException;
import com.orders.repository.OrderRepository;
import com.orders.repository.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderStateService {

    private static final String ORDER_ID = "orderId";
    private final OrderRepository orderRepository;
    private final StateMachineFactory<OrderStates, OrderEvents> factoryConfig;

    public void updateState(UpdateStateOrderApi updateStateOrderApi) {
        final OrderEvents orderEvent = updateStateOrderApi.getOrderEvents();
        switch (orderEvent) {
            case FULFILL:
                fullFill(updateStateOrderApi.getOrderId());
                break;
            case PAY:
                pay(updateStateOrderApi.getOrderId(), updateStateOrderApi.getPaymentConfirmationNumber());
                break;
            case CANCEL:
                cancel(updateStateOrderApi.getOrderId());
                break;
            default:
                throw new StateNotFoundException("Incorrect state order, allowed orderEvents is: fullFill,pay or cancel, sended order event: " + orderEvent);
        }
    }

    private void fullFill(Long orderId) {
        StateMachine<OrderStates, OrderEvents> sm = this.build(orderId);

        final Message<OrderEvents> fullFillmentMessage = MessageBuilder.withPayload(OrderEvents.FULFILL)
                .setHeader(ORDER_ID, orderId)
                .build();

        sm.sendEvent(fullFillmentMessage);
    }

    private void pay(Long orderId, String paymentConfirmationNumber) {
        StateMachine<OrderStates, OrderEvents> sm = this.build(orderId);

        final Message<OrderEvents> paymentMessage = MessageBuilder.withPayload(OrderEvents.PAY)
                .setHeader(ORDER_ID, orderId)
                .setHeader("paymentConfirmationNumber", paymentConfirmationNumber)
                .build();

        sm.sendEvent(paymentMessage);
    }

    private void cancel(Long orderId) {
        StateMachine<OrderStates, OrderEvents> sm = this.build(orderId);

        final Message<OrderEvents> paymentMessage = MessageBuilder.withPayload(OrderEvents.CANCEL)
                .setHeader(ORDER_ID, orderId)
                .build();

        sm.sendEvent(paymentMessage);
    }

    private StateMachine<OrderStates, OrderEvents> build(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        StateMachine<OrderStates, OrderEvents> sm = this.factoryConfig.getStateMachine(String.valueOf(orderId));
        sm.stop();

        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<OrderStates, OrderEvents>() {
                        @Override
                        public void preStateChange(State<OrderStates, OrderEvents> state, Message<OrderEvents> message, Transition<OrderStates, OrderEvents> transition, StateMachine<OrderStates, OrderEvents> stateMachine, StateMachine<OrderStates, OrderEvents> stateMachine1) {
                            Optional.ofNullable(message).flatMap(msg -> Optional.ofNullable(Long.class.cast(msg.getHeaders().getOrDefault(ORDER_ID, -1L)))).ifPresent(orderId1 -> {
                                final Order order1 = orderRepository.getOne(orderId1);
                                order1.setState(state.getId());
                                orderRepository.save(order1);
                            });
                        }
                    });
                    sma.resetStateMachine(new DefaultStateMachineContext<>(
                            OrderStates.valueOf(order.get().getState()), null, null, null));
                });

        sm.start();
        return sm;

    }
}
