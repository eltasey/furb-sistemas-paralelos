package controller;

public interface ServerConnectionListener {

	void onConnectionWaiting(String player);
	void onConnectionEstablished(String player);
	void onConnectionEnded(String player);
}
