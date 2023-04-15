package growableArrs;
public abstract class GrowableArr<T> {
	public abstract void doubleArr();
		
	/*The subclasses use wrapper methods to pass in the data into these methods that provide functionality,
	 * and in separating the functionality and data with the wrapper methods being the bridge between the two
	 * maximizes reusability (of the functionality) without exposing the data.*/
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