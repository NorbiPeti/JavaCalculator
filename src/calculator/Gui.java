package calculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
		frame.setMinimumSize(new Dimension(300, 400));
		frame.setTitle("Calculator");
		frame.setLayout(new BorderLayout(10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '+')
					tf.setText(tf.getText() + '+');
				else if (e.getKeyChar() == '-')
					tf.setText(tf.getText() + "-");
				else if (e.getKeyChar() == '*')
					tf.setText(tf.getText() + "*");
				else if (e.getKeyChar() == '/')
					tf.setText(tf.getText() + "/");
				else if (e.getKeyChar() == '.')
					tf.setText(tf.getText() + ".");
				else if (e.getKeyChar() == '\n') {
					calc();
				} else if (e.getKeyChar() == '\b' && tf.getText().length() > 0)
					tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
				else if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9')
					tf.setText(tf.getText() + e.getKeyChar());
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		tf = new JTextField();
		tf.setFocusable(false);
		frame.add(tf, BorderLayout.NORTH);
		panel = new JPanel();
		frame.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(4, 4));
		String str = addNumbers("+-*");
		str += ".0";
		addAppendButtons(str);
		addButton('=').addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				calc();
			}
		});
		addAppendButtons("/");
	}

	public void show() {
		frame.setVisible(true);
	}

	public void close() {
		frame.dispose();
	}

	private String addNumbers(String rowend) {
		String str = "";
		int x = 0;
		for (int i = 1; i < 10; i++) {
			str += i;
			if (i % 3 == 0)
				str += rowend.charAt(x++);
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

	private void calc() {
		try {
			Double ret = Calc.calculate(tf.getText());
			if (ret == null)
				JOptionPane.showMessageDialog(frame, "Unable to convert result to number!", "Error!",
						JOptionPane.WARNING_MESSAGE);
			else
				tf.setText(Double.toString(ret));
		} catch (ArithmeticException e) {
			JOptionPane.showMessageDialog(frame, "Arithmetic error: " + e.getMessage(), "Error!",
					JOptionPane.WARNING_MESSAGE);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame,
					"Number format error: " + e.getMessage() + "\n\nThe number may be too large.", "Error!",
					JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Unknown error!\n" + e, "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}
}
