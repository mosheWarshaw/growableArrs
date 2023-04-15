package growableArrs;
public class DoublingArr<T> extends GrowableArr<T>{
	private T[][] outerArr = (T[][]) new Object[1][1];
	
	//This is the largest userIndex that has an elem placed in it.
	private int biggestElemIndex = -1;
	
	public int[] convertIndex(int userIndex) {		
		/*The user's index needs to be converted into an arrIndex and an elemIndex
		 * (; finding the elemIndex is discussed in a separate
		 * comment).
		 * 
		 * A log of base 2 operation tells you what number should be the exponent for 2.
		 * It returns the arrIndex because it's telling you the amount of times
		 * the first inner arr was doubled to get to the userIndex, because
		 * each inner arr is twice the size of the previous inner
		 * arr. eg: you have to double 1 (ie the first inner arr size) 2 point something times to get
		 * 5. The amount of doublings is the amount of inner arrs in 1-based-counting that occured.
		 * This number is the arrIndex; it doesn't need to be converted to 0-based-counting
		 * because the first inner arr is doubled 0 times, so the log operation returns 0
		 * for log 1 because 2^0 equals 1.
		 *        2 
		 * 
		 * To the user the arr looks like
		 * (the following is the arr of elems (the letters) with an arr of the indexes beneath
		 * it to make it clear):
		 * [a, b, c, d, e, f, g]
		 * [0, 1, 2, 3, 4, 5, 6]
		 * What the arr really looks like is:
		 * [[a], [b, c], [d, e, f, g]]
		 * [[1], [2, 3], [4, 5, 6, 7]]
		 * */
		
		userIndex++;
		
		/*This is the way to do the following in java:
		 * 
		 * 			log  userIndex
		 * 			    2
		 */
		int arrIndex = (int) (Math.log(userIndex) / Math.log(2));
				
		/*A while loop is used because the user could want to insert an elem in the 5th inner arr when
		 * there's only 1 inner arr, for example, so you would need to double more than once.*/
		while(arrIndex >= outerArr.length) {
			doubleArr();
		}				
		
		/*The calculation for the sum of elems in the inner arrays before (, meaning with a
		 * lower index than) the arr of arrIndex.
		 * 
		 * summation:
		 * 
		 *    n
		 * -------
		 * \
		 *  \
		 *  /	   2^i       
		 * /
		 * -------
		 *  i = 0
		 * 
		 * 
		 * closed form: 2^(n + 1) - 1
		 *  
		 * 'n' is the arrIndex. since it is the arr of the elem
		 * the user wants, the amount of previous arrs is 1 less than the arrIndex,
		 * so the closed form becomes  2^(n - 1 + 1) - 1 = 2^(n) - 1.*/
		int prevArrsElemsAmount = (int) Math.pow(2, arrIndex) - 1;
		/*userIndex - prevArrsElemsAmount is the amount of elems between the start of the
		 * arrIndexes inner array and the elem's index. It's in 1-based-counting so I
		 * subtract 1.*/
		int elemIndex = userIndex - prevArrsElemsAmount - 1;
		
		int[] indexesArr = new int[2];
		indexesArr[0] = arrIndex;
		indexesArr[1] = elemIndex;
		return indexesArr;
	}
	
	@Override
	public void doubleArr() {
		T[][] tempOuterArr = (T[][]) new Object[outerArr.length + 1][];
		
		//Copies the inner arrs.
		for(int a = 0; a < outerArr.length; a++) {
			tempOuterArr[a] = outerArr[a];
		}
		
		//Adds a new inner arr.
		int lastInnerArrsLength = outerArr[outerArr.length - 1].length;
		tempOuterArr[outerArr.length] = (T[]) new Object[lastInnerArrsLength * 2];
		
		outerArr = tempOuterArr;
	}
	
	
	
	/*These methods won't likely be reused (since they're so small and simple), so
	 * wrappers aren't used for them.*/
	public T get(int userIndex) {
		int[] indexes = convertIndex(userIndex);
		return outerArr[indexes[0]][indexes[1]];
	}
	public void set(int userIndex, T elem) {
		if(userIndex > biggestElemIndex) {
			biggestElemIndex = userIndex;
		}
		int[] indexes = convertIndex(userIndex);
		outerArr[indexes[0]][indexes[1]] = elem;
	}
	public void add(T elem) {
		 /*This method doesn't add the elem to the earliest free space
		  * in the arr if the user made a sparse arr out of it.*/
		biggestElemIndex++;
		set(biggestElemIndex, elem);
	}

	
	
	//wrapper methods.
	public void insert(int userIndex, T elem) {
		/*When a method is adding an element, the outerArr might need to expand
		 * to create space for that elem. When I expand the arr, I create a new 2d-arr,
		 * and I assign the outerArr variable to hold the refernece to it instead of the
		 * old, smaller 2d-arr. Therefore, I need to calculate the index before passing in
		 * the outerArr variable, instead of passing the method the userIndex and outerArr var
		 * and having it call the subclass's convertIndex method.
		 * (userIndex is still passed in, though for another purpose.)*/
		int[] indexes = convertIndex(userIndex);
		int arrIndex = indexes[0];
		int elemIndex = indexes[1];
		
		if(userIndex > biggestElemIndex) {
			biggestElemIndex = userIndex;
		}
		
		insert(userIndex, arrIndex, elemIndex, elem, biggestElemIndex, outerArr);
	}
	public void delete(int userIndex) {
		int[] indexes = convertIndex(userIndex);
		int arrIndex = indexes[0];
		int elemIndex = indexes[1];
		delete(userIndex, arrIndex, elemIndex, biggestElemIndex, outerArr);
		
		if(userIndex == biggestElemIndex) {
			biggestElemIndex--;
		}
	}
}
