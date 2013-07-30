import java.util.Scanner;
// Chris Shafer cs 3330 final bonus
public class Sequencer {
	char[] sequenceOne;
	char[] sequenceTwo;
	int[][] matrix;
	String sequenceOneString = "";
	String sequenceTwoString = "";
	/*
	 * this sequencer uses the needleman wunsch algorithim for alignment
	 * run it by inputing the sequences in the console in all caps.
	 * example input as follows
	 * 
	 * GAATTCAGTTA
	 * GGATCAGATA
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		String firstSequence;
		String secondSequence;

		System.out.print("Input Sequence 1:");
		firstSequence = input.nextLine();
		System.out.print("Input Sequence 2:");
		secondSequence = input.nextLine();

		Sequencer sequencer = new Sequencer();
		sequencer.initalize(firstSequence.toUpperCase().replace(" ", "")
				.toCharArray(), secondSequence.toUpperCase().replace(" ", "")
				.toCharArray());
		sequencer.process();
		sequencer.backtrace();
		sequencer.printAlignment();
	}

	void initalize(char[] seqA, char[] seqB) {
		sequenceOne = seqA;
		sequenceTwo = seqB;
		matrix = new int[sequenceOne.length + 1][sequenceTwo.length + 1];
		for (int i = 0; i <= sequenceOne.length; i++) {
			for (int x = 0; x <= sequenceTwo.length; x++) {
				if (i == 0) {
					matrix[i][x] = -x;
				} else if (x == 0) {
					matrix[i][x] = -i;
				} else {
					matrix[i][x] = 0;
				}
			}
		}
	}

	private int weight(int i, int x) {
		if (sequenceOne[i - 1] == sequenceTwo[x - 1]) {
			return 1;
		} else {
			return -1;
		}
	}

	void printAlignment() {
		System.out.println("Sequence 1: " + sequenceOneString);
		System.out.println("Sequence 2: " + sequenceTwoString);
		System.out.println();
	}

	void process() {
		for (int i = 1; i <= sequenceOne.length; i++) {
			for (int x = 1; x <= sequenceTwo.length; x++) {
				int scoreDiag = matrix[i - 1][x - 1] + weight(i, x);
				int scoreLeft = matrix[i][x - 1] - 1;
				int scoreUp = matrix[i - 1][x] - 1;
				matrix[i][x] = Math
						.max(Math.max(scoreDiag, scoreLeft), scoreUp);
			}
		}
	}

	void backtrace() {
		int i = sequenceOne.length;
		int x = sequenceTwo.length;
		while (i > 0 && x > 0) {
			if (matrix[i][x] == matrix[i - 1][x - 1] + weight(i, x)) {
				sequenceOneString += sequenceOne[i - 1];
				sequenceTwoString += sequenceTwo[x - 1];
				i--;
				x--;
				continue;
			} else if (matrix[i][x] == matrix[i][x - 1] - 1) {
				sequenceOneString += "_";
				sequenceTwoString += sequenceTwo[x - 1];
				x--;
				continue;
			} else {
				sequenceOneString += sequenceOne[i - 1];
				sequenceTwoString += "_";
				i--;
				continue;
			}
		}
		sequenceOneString = new StringBuffer(sequenceOneString).reverse()
				.toString();
		sequenceTwoString = new StringBuffer(sequenceTwoString).reverse()
				.toString();
	}

}