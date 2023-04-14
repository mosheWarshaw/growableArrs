package growableArrs;
/*terminology:
 * -"arr" is short for "array".
 * -"elem" is short for "element".
 * -an "inner arr" is one of the 1d-arrs inside the "outer arr". the inner arrs
 * hold the elements. the "outer arr" holds the "inner arrs".
 * -"arrIndex" is the index of the inner arr that holds the elem that the user is looking for
 * (or whatever other purpose you're acessingor modifying an elem in the arr).
 * -"elemIndex" is the index in the inner arr that holds the elem.*/
/*When the java ArrayList runs out of space it creates a new array that is twice the size of the
 * current one, and it copies the elems into the new arr. I have 2 different growable arrays,
 * the DoublingGrowableArr and the UniformGrowableArr, that are each more efficient.
 * 
 * First I'll explain my DoublingGrowableArr. It is a 2d-array that starts with one inner arr,
 * and when the inner arr is filled an empty inner arr that's twice the size of the first one
 * is created, and the filled and empty inner arrs are copied into a new
 * outer arr that has space for 2 inner arrs. The key difference between the ArrayList and the
 * DoublingGrowableArr is the ArrayList copies elems when more space is needed, and my growable arr
 * copies arr references, which significantly decreases the amount of
 * copies made.
 * The methods allow the user to pass in an index as if it was a 1d-arr, and the index
 * is converted into indexes that can be used for the get the right elem from the right
 * inner arr without additional complexity.
 * 
 * The UnifromGrowableArr is the same idea as the DoublingGrowableArr, except the inner arrs are a
 * uniform size. The user determines the size the inner arrs should be, and they're all that size.
 * If the user chooses a very bad inner arr size then the UnifromGrowableArr will perform more copies
 * than an ArrayList would in the same situation.*/
public abstract class GrowableArr<T> {
	public abstract void doubleArr();
	
	/*Methods are most reusable when values are passed to them rather
	 * than when these values are globals. I have these methods have the outerArr
	 * passed to them for this reason, and I create wrapper methods in the subclasses
	 * for them so I don't have to give the user access to the outerArr. The subclass
	 * passes the outerArr in, not the user.
	 * By separating these methods from the data I have maximized reusability without
	 * exposing data.
	 * I don't bother doing this for the get, set, and add methods because
	 * of how few lines of code they are, and the chances of someone reusing them
	 * instead of writing their own small getters and setters and add methods
	 * is small.*/		
	public void insert(int userIndex, int arrIndex, int elemIndex, T elem,  int biggestElemIndex, T[][] outerArr) {		
		int lastArrIndex = outerArr.length - 1;
		int lastElemIndex = outerArr[lastArrIndex].length - 1;
		if(arrIndex == lastArrIndex && elemIndex == lastElemIndex &&
				outerArr[lastArrIndex][lastElemIndex] != null) {
			/*If the user wants to insert an elem into the last index of the last inner
			 * array, and there is an elem there, then a new inner array needs to be created
			 * so that elem has a place it can be bumped into.*/
			doubleArr();
		}
		
		int b = elemIndex;
		T elemToInsert = elem;
		T bumpedElem;
		int totalSize = 0;
		for(int a = arrIndex; a < outerArr.length; a++){
			for(; b < outerArr[a].length && totalSize <= biggestElemIndex; b++, totalSize++){
				bumpedElem = outerArr[a][b];
				outerArr[a][b] = elemToInsert;
				elemToInsert = bumpedElem;
			}
			b = 0;
		}
	}	
	public void delete(int userIndex, int arrIndex, int elemIndex, int biggestElemIndex, T[][] outerArr) {				
		int b = elemIndex;
		int totalSize = 0;
		T nextElem;
		for(int a = arrIndex; a < outerArr.length; a++){
			for(; b < outerArr[a].length && totalSize <= biggestElemIndex; b++, totalSize++){
				if(b + 1 >=  outerArr[a].length) {
					if(a + 1 >= outerArr.length) {
						nextElem = null;
					}
					else {
						nextElem = outerArr[a + 1][0];
					}
				}
				else {
					nextElem = outerArr[a][b + 1];
				}
				outerArr[a][b] = nextElem;
			}
			b = 0;
		}
	}
}