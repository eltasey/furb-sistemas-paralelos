package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import controller.Listener;
import controller.PartyMaker;
import controller.Queue;
import controller.Server;

public class Window extends JFrame implements Listener {

	public static void main(String[] args) {
		Window window = new Window();
		EventQueue.invokeLater(() -> window.setVisible(true));
	}

	JButton btOpenConnection;
	JTextField tfPlayerName;
	JLabel lPlayerName;
	Server server = new Server();

	Queue queue = new Queue();
	PartyMaker partyMaker = new PartyMaker(this);
	Thread ctrl = new Thread(() -> partyMaker.process());

	Window() {
		lPlayerName = new JLabel("Player name:");

		tfPlayerName = new JTextField();
		btOpenConnection = new JButton("Nova conexão");
		btOpenConnection.addActionListener(l -> openConnection());

		lPlayerName.setBounds(10, 10, 90, 30);
		tfPlayerName.setBounds(100, 10, 90, 30);
		btOpenConnection.setBounds(210, 10, 200, 30);

		add(lPlayerName);
		add(tfPlayerName);
		add(btOpenConnection);

		setTitle("Wait and notify");
		setResizable(false);
		setLayout(null);
		setSize(425, 100);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ctrl.start();
	}

	void openConnection() {
		queue.playerConect(tfPlayerName.getText());
		partyMaker.onPlayerAdded();
	}

	@Override
	public String getFisrtPlayer() {
		return queue.getNextPlayer();
	}

	@Override
	public void connect(List<String> party) {
		new Thread(() -> server.connect(party)).start();;
	}

}
