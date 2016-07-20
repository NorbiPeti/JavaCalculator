package calculator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

public class Gui {
	private JFrame frame;
	private JPanel panel;
	private JTextField tf;

	public static final String TYPE_CHARS = "+-*/.()";

	public Gui() throws Exception {
		frame = new JFrame();
		frame.setMinimumSize(new Dimension(300, 400));
		frame.setTitle("Calculator");
		frame.setLayout(new BorderLayout(10, 10));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				for (int i = 0; i < TYPE_CHARS.length(); i++) {
					if (e.getKeyChar() == TYPE_CHARS.charAt(i)) {
						tf.setText(tf.getText() + TYPE_CHARS.charAt(i));
						return;
					}
				}
				if (e.getKeyChar() == '\n') {
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
		addAppendButtons("123+");
		addButton('C').addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (tf.getText().length() > 0)
					tf.setText(tf.getText().substring(0, tf.getText().length() - 1));
			}
		});
		addAppendButtons("456-(789*).0");
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
			if (tf.getText().length() == 0) {
				tf.setText("0.0");
				return;
			}
			String result = Calc.calculate(tf.getText());
			String errormsg = "Unbalanced parenthesis: ";
			int openc = result.length() - result.replace("(", "").length();
			int closec = result.length() - result.replace(")", "").length();
			if (openc - closec > 0)
				errormsg += "There is " + (openc - closec) + " more of ( than )";
			else if (openc - closec < 0) {
				errormsg += "There is " + (closec - openc) + " more of ) than (";
			} else
				errormsg = "";
			if (errormsg.length() == 0)
				tf.setText(Double.toString(Double.parseDouble(result)));
			else
				JOptionPane.showMessageDialog(frame, errormsg, "Error!", JOptionPane.WARNING_MESSAGE);
		} catch (ArithmeticException e) {
			JOptionPane.showMessageDialog(frame, "Arithmetic error: " + e.getMessage(), "Error!",
					JOptionPane.WARNING_MESSAGE);
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(frame,
					"Number format error: " + e.getMessage() + "\n\nThe number may be too large or there's a mistype.",
					"Error!", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frame, "Unknown error!\n" + e, "Error!", JOptionPane.WARNING_MESSAGE);
		}
	}
}
