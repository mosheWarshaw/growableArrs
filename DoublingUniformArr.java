package growableArrs;
public class DoublingUniformArr<T> extends UniformArr<T>{
	public DoublingUniformArr(int intialArrSize, int innerArrSizes) throws Exception {
		super(intialArrSize, innerArrSizes);
	}
	
	@Override
	public int calculateNewSize(int prevSize) {
		return prevSize * 2;
	}
}
















