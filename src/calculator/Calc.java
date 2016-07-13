package calculator;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {

	private static final Pattern MULTIPLY = Pattern.compile("([0-9\\.]+)\\*([0-9\\.]+)");
	private static final Pattern DIVIDE = Pattern.compile("([0-9\\.]+)\\/([0-9\\.]+)");
	private static final Pattern ADD = Pattern.compile("([0-9\\.]+)\\+([0-9\\.]+)");
	private static final Pattern SUBTRACT = Pattern.compile("([0-9\\.]+)\\-([0-9\\.]+)");

	public static Double calculate(String text) {
		StringBuffer buf = new StringBuffer(text);
		replace(buf, MULTIPLY, (a, b) -> a * b);
		replace(buf, DIVIDE, (a, b) -> a / b);
		replace(buf, ADD, (a, b) -> a + b);
		replace(buf, SUBTRACT, (a, b) -> a - b);
		try {
			return Double.parseDouble(buf.toString());
		} catch (Exception e) {
			return null;
		}
	}

	private static void replace(StringBuffer buf, Pattern pattern, BiFunction<Double, Double, Double> doit) {
		while (true) {
			Matcher matcher = pattern.matcher(buf.toString());
			if (!matcher.find())
				break;
			buf.replace(matcher.start(), matcher.end(), Double
					.toString(doit.apply(Double.parseDouble(matcher.group(1)), Double.parseDouble(matcher.group(2)))));
		}
	}
}
