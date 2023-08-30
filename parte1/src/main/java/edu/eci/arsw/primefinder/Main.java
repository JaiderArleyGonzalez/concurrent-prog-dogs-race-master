package edu.eci.arsw.primefinder;

import java.util.*;

public class Main {
	private static final int numberOfThreads = 3;
	private static final int finalNumber = 30000000;
	

	public static void main(String[] args) {
		int startIndex, endIndex;
		int chunkSize = finalNumber / numberOfThreads;

		ArrayList<PrimeFinderThread> threads = new ArrayList<PrimeFinderThread>();
		for (int i = 0; i < numberOfThreads; i++) {
			startIndex = i * chunkSize;
			endIndex = (i == numberOfThreads - 1) ? finalNumber : (i + 1) * chunkSize;
			PrimeFinderThread thread = new PrimeFinderThread(startIndex, endIndex);
			threads.add(thread);
		}
		for (PrimeFinderThread thread : threads) {
			thread.start();
			thread.setStartTime(System.currentTimeMillis());
		}

		int count = 0;
		int countAlive = 0;
		while (countAlive <= numberOfThreads) {
			if (count == numberOfThreads) {
				System.out.println("Presione enter para continuar");
				Scanner sc = new Scanner(System.in);
				sc.nextLine();
				sc.close();
				
				for(PrimeFinderThread thread: threads){
					thread.play();
				}
				count=0;
			}
			for (PrimeFinderThread thread : threads) {
				//System.out.println("El " + thread.getName() + " tiene tiempo de:" + thread.getTime());
				
				try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				if (thread.getTime() >= 5 && count <= numberOfThreads) {
					
					thread.setStartTime(System.currentTimeMillis());
					count++;
					thread.pause();
					System.out.println("El " + thread.getName() + " ha encontrado:" + thread.getPrimes().size() + " primos");
				}
				
				if (!thread.isAlive()) {
					countAlive++;
				}

			}
			
		}		
		System.out.println("la búsqueda ha finalizado");
	}

}
