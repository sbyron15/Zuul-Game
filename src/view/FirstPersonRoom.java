package view;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class FirstPersonRoom extends MapRoom {
	
	public FirstPersonRoom(String description) {
		super(description);
	}
	
	public JPanel getView(String direction) {
		
		JPanel panel = new JPanel();
		
		panel.setLayout(null);
		
		if (getExit(direction) != null) {
			JLabel roomLabel = new JLabel(getExit(direction).getDescription());
			roomLabel.setHorizontalAlignment(SwingConstants.CENTER);
			roomLabel.setForeground(Color.WHITE);
			roomLabel.setBounds(238, 280, 141, 16);
			panel.add(roomLabel);
			
			JLabel door = new JLabel("");
			door.setIcon(new ImageIcon(FirstPersonRoom.class.getResource("/img/firstperson/door.png")));
			door.setBounds(219, 253, 180, 250);
			panel.add(door);
		}
		
		JLabel room = new JLabel("");
		room.setIcon(new ImageIcon(FirstPersonRoom.class.getResource("/img/firstperson/room.png")));
		room.setBounds(0, 0, 600, 600);
		panel.add(room);
		
		return panel;
	}

}
