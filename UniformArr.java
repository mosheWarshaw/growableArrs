package growableArrs;
public class UniformArr<T> extends GrowableArr<T>{
	private T[][] outerArr;
	private int arrSizes;
	//This is the largest userIndex that has an elem placed in it. The add method uses it.
	private int biggestElemIndex = -1;

	public UniformArr(int initialArrSize, int innerArrSizes) throws Exception {
		if(initialArrSize == 0 || innerArrSizes == 0) {
			throw new Exception("Size shouldn't be 0.");
		}
		arrSizes = innerArrSizes;
		int numOfInnerArrs = (initialArrSize - 1) / arrSizes;
		outerArr = (T[][]) new Object[numOfInnerArrs + 1][arrSizes];
	}	
	
	public int[] convertIndex(int userIndex, String callingMethod) {
		int arrIndex = userIndex / arrSizes;
		
		if(arrIndex >= outerArr.length) {
			if(callingMethod.equals("get") || callingMethod.equals("delete")) {
				throw new IndexOutOfBoundsException();
			}
			else {//callingMethod.equals("set") || callingMethod.equals("insert") 
				/*A while loop is used because the user could want to insert an
				 * elem in the 5th inner arr when there's only 1 inner arr, for
				 * example, so you would need to double more than once.*/
				while(arrIndex >= outerArr.length) {
					doubleArr();
				}
			}
		}
				
		int elemIndex = userIndex % arrSizes;
		int[] indexes = new int[2];
		indexes[0] = arrIndex;
		indexes[1] = elemIndex;
		return indexes;
	}
	
	@Override
	public void doubleArr() {
		T[][] tempOuterArr = (T[][]) new Object[calculateNewSize(outerArr.length)][arrSizes];
		for (int i = 0; i < outerArr.length; i++) {
			tempOuterArr[i] = outerArr[i];
		}
		outerArr = tempOuterArr;
	}
	public int calculateNewSize(int prevSize) {
		//This simple calculation is separated into its own method so DoublingUniformArr can override it, because this alulction is ht eonly differnece between that class and this one.
		return prevSize + 1;
	}
	
	/*These methods won't likely be reused (since they're so small and simple), so
	 * wrappers aren't used for them.*/
	public T get(int userIndex) {
		int[] indexes = convertIndex(userIndex, "get");
		return outerArr[indexes[0]][indexes[1]];
	}
	public void set(int userIndex, T elem) {
		if(userIndex > biggestElemIndex) {
			biggestElemIndex = userIndex;
		}
		int[] indexes = convertIndex(userIndex, "set");
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
		int[] indexes = convertIndex(userIndex, "insert");
		int arrIndex = indexes[0];
		int elemIndex = indexes[1];
		
		if(userIndex > biggestElemIndex) {
			biggestElemIndex = userIndex;
		}
		
		insert(userIndex, arrIndex, elemIndex, elem, biggestElemIndex, outerArr);
	}
	public void delete(int userIndex) {
		int[] indexes = convertIndex(userIndex, "delete");
		int arrIndex = indexes[0];
		int elemIndex = indexes[1];
		delete(userIndex, arrIndex, elemIndex, biggestElemIndex, outerArr);
		
		if(userIndex == biggestElemIndex) {
			biggestElemIndex--;
		}
	}
}