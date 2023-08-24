package edu.eci.arsw.primefinder;

import java.util.*;

public class Main {
	private static final int numberOfThreads = 3;
	private static final int finalNumber = 30000000;
	private static List<Integer> primes = new LinkedList<Integer>();

	public static void main(String[] args) {
		int startIndex, endIndex;
		int chunkSize = finalNumber / numberOfThreads;

		ArrayList<PrimeFinderThread> threads = new ArrayList<PrimeFinderThread>();
		for (int i = 0; i < numberOfThreads; i++) {
			startIndex = i * chunkSize;
			endIndex = (i == numberOfThreads - 1) ? finalNumber : (i + 1) * chunkSize;
			PrimeFinderThread thread = new PrimeFinderThread(startIndex, endIndex, "Hilo N°" + (i + 1));
			threads.add(thread);
		}
		for (PrimeFinderThread thread : threads) {
			thread.start();
			thread.setStartTime(System.currentTimeMillis());
		}

		int count = 0;
		int countAlive = 0;
		while (countAlive <= numberOfThreads) {
			for (PrimeFinderThread thread : threads) {
				if (thread.getTime() >= 5 && count <= numberOfThreads) {
					count++;

					try {
						thread.wait();
						System.out.println("El " + thread.getName() + " encontró:" + thread.getPrimes().toString());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (!thread.isAlive()) {
					countAlive++;
				}
			}
			if (count == numberOfThreads) {
				System.out.println("Presione enter para continuar:");
				String entradaTeclado = "";
				Scanner entradaEscaner = new Scanner(System.in); // Creación de un objeto Scanner
				entradaTeclado = entradaEscaner.nextLine();
			}
		}

		// primes.addAll(thread.getPrimes());
		// System.out.println(primes.toString());

	}

}
