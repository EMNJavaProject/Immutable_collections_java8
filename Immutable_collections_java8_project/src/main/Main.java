package main;

public class Main {

	public static void main(String[] args) {
		System.out.println("Main.");
		Benchmarks bench = new Benchmarks();
		bench.run(5, 10000000);
	}

}
