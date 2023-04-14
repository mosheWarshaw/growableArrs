package growableArrs;
public class UniformGrowableArr<T> extends GrowableArr<T>{
	private T[][] outerArr;
	private int arrSizes;
	//This is the largest userIndex that has an elem placed in it. The add method uses it.
	private int biggestElemIndex = -1;

	public UniformGrowableArr(int arrSizesPar) {
		outerArr = (T[][]) new Object[1][arrSizesPar];
		arrSizes = arrSizesPar;
	}	
	
	public int[] convertIndex(int userIndex) {
		int arrIndex = userIndex / arrSizes;
		
		//Before going further, grow the arr if necessary.
		/*A while loop is used because the user could want to insert
		 * an elem in the 5th inner arr when there's only 1 inner arr,
		 * for example, so you would need to double more than once.*/
		while(arrIndex >= outerArr.length) {
			doubleArr();
		}
				
		int elemIndex = userIndex % arrSizes;
		int[] indexes = new int[2];
		indexes[0] = arrIndex;
		indexes[1] = elemIndex;
		return indexes;
	}
	
	@Override
	public void doubleArr() {
		T[][] tempOuterArr = (T[][]) new Object[outerArr.length + 1][arrSizes];
		for (int i = 0; i < outerArr.length; i++) {
			tempOuterArr[i] = outerArr[i];
		}
		outerArr = tempOuterArr;
	}
	
	
	
	public T get(int userIndex) {
		int[] indexes = convertIndex(userIndex);
		int arrIndex = indexes[0];
		int elemIndex = indexes[1];
		return outerArr[arrIndex][elemIndex];
	}
	public void set(int userIndex, T elem) {
		if(userIndex > biggestElemIndex) {
			biggestElemIndex = userIndex;
		}
		
		int[] indexes = convertIndex(userIndex);
		int arrIndex = indexes[0];
		int elemIndex = indexes[1];
		
		outerArr[arrIndex][elemIndex] = elem;
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