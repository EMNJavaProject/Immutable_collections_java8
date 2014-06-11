package main;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import collections.implementations.ImmutableArrayList;
import collections.implementations.ImmutableLinkedList;
import collections.interfaces.ImmutableList;

public class Benchmarks {

	ImmutableList<Integer> ill; /** ImmutableLinkedList implementation */
	ImmutableList<Integer> ial; /** ImmutableArrayList implementation */
	List<Integer> ll; /** LinkedList implementation */
	List<Integer> al; /** ArrayList implementation */
	ImmutableList<Integer> hial; /** Half ImmutableLinkedList implementation */
	List<Integer> dummy; /** List implementation needed to concat */

	public Benchmarks(int size)
	{
		Integer[] array = new Integer[size];
		for(int i=0; i< size; ++i)
			array[i] = i;
		ill = new ImmutableLinkedList<Integer>(array);
		ial = new ImmutableArrayList<Integer>(array);
		ll = new LinkedList<Integer>();
		ll.addAll(ill.asList());
		al = new ArrayList<Integer>();
		al.addAll(ial.asList());
		hial = null;
		dummy = new ArrayList<Integer>();
		dummy.addAll(ial.asList());

	}

	public void run(int warmup, int maxIterations)
	{
		runImmuLinkedListBench(warmup, maxIterations);
		runImmuArrayListBench(warmup, maxIterations);
		runLinkedListBench(warmup, maxIterations);
		runArrayListBench(warmup, maxIterations);
		//runHalfImmuLinkedListBench(warmup, maxIterations);


	}


	public void runImmuLinkedListBench(int warmup, int maxIterations)
	{
		System.out.println("ImmutableLinkedList Microbenchs started...");
		this.execAll(ill, warmup, maxIterations);
	}

	public void runImmuArrayListBench(int warmup, int maxIterations)
	{
		System.out.println("ImmutableArrayList Microbenchs started...");
		this.execAll(ial, warmup, maxIterations);
	}

	public void runArrayListBench(int warmup, int maxIterations)
	{
		System.out.println("ArrayList Microbenchs started...");
		this.execAll(al, warmup, maxIterations);
	}

	public void runLinkedListBench(int warmup, int maxIterations)
	{
		System.out.println("LinkedList Microbenchs started...");
		this.execAll(ll, warmup, maxIterations);
	}

	public void runHalfImmuLinkedListBench(int warmup, int maxIterations)
	{

		System.out.println("Half ImmutableLinkedList Microbenchs started...");
		this.execAll(hial, warmup, maxIterations);

	}




	private <E> void execAll(ImmutableList<E> list, int warmup, int maxIterations)
	{
		long execTime = 0;

		execTime = this.execGet(list, warmup, maxIterations);
		System.out.println("___get     microbenchs done in "+ execTime +" ms.");

		execTime = this.execSize(list, warmup, maxIterations);
		System.out.println("___size    microbenchs done in "+ execTime +" ms.");

		execTime = this.execIndexOf(list, warmup, maxIterations);
		System.out.println("___indexOf microbenchs done in "+ execTime +" ms.");

		execTime = this.execConcat(list, warmup, maxIterations);
		System.out.println("___concat microbenchs done in "+ execTime +" ms.");
	}

	private <E> void execAll(List<E> list, int warmup, int maxIterations)
	{
		long execTime = 0;

		execTime = this.execGet(list, warmup, maxIterations);
		System.out.println("___get     microbenchs done in "+ execTime +" ms.");

		execTime = this.execSize(list, warmup, maxIterations);
		System.out.println("___size    microbenchs done in "+ execTime +" ms.");

		execTime = this.execIndexOf(list, warmup, maxIterations);
		System.out.println("___indexOf microbenchs done in "+ execTime +" ms.");

		execTime = this.execConcat(list, warmup, maxIterations);
		System.out.println("___concat microbenchs done in "+ execTime +" ms.");
	}






	private <E> long execGet(ImmutableList<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		int indicetoGet = list.size()-1;
		@SuppressWarnings("unused")
		E x;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = list.get(indicetoGet);
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
		}
		return (execTime/warmup);
	}

	private <E> long execGet(List<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		int indicetoGet = list.size()-1;
		@SuppressWarnings("unused")
		E x;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = list.get(indicetoGet);
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
		}
		return (execTime/warmup);
	}

	private <E> long execSize(ImmutableList<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		@SuppressWarnings("unused")
		int x;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = list.size();
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
		}
		return (execTime/warmup);
	}

	private <E> long execSize(List<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		@SuppressWarnings("unused")
		int x;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = list.size();
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
		}
		return (execTime/warmup);
	}

	private <E> long execIndexOf(ImmutableList<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		E objToGet = list.get(list.size()-1);
		@SuppressWarnings("unused")
		int x;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = list.indexOf(objToGet);
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
		}
		return (execTime/warmup);
	}

	private <E> long execIndexOf(List<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		E objToGet = list.get(list.size()-1);
		@SuppressWarnings("unused")
		int x;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				x = list.indexOf(objToGet);
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
		}
		return (execTime/warmup);
	}


	private <E> long execConcat(ImmutableList<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		@SuppressWarnings("unused")
		ImmutableList<E> out;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				out = list.concat(list);
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
		}
		return (execTime/warmup);
	}

	@SuppressWarnings("unchecked")
	private <E> long execConcat(List<E> list, int warmup, int maxIterations)
	{
		long  beg = 0;
		long  end = 0;
		long execTime = 0;

		for(int i=0; i < warmup; ++i)
		{
			beg = System.currentTimeMillis();
			for(int ite=0; ite < maxIterations;  ++ite)
			{
				list.addAll((Collection<? extends E>) this.dummy);
			}
			end = System.currentTimeMillis();
			execTime += (end-beg);
			list.removeAll(this.dummy);
		}
		return (execTime/warmup);
	}
}
