import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Demo {

	public static void main(String[] args) {

		double[][] probabilities = new double[5][5];

		probabilities[0][0] = 0;
		probabilities[0][1] = 0;
		probabilities[0][2] = 0;
		probabilities[0][3] = 0;
		probabilities[0][4] = 0;

		probabilities[1][0] = 0;
		probabilities[1][1] = 0.1;
		probabilities[1][2] = 0.1;
		probabilities[1][3] = 0.1;
		probabilities[1][4] = 0;

		probabilities[2][0] = 0;
		probabilities[2][1] = 0.1;
		probabilities[2][2] = 0.9;
		probabilities[2][3] = 0;
		probabilities[2][4] = 0;

		probabilities[3][0] = 0;
		probabilities[3][1] = 0.1;
		probabilities[3][2] = 0;
		probabilities[3][3] = 0;
		probabilities[3][4] = 0;

		probabilities[4][0] = 0;
		probabilities[4][1] = 0;
		probabilities[4][2] = 0;
		probabilities[4][3] = 0;
		probabilities[4][4] = 0;

		probabilities = compute_next_stage(probabilities, 1, 1, 1, 1);
		for (double[] row : probabilities) {
			System.out.println(Arrays.toString(row));
		}

	}

	private static double[][] compute_next_stage(double[][] prob, int e_e,
			int e_ne, int ne_e, int ne_ne) {
		double[][] res = new double[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				List<Double> adj = compute_adj(i, j, prob);
				double a = compute_a(prob[i][j], e_e, e_ne, adj);
				double b = compute_b(prob[i][j], ne_e, ne_ne, adj);
				res[i][j] = a / (a + b);

			}
		}
		return res;
	}

	private static double compute_a(double prob_i, int e_e, int e_ne,
			List<Double> adj) {
		double q = 0;
		for (Double prob_j : adj) {
			q += (e_ne * (1 - prob_j) + e_e * (prob_j));
		}
		return q * prob_i;
	}

	private static double compute_b(double prob_i, int ne_e, int ne_ne,
			List<Double> adj) {
		double q = 0;
		for (Double prob_j : adj) {
			q += (ne_e * prob_j + ne_ne * (1 - prob_j));
		}
		return q * (1 - prob_i);
	}

	private static List<Double> compute_adj(int x, int y, double[][] array) {

		List<Double> res = new ArrayList<Double>();

		for (int dr = -1; dr <= +1; dr++) {
			for (int dc = -1; dc <= +1; dc++) {
				int r = x + dr;
				int c = y + dc;

				if ((r >= 0) && (r < array.length) && (c >= 0)
						&& (c < array[0].length)) {
					// skip p
					if ((dr != 0) || (dc != 0))
						res.add(array[r][c]);
				}
			}
		}
		return res;

	}

}
