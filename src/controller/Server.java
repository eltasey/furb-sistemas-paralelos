package controller;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {

	Lock lock = new ReentrantLock();
	Condition onGame = lock.newCondition();
	private List<String> players;

	public void connect(List<String> players) {
		lock.lock();
		this.players = players;
		System.out.println("Jogadores Conectados: " + players.toString());
		if (players.size() == Constantes.SERVER_CAPACITY) {
			try {
				onGame.await(30, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		disconnect();
	}

	private void disconnect() {
		System.out.println("Jogadores Desconectados: " + players.toString());
		onGame.signal();
	}

}
