package com.orders.logging;

import com.orders.api.resource.OrderEvents;
import com.orders.api.resource.OrderStates;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

public class StateMachineListener extends StateMachineListenerAdapter<OrderStates, OrderEvents> {

    @Override
    public void stateChanged(State from, State to) {
        System.out.printf("[LOG] Transitioned from %s to %s%n", from == null ? "none" : from.getId(), to.getId());
    }
}
