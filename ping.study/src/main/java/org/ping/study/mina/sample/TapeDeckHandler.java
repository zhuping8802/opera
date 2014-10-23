/**
 * typename: TapeDeckHandler
 * filename: TapeDeckHandler.java
 * description: TODO
 * date: 2014年10月23日下午9:36:56
 * auth: ping
 */
package org.ping.study.mina.sample;

import org.apache.mina.statemachine.StateMachine;
import org.apache.mina.statemachine.StateMachineFactory;
import org.apache.mina.statemachine.StateMachineProxyBuilder;
import org.apache.mina.statemachine.annotation.State;
import org.apache.mina.statemachine.annotation.Transition;
import org.apache.mina.statemachine.annotation.Transitions;
import org.apache.mina.statemachine.event.Event;

/**
 * typename: TapeDeckHandler 
 * filename: TapeDeckHandler.java 
 * description: TODO 
 * date: 2014年10月23日下午9:36:56
 * auth: ping  
 */
public class TapeDeckHandler {

	@State
	public static final String ROOT = "Root";
	@State(ROOT)
	public static final String EMPTY = "Empty";
	@State(ROOT)
	public static final String LOADED = "Loaded";
	@State(ROOT)
	public static final String PLAYING = "Playing";
	@State(ROOT)
	public static final String PAUSED = "Paused";

	@Transition(on = "*", in = ROOT)
	public void error(Event event) {
		System.out.println("Cannot '" + event.getId() + "' at this time");
	}

	@Transition(on = "load", in = EMPTY, next = LOADED)
	public void loadTape(String nameOfTape) {
		System.out.println("Tape '" + nameOfTape + "' loaded");
	}

	@Transitions({ @Transition(on = "play", in = LOADED, next = PLAYING),
			@Transition(on = "play", in = PAUSED, next = PLAYING) })
	public void playTape() {
		System.out.println("Playing tape");
	}

	@Transition(on = "pause", in = PLAYING, next = PAUSED)
	public void pauseTape() {
		System.out.println("Tape paused");
	}

	@Transition(on = "stop", in = PLAYING, next = LOADED)
	public void stopTape() {
		System.out.println("Tape stopped");
	}

	@Transition(on = "eject", in = LOADED, next = EMPTY)
	public void ejectTape() {
		System.out.println("Tape ejected");
	}

	public static void main(String[] args) {
		TapeDeckHandler handler = new TapeDeckHandler();
		StateMachine sm = StateMachineFactory.getInstance(Transition.class)
				.create(TapeDeckHandler.EMPTY, handler);
		TapeDeck deck = new StateMachineProxyBuilder().create(TapeDeck.class,
				sm);

//		deck.load("The Knife - Silent Shout");
		deck.play();
		deck.pause();
		deck.play();
		deck.stop();
		deck.eject();
	}
}
