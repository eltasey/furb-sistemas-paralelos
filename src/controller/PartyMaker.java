package controller;

import java.util.ArrayList;
import java.util.List;

public class PartyMaker {

	Listener listner;

	public PartyMaker(Listener listner) {
		this.listner = listner;
	}

	public synchronized void process() {
		while (true) {
			List<String> party = new ArrayList<>(Constantes.SERVER_CAPACITY);
			while (party.size() < Constantes.SERVER_CAPACITY) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				String fisrtPlayer = listner.getFisrtPlayer();
				party.add(fisrtPlayer);
				System.out.println("Player " + fisrtPlayer + " entrou para o grupo. " + party.toString());

			}
			System.out.println("Grupo esperando servidor: " + party.toString() + ".");
			listner.connect(party);
		}

	}

	public synchronized void onPlayerAdded() {
		notify();
	}

}
