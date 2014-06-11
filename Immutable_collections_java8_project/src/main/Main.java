package main;

public class Main {

	public static void main(String[] args) {
		System.out.println("Main.");
		Benchmarks bench = new Benchmarks(100);
		bench.run(10, 1000);
	}

}
