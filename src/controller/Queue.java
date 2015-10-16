package controller;

import java.util.Vector;

public class Queue {

	private Vector<String> players;

	public Queue() {
		players = new Vector<String>();
	}

	public synchronized void playerConect(String player) {
		System.out.println("Player " + player + " entrou na fila.");
		players.addElement(player);
	}

	public String getNextPlayer() {
		String firstElement = players.firstElement();
		players.remove(firstElement);
		return firstElement;
	}

}
