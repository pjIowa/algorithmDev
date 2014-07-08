import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Arrays;

public class sorting {

	private static int[] arr;
	private static int[] arrCopy;
	private static int[] arrConst;
	private static BufferedReader read;
	private static Random randomGenerator;

	private static int size;
	private static int random;

	private static int n;

	private static void printArray() {
		System.out.print("[" + arr[0]);
		for(int i=1; i<size; i++) {
			System.out.print(", " + arr[i]);
		}
		System.out.println("]");
	}

	public static void buildheap(){
		n=arr.length-1;
		for(int i=n/2;i>=0;i--){
			heapify(i);
		}
	}

	public static void heapify(int i){ 
		int largest;
		int left=2*i;
		int right=2*i+1;
		if(left <= n && arr[left] > arr[i]){
			largest=left;
		}
		else{
			largest=i;
		}

		if(right <= n && arr[right] > arr[largest]){
			largest=right;
		}
		if(largest!=i){
			exchange(i,largest);
			heapify(largest);
		}
	}

	public static void exchange(int i, int j){
		int t=arr[i];
		arr[i]=arr[j];
		arr[j]=t; 
	}

	public static void heapsort(){
		buildheap();    
		for(int i=n;i>0;i--){
			exchange(0, i);
			n=n-1;
			heapify(0);
		}
	}

	/*
	 * Problem 1
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: sorts the array of the given range using insertion sort
	 */
	private static void insertSort(int low, int high)
	{
		for (int i = low; i <= high; i++)
		{
			int holeValue = arr[i];
			int holeIndex = i;

			while (holeIndex > low && holeValue < arr[holeIndex - 1])
			{
				arr[holeIndex] = arr[holeIndex - 1];
				holeIndex--;
			}
			arr[holeIndex] = holeValue;                        
		}
	}

	/*
	 * Problem 2
	 * params: 2 ints, index for inclusive lower and upper bounds in array
	 * return: bool
	 * description: returns if all the elements in the given range are in order
	 */
	private static boolean isSorted(int low, int high)
	{
		for (int i = low; i < high; i++)
		{
			if (arr[i] > arr[i + 1])
				return false;
		}
		return true;
	}

	private static void mergesort(int low, int high) {
		// Check if low is smaller then high, if not then the array is sorted
		if (low < high) {
			// Get the index of the element which is in the middle
			int middle = low + (high - low) / 2;
			// Sort the left side of the array
			mergesort(low, middle);
			// Sort the right side of the array
			mergesort(middle + 1, high);
			// Combine them both
			merge(low, middle, high);
		}
	}

	/*
	 * Problem 3a
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: Halves the array until the subarray has less than 100 elements,
	 * 				sorts the halves with insertion sort,
	 * 				then merges all of the subarrays together.
	 */
	private static void mergesortI(int low, int high)
	{
		//Check if range is small enough to permit use of insertion sort
		if(high - low < 100)
		{
			insertSort(low, high);
		}
		//Otherwise break into smaller chunks
		else
		{
			// Check if low is smaller then high, if not then the array is sorted
			if (low < high)
			{
				// Get the index of the element which is in the middle
				int middle = low + (high - low) / 2;
				// Sort the left side of the array
				mergesortI(low, middle);
				// Sort the right side of the array
				mergesortI(middle + 1, high);
				// Combine them both
				merge(low, middle, high);
			}
		}
	}

	/*
	 * Problem 3b
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: checks if either half of the array is not in order,
	 * 				and merge sorts the subarrays if true, then
	 * 				the halves are put together again.
	 */
	private static void mergesortII(int low, int high)
	{
		if (low < high)
		{
			// Get the index of the element which is in the middle
			int middle = low + (high - low) / 2;
			// Sort the left side of the array
			if(!isSorted(low, middle))
			{
				mergesortII(low, middle);
			}
			// Sort the right side of the array
			if(!isSorted(middle+1, high)) 
			{
				mergesortII(middle + 1, high);
			}
			// Combine them both
			merge(low, middle, high);
		}
	}

