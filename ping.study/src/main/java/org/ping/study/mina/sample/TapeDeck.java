/**
 * typename: TapeDeck
 * filename: TapeDeck.java
 * description: TODO
 * date: 2014年10月23日下午9:35:53
 * auth: ping
 */
package org.ping.study.mina.sample;

/**
 * typename: TapeDeck 
 * filename: TapeDeck.java 
 * description: TODO 
 * date: 2014年10月23日下午9:35:53
 * auth: ping  
 */
public interface TapeDeck {

	void load(String nameOfTape);

	void eject();

	void pause();

	void stop();
	
	void play();
}
