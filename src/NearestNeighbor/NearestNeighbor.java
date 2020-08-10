/* August 11-Rachel Papiernik
 * The purpose of this program is to read the imported csv files, loads and parses the two files into separate arrays, classifies each testing file and computes accuracy.
 */
package NearestNeighbor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class NearestNeighbor {

	private static double[][] trainingSamples = new double[75][4];
	private static String[] trainingClassification = new String[75];

	private static double[][] testingSamples = new double[75][4];
	private static String[] testingClassification = new String[75];

	private static String[] testingClassificationAccurate = new String[75];

	public static void main(String[] args) {
		System.out.println("Programming Fundmentals ");
		System.out.println("Name:Rachel Papiernik ");
		System.out.println("Programming Assignment 3");

		System.out.println("What is your name?");
		Scanner reader = new Scanner(System.in);
		String name = reader.next();
		System.out.println("Hello " + name + "!");

		// name of training file is entered here
		System.out.println("Enter the name of the training file: ");
		String trainingFileName = reader.next();

		loadTrainingData(trainingFileName);

		// name of testing file is entered here
		System.out.println("Enter the name of the testing file: ");
		String testingFileName = reader.next();
		loadTestingData(testingFileName);

		analyzeTestSamples();
		// used to calculate accuracy
		calculateAccuracy();

		//closes the reader
		reader.close();
	}
	private static void calculateAccuracy() {
		int matchCount = 0;
		for (int samples = 0; samples < testingClassification.length; samples++) {
			if (testingClassification[samples].equalsIgnoreCase(testingClassificationAccurate[samples])) {
				matchCount++;
			}
		}
		double accuracy = (double) matchCount / (double) testingClassification.length;
		System.out.println(" matchCount : " + matchCount);
		System.out.println(" ACCURACY : " + accuracy);

	}

	private static void analyzeTestSamples() {
		System.out.println("EX#: TRUE LABEL,     PREDICTED LABEL");
		
		for (int i = 0; i < testingSamples.length; i++) {
			double testingSample[] = testingSamples[i];
			double smallestKnownDistance = 0.0;
			double currentDistance = 0.0;

			int smallestIndex = 0;
			for (int j = 0; j < trainingSamples.length; j++) {
				double trainingSample[] = trainingSamples[j];
				currentDistance = calculateDistance(testingSample, trainingSample);
				if (j == 0) {
					smallestKnownDistance = currentDistance;
				}
				if (currentDistance < smallestKnownDistance) {
					smallestKnownDistance = currentDistance;
					smallestIndex = j;
				}
			}
			testingClassificationAccurate[i] = trainingClassification[smallestIndex];
			
			System.out.println(i + 1 + ":" + testingClassification[i] + "      " + testingClassificationAccurate[i]);

		}
	}

	private static double calculateDistance(double[] testingSample, double[] trainingSample) {
		double distance = Math.sqrt(Math.pow(trainingSample[0] - testingSample[0], 2)
				+ Math.pow(trainingSample[1] - testingSample[1], 2) + Math.pow(trainingSample[2] - testingSample[2], 2)
				+ Math.pow(trainingSample[3] - testingSample[3], 2));
		return distance;
	}

	private static void loadTestingData(String testingFileNmae) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int rowCount = 0;
		try {
			br = new BufferedReader(new FileReader(testingFileNmae));
			while ((line = br.readLine()) != null) {
				String[] row = line.split(cvsSplitBy);
				testingSamples[rowCount][0] = Double.valueOf(row[0]);
				testingSamples[rowCount][1] = Double.valueOf(row[1]);
				testingSamples[rowCount][2] = Double.valueOf(row[2]);
				testingSamples[rowCount][3] = Double.valueOf(row[3]);
				testingClassification[rowCount] = row[4];
				rowCount++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private static void loadTrainingData(String trainingFileName) {
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int rowCount = 0;
		try {
			br = new BufferedReader(new FileReader(trainingFileName));
			while ((line = br.readLine()) != null) {
				String[] row = line.split(cvsSplitBy);
				trainingSamples[rowCount][0] = Double.valueOf(row[0]);
				trainingSamples[rowCount][1] = Double.valueOf(row[1]);
				trainingSamples[rowCount][2] = Double.valueOf(row[2]);
				trainingSamples[rowCount][3] = Double.valueOf(row[3]);
				trainingClassification[rowCount] = row[4];
				
				rowCount++;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}