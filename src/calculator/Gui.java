package calculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Gui {
	private JFrame frame;
	private JPanel panel;
	private JTextField tf;

	public Gui() throws Exception {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(200, 400));
		frame.setTitle("Calculator");
		frame.setLayout(new BorderLayout(10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		tf = new JTextField();
		tf.setFocusable(false);
		frame.add(tf, BorderLayout.NORTH);
		panel = new JPanel();
		frame.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(5, 3));
		String str = addNumbers();
		str += "+0-*/";
		addAppendButtons(str);
		addButton('=').addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf.setText(Integer.toString(Calc.calculate(tf.getText())));
			}
		});
	}

	public void show() {
		frame.setVisible(true);
	}

	public void close() {
		frame.dispose();
	}

	private String addNumbers() {
		String str = "";
		for (int i = 1; i < 10; i++) {
			str += i;
		}
		return str;
	}

	private void addAppendButtons(String btns) {
		for (int i = 0; i < btns.length(); i++) {
			Character c = btns.charAt(i);
			JButton num = addButton(c);
			final Character number = c;
			num.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					tf.setText(tf.getText() + number);
				}
			});
		}
	}

	private JButton addButton(Character c) {
		JButton num = new JButton();
		num.setText(Character.toString(c));
		num.setFocusable(false);
		panel.add(num);
		return num;
	}
}
