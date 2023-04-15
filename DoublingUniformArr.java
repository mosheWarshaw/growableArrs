package growableArrs;
public class DoublingUniformArr<T> extends UniformArr<T>{
	public DoublingUniformArr(int arrSizesPar) {
		super(arrSizesPar);
	}
	
	@Override
	public int calculateNewSize(int prevSize) {
		return prevSize * 2;
	}
}
















