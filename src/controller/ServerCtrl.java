package controller;

import java.util.Vector;

public class ServerCtrl {

	static final int SERVER_CAPACITY = 5;

	private Vector<String> players;;

	private ServerConnectionListener serverConnectionListener;

	public ServerCtrl() {
		players = new Vector<String>();
	}
	
	public void setConnectionListener(ServerConnectionListener listener){
		this.serverConnectionListener = listener;
	}

	public synchronized void openConnection(String player) {
		try {
			if(players.size() == SERVER_CAPACITY){
				serverConnectionListener.onConnectionWaiting(player);
			}
			while (players.size() == SERVER_CAPACITY) {
				wait();
			}
			
			players.addElement(player);
			serverConnectionListener.onConnectionEstablished(player);
			notify();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public synchronized void closeConnection() {
		if(!players.isEmpty()){
			String message = (String) players.firstElement();
			players.removeElement(message);
			
			notify();
			
			serverConnectionListener.onConnectionEnded(message);
		}
	}

}
