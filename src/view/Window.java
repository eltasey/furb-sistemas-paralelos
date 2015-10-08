package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import controller.ServerConnectionListener;
import controller.ServerCtrl;

public class Window extends JFrame implements ServerConnectionListener {

	public static void main(String[] args) {
		Window window = new Window();
		ServerCtrl serverCtrl = new ServerCtrl();
		serverCtrl.setConnectionListener(window);
		window.addController(serverCtrl);

		EventQueue.invokeLater(() -> window.setVisible(true));
	}

	JButton btOpenConnection;
	JButton btCloseConnection;
	JTextField tfPlayerName;
	JTextArea taServerDisplay;
	JLabel lPlayerName;

	ServerCtrl ctrl;

	Window() {
		lPlayerName = new JLabel("Player name:");
		taServerDisplay = new JTextArea();
		taServerDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		tfPlayerName = new JTextField();
		btOpenConnection = new JButton("Nova conexão");
		btCloseConnection = new JButton("Fechar conexão mais antiga");
		btOpenConnection.addActionListener(l -> openConnection());
		btCloseConnection.addActionListener(l -> closeConnection());

		lPlayerName.setBounds(10, 10, 90, 30);
		taServerDisplay.setBounds(10, 90, 400, 400);
		tfPlayerName.setBounds(100, 10, 90, 30);
		btOpenConnection.setBounds(210, 10, 200, 30);
		btCloseConnection.setBounds(210, 50, 200, 30);
		

		add(lPlayerName);
		add(taServerDisplay);
		add(tfPlayerName);
		add(btOpenConnection);
		add(btCloseConnection);

		setTitle("Wait and notify");
		setResizable(false);
		setLayout(null);
		setSize(425, 525);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void onConnectionWaiting(String player) {
		taServerDisplay.append(player + " on queue.\n");
	}
	
	public void onConnectionEstablished(String player){
		taServerDisplay.append(player +" connected.\n");
	}
	
	public void onConnectionEnded(String player){
		taServerDisplay.append(player + " disconnected.\n");
	}

	void openConnection() {
		new Thread(() -> ctrl.openConnection(tfPlayerName.getText())).start();
	}

	void closeConnection() {
		ctrl.closeConnection();
	}

	void addController(ServerCtrl ctrl) {
		this.ctrl = ctrl;
	}
}
