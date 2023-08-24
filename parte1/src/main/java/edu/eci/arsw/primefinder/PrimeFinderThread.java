package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

	private int a, b;
	private long startTime, endTime;
	private List<Integer> primes = new LinkedList<Integer>();
	private String name;

	public PrimeFinderThread(int a, int b, String name) {
		super();
		this.a = a;
		this.b = b;
		this.name = name;

	}

	public void run() {
		for (int i = a; i <= b; i++) {
			if (isPrime(i)) {
				primes.add(i);
				// System.out.println(i);
			}
		}

	}

	public synchronized double getTime() {
		long endTime = System.currentTimeMillis();
		long elapsedTimeMillis = endTime - startTime;
		double elapsedTimeSeconds = (double) elapsedTimeMillis / 1000.0;
		return elapsedTimeSeconds;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public boolean isPrime(int n) {
		if (n % 2 == 0)
			return false;
		for (int i = 3; i * i <= n; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

	public List<Integer> getPrimes() {
		return primes;
	}

}
