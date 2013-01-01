package game;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;

public class TilingGameApplet extends JApplet implements ActionListener {

	public void init() {
		getContentPane().setLayout(new BorderLayout());
		JButton start = new JButton("Start Tiling Game");
		start.setFont(new Font("Dialog", Font.BOLD, 24));
		start.addActionListener(this);
		getContentPane().add(start);
	}

	public void actionPerformed(ActionEvent evt) {
		Main.main(new String[0]);
	}

}