	/*
	 * Problem 3c
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: checks if the range has less than 100 elements,
	 * 				and uses insertion sort if it's not in order, if the # of elements
	 * 				is more than 100, it checks if either half of the array is not in order,
	 * 				and recursively calls the function if true,
	 * 				then the halves are put together again.
	 */
	private static void mergesortIII( int low, int high)
	{
		if(high - low < 100)
		{
			if(!isSorted(low, high))
				insertSort(low, high);
		}
		else
		{
			if (low < high) 
			{
				// Get the index of the element which is in the middle
				int middle = low + (high - low) / 2;
				// Sort the left side of the array
				if(!isSorted(low, middle)) 
				{
					mergesortIII(low, middle);
				}
				// Sort the right side of the array
				if(!isSorted(middle+1, high)) 
				{
					mergesortIII(middle + 1, high);
				}
				// Combine them both
				merge(low, middle, high);
			}
		}
	}



	private static void merge(int low, int middle, int high) {

		// Copy both parts into the arrCopy array
		for (int i = low; i <= high; i++) 
		{
			arrCopy[i] = arr[i];
		}

		int i = low;
		int j = middle + 1;
		int k = low;
		// Copy the smallest values from either the left or the right side back
		// to the original array
		while (i <= middle && j <= high) 
		{
			if (arrCopy[i] <= arrCopy[j]) 
			{
				arr[k] = arrCopy[i];
				i++;
			} 
			else 
			{
				arr[k] = arrCopy[j];
				j++;
			}
			k++;
		}
		// Copy the rest of the left side of the array into the target array
		while (i <= middle) 
		{
			arr[k] = arrCopy[i];
			k++;
			i++;
		}

	}

	private static void quicksort(int low, int high) {
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		int pivot = arr[(high+low)/2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (arr[i] < pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (arr[j] > pivot) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i < j) {
				exchange(i, j);
				i++;
				j--;
			} else if (i == j) { i++; j--; }
		}

		// Recursion
		if (low < j)
			quicksort(low, j);
		if (i < high)
			quicksort(i, high);
	}

	/*
	 * Problem 4, helper function
	 * params: 3 ints, three different indices
	 * return: int
	 * description: checks which value at the given indices
	 * 				gives the median value of the three, and
	 * 				returns the index of the median found
	 */
	private static int getMedianIndex(int x, int y, int z)
	{
		if (arr[x] > arr[y]) 
		{
			if (arr[y] > arr[z]) 
				return y;
			else if (arr[x] > arr[z]) 
				return z;
			else 
				return x;     
		} 
		else 
		{
			if (arr[x] > arr[z]) 
				return x;
			else if (arr[y] > arr[z]) 
				return z;
			else 
				return y;      
		}
	}

	/*
	 * Problem 5a
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: Once the pivot allows the recursive call to use a subarray with
	 * 				less than 100 elements, it sorts the subarray with insertion sort. 
	 */
	private static void quicksortI(int low, int high) 
	{
		if(high-low < 100)
			insertSort(low, high);
		else
		{
			int i = low, j = high;
			// Get the pivot element from the middle of the list
			int pivot = arr[(high+low)/2];

			// Divide into two lists
			while (i <= j) {
				// If the current value from the left list is smaller then the pivot
				// element then get the next element from the left list
				while (arr[i] < pivot) {
					i++;
				}
				// If the current value from the right list is larger then the pivot
				// element then get the next element from the right list
				while (arr[j] > pivot) {
					j--;
				}

				// If we have found a values in the left list which is larger then
				// the pivot element and if we have found a value in the right list
				// which is smaller then the pivot element then we exchange the
				// values.
				// As we are done we can increase i and j
				if (i < j) {
					exchange(i, j);
					i++;
					j--;
				} else if (i == j) { i++; j--; }
			}

			// Recursion
			if (low < j)
				quicksortI(low, j);
			if (i < high)
				quicksortI(i, high); 
		}

	}

	/*
	 * Problem 5b
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: if the range (low, pivot) or (pivot, high) is already sorted,
	 * 				skip the recursive call to the function
	 */
	public static void quicksortII(int low, int high)
	{
		int i = low, j = high;
		// Get the pivot element from the middle of the list
		int pivot = arr[(high+low)/2];

		// Divide into two lists
		while (i <= j) {
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (arr[i] < pivot) {
				i++;
			}
			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (arr[j] > pivot) {
				j--;
			}

			// If we have found a values in the left list which is larger then
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i < j) {
				exchange(i, j);
				i++;
				j--;
			} else if (i == j) { i++; j--; }
		}

