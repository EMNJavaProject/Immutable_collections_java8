package main;

import collections.implementations.ImmutableArrayList;
import collections.implementations.ImmutableLinkedList;
import collections.interfaces.ImmutableList;

public class Benchmarks {

	ImmutableList<Integer> ll;
	ImmutableList<Integer> al;

	public Benchmarks()
	{
		ll = new ImmutableLinkedList<Integer>(1, 2, 3, 4, 5, 6, 7 , 8 , 9 , 10);
		al = new ImmutableArrayList<Integer>(1, 2, 3, 4, 5, 6, 7 , 8 , 9 , 10);
	}

	public void run(int warmup, int maxIterations)
	{
		runLinkedListBench(warmup, maxIterations);
		runArrayListBench(warmup, maxIterations);
	}


	@SuppressWarnings("unused")
	public void runLinkedListBench(int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		int indicetoGet = ll.size()-1;
		Integer x;
		ImmutableList<Integer> out ;

		System.out.println("ImmutableLinkedList Microbenchs started...");

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = ll.get(ite%indicetoGet);
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableLinkedList get Microbenchs "+(i+1) +" done in "+ (end - beg) +" ms - "+maxIterations+" iterations.");
		}

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = ll.size();
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableLinkedList size Microbenchs "+(i+1) +" done in "+ (end - beg)+" ms - "+maxIterations+" iterations.");

		}

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = ll.indexOf(ite%indicetoGet);
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableLinkedList indexOf Microbenchs "+(i+1) +" done in "+ (end - beg) +" ms - "+maxIterations+" iterations.");

		}
		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				out = ll.concat(11);
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableLinkedList concat(E) Microbenchs "+(i+1) +" done in "+ (end - beg) +" ms - "+maxIterations+" iterations.");

		}

	}
	@SuppressWarnings("unused")
	public void runArrayListBench(int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		int indicetoGet = al.size()-1;
		Integer x = new Integer(0);
		ImmutableList<Integer> out ;
		System.out.println("ImmutableArrayList Microbenchs started...");



		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = al.get(ite%indicetoGet);
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableArrayList get Microbenchs "+(i+1) +" done in "+ (end - beg) +" ms - "+maxIterations+" iterations.");
		}

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = al.size();
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableArrayList size Microbenchs "+(i+1) +" done in "+ (end - beg)+" ms - "+maxIterations+" iterations.");

		}

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = al.indexOf(ite%indicetoGet);
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableArrayList indexOf Microbenchs "+(i+1) +" done in "+ (end - beg) +" ms - "+maxIterations+" iterations.");

		}
		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				out = al.concat(11);
			}
			end = System.currentTimeMillis();
			System.out.println("ImmutableArrayList concat(E) Microbenchs "+(i+1) +" done in "+ (end - beg) +" ms - "+maxIterations+" iterations.");

		}
	}

}
