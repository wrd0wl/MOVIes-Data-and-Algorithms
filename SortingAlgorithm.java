/* 
 * Copyright (C) 2020 - Angelo Di Iorio
 * 
 * Progetto Movida.
 * Corso di Algoritmi e Strutture Dati
 * Laurea in Informatica, UniBO, a.a. 2019/2020
 * 
*/
package movida.commons;

/**
 * 
 * Elenco degli algoritmi di ordinamento
 * previsti nell'applicazione Movida
 * 
 * Ogni gruppo deve implementare 2 algoritmi.
 * 
 */
public enum SortingAlgorithm {
	InsertionSort,
	SelectionSort,
	BubbleSort,
	MergeSort,
	QuickSort,
	HeapSort
}

	public static void insertionSort(Movie A[]) {
		for (int i = 1; i <= A.length - 1; i++) {
			Movie x = A[i];
			for (int j = 0; j < i; j++)
				if ((A[j].getTitle()).compareTo(x.getTitle()) > 0) break;
			if (j < i) {
				for (int t = i; t > j; t--) A[t] = A[t - 1];
				A[j] = x;
			}
		}
	}