		// Recursion
		if (low < j)
		{
			if(!isSorted(low, j))
				quicksortII(low, j);
		}
		if (i < high)
		{
			if(!isSorted(i, high))
				quicksortII(i, high); 
		}
	}

	/*
	 * Problem 5c and 4a
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: Set the pivot value equal to the median value of low,
	 * 				high, and the average index of the two
	 */
	private static void quicksortIII(int low, int high) 
	{
		int i = low, j = high;
		int middle = (high + low)/2;
		// Get the pivot element from the median of low, high, and middle
		int pivot = arr[getMedianIndex(low, middle, high)];

		// Divide into two lists
		while (i <= j)
		{
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (arr[i] < pivot) 
			{
				i++;  
			}

			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (arr[j] > pivot) 
			{
				j--;
			}

			// If we have found a values in the left list which is larger then            
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i < j) 
			{            
				exchange(i, j);             
				i++;                
				j--;            
			} 
			else if (i == j) 
			{ 
				i++; 
				j--; 
			}            
		}

		// Recursion        
		if (low < j)
			quicksortIII(low, j);
		if (i < high)
			quicksortIII(i, high);
	}

	/*
	 * Problem 5d and 4b
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: Set the pivot value equal to the median value of
	 * 				pivot on the median of three medians of their own three elements
	 */
	private static void quicksortIV(int low, int high) 
	{
		int i = low, j = high;
		int middle = (high + low)/2;
		// Get the pivot element from the median of low, high, and middle
		int lowMedian = low;
		int middleMedian = middle;
		int highMedian = high;
		if ((high - low) > 2)
		{
			lowMedian = getMedianIndex(low, low + 1, low + 2);
			middleMedian = getMedianIndex(middle - 1, middle, middle + 1);
			highMedian = getMedianIndex(high - 2, high - 1, high);            
		}

		int pivot = arr[getMedianIndex(lowMedian, middleMedian, highMedian)];
		exchange(getMedianIndex(lowMedian, middleMedian, highMedian), low);

		// Divide into two lists
		while (i <= j)
		{
			// If the current value from the left list is smaller then the pivot
			// element then get the next element from the left list
			while (arr[i] < pivot) 
			{
				i++;  
			}

			// If the current value from the right list is larger then the pivot
			// element then get the next element from the right list
			while (arr[j] > pivot) 
			{
				j--;
			}

			// If we have found a values in the left list which is larger then            
			// the pivot element and if we have found a value in the right list
			// which is smaller then the pivot element then we exchange the
			// values.
			// As we are done we can increase i and j
			if (i < j) 
			{            
				exchange(i, j);             
				i++;                
				j--;            
			} 
			else if (i == j) 
			{ 
				i++; 
				j--; 
			}            
		}

		// Recursion        
		if (low < j)
			quicksortIV(low, j);
		if (i < high)
			quicksortIV(i, high);
	}

	/*
	 * Problem 5e
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: 5a, b, and c
	 */
	private static void quicksortV(int low, int high) 
	{
		if(high-low < 100)
			insertSort(low, high);
		else
		{
			int i = low, j = high;
			int middle = (high + low)/2;
			// Get the pivot element from the median of low, high, and middle
			int pivot = arr[getMedianIndex(low, middle, high)];

			// Divide into two lists
			while (i <= j) {
				// If the current value from the left list is smaller then the pivot
				// element then get the next element from the left list
				while (arr[i] < pivot) {
					i++;
				}
				// If the current value from the right list is larger then the pivot
				// element then get the next element from the right list
				while (arr[j] > pivot) {
					j--;
				}

				// If we have found a values in the left list which is larger then
				// the pivot element and if we have found a value in the right list
				// which is smaller then the pivot element then we exchange the
				// values.
				// As we are done we can increase i and j
				if (i < j) {
					exchange(i, j);
					i++;
					j--;
				} else if (i == j) { i++; j--; }
			}

			// Recursion
			if (low < j)
				if(!isSorted(low, j))
					quicksortV(low, j);
			if (i < high)
				if(!isSorted(i, high))
					quicksortV(i, high); 
		}
	}

	/*
	 * Problem 5e
	 * params: 2 ints, index for inclusive lower and upper bounds to sort array
	 * description: 5a, b, and d
	 */
	private static void quicksortVI(int low, int high) 
	{
		if(high-low < 100)
			insertSort(low, high);
		else
		{
			int i = low, j = high;
			int middle = (high + low)/2;
			// Get the pivot element from the median of low, high, and middle
			int lowMedian = low;
			int middleMedian = middle;
			int highMedian = high;
			if ((high - low) > 2)
			{
				lowMedian = getMedianIndex(low, low + 1, low + 2);
				middleMedian = getMedianIndex(middle - 1, middle, middle + 1);
				highMedian = getMedianIndex(high - 2, high - 1, high);            
			}

			int pivot = arr[getMedianIndex(lowMedian, middleMedian, highMedian)];

			// Divide into two lists
			while (i <= j) {
				// If the current value from the left list is smaller then the pivot
				// element then get the next element from the left list
				while (arr[i] < pivot) {
					i++;
				}
				// If the current value from the right list is larger then the pivot
				// element then get the next element from the right list
				while (arr[j] > pivot) {
					j--;
				}

				// If we have found a values in the left list which is larger then
				// the pivot element and if we have found a value in the right list
				// which is smaller then the pivot element then we exchange the
				// values.
				// As we are done we can increase i and j
				if (i < j) {
					exchange(i, j);
					i++;
					j--;
				} else if (i == j) { i++; j--; }
			}

			// Recursion
			if (low < j)
				if(!isSorted(low, j))
					quicksortVI(low, j);
			if (i < high)
				if(!isSorted(i, high))
					quicksortVI(i, high); 
		}
	}

	public static void main(String[] args) {

		read = new BufferedReader(new InputStreamReader(System.in));

		randomGenerator = new Random();

		try
		{
			//size and range input
			System.out.print("Please enter array size : ");
			size = Integer.parseInt(read.readLine());

			System.out.print("Please enter the random range : ");
			random = Integer.parseInt(read.readLine());

			// create array
			arr = new int[size];
			arrCopy = new int[size];
			arrConst = new int[size];

			for(int i = 0; i< size; i++) arr[i]=arrCopy[i]=arrConst[i]=randomGenerator.nextInt(random);
			if (size < 101) { 
				System.out.println("Initial array:");
				printArray();
			}

			// array class sorting function
			long start = System.currentTimeMillis();
			Arrays.sort(arr);
			if (size < 101) printArray();
			long finish = System.currentTimeMillis();
			System.out.println("Arrays.sort: " + (finish-start) + " milliseconds.\n");

			// Heap sort      
			start = finish;
			heapsort();
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("heapsort: " + (finish-start) + " milliseconds.\n");

			// Merge sort original function, which destroys arrCopy[]
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			mergesort(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("mergesort, original: " + (finish-start) + " milliseconds.\n");

			//further merge sorts need arrcopy to be reset to the original
			//data since the merge function destroys arrcopy during 
			//its operation.
			// Problem 3a
			for(int i=0; i<size; i++)
			{
				arrCopy[i] = arrConst[i];
				arr[i] = arrCopy[i];
			}
			start = finish;
			mergesortI(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("mergesort, vers. 1: " + (finish-start) + " milliseconds.\n");
			
			// Problem 3b
			for(int i=0; i<size; i++)
			{
				arrCopy[i] = arrConst[i];
				arr[i] = arrCopy[i];
			}
			start = finish;
			mergesortII(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("mergesort, vers. 2: " + (finish-start) + " milliseconds.\n");

			// Problem 3c
			for(int i=0; i<size; i++)
			{
				arrCopy[i] = arrConst[i];
				arr[i] = arrCopy[i];
			}
			start = finish;
			mergesortIII(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("mergesort, vers. 3: " + (finish-start) + " milliseconds.\n");

			// Quick sort original
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			quicksort(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("quicksort, original: " + (finish-start) + " milliseconds.\n");

			// Problem 5a
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			quicksortI(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("quicksort, vers. 1: " + (finish-start) + " milliseconds.\n");

			// Problem 5b
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			quicksortII(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("quicksort, vers. 2: " + (finish-start) + " milliseconds.\n");

			// Problem 5c
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			quicksortIII(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("quicksort, vers. 3: " + (finish-start) + " milliseconds.\n");

			// Problem 5d
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			quicksortIV(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("quicksort, vers. 4: " + (finish-start) + " milliseconds.\n");

			// Problem 5e
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			quicksortV(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("quicksort, vers. 5: " + (finish-start) + " milliseconds.\n");

			// Problem 5f
			for(int i=0; i<size; i++) arr[i] = arrCopy[i];
			start = finish;
			quicksortVI(0, size-1);
			if (size < 101) printArray();
			finish = System.currentTimeMillis();
			System.out.println("quicksort, vers. 6: " + (finish-start) + " milliseconds.\n");
			
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
}