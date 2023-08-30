package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread {

	private int a, b;
	private long startTime;
	private List<Integer> primes = new LinkedList<Integer>();
	private boolean pause;

	public PrimeFinderThread(int a, int b) {
		super();
		this.a = a;
		this.b = b;
		pause = false;

	}

	public void run() {
		for (int i = a; i <= b; i++) {
			if (isPrime(i)) {
				primes.add(i);
				// System.out.println(i);
				if(pause){
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

	}

	public synchronized double getTime() {
		long endTime = System.currentTimeMillis();
		long elapsedTimeMillis = endTime - startTime;
		double elapsedTimeSeconds = (double) elapsedTimeMillis / 1000.0;
		return elapsedTimeSeconds;
	}

	public void pause(){
		pause = true;
	}

	public synchronized void play(){
		pause = false;
		notify();
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
