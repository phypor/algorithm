import java.util.BitSet;

public class BitSetTest {

	public  static class Filter{
	    private int [] seeds = new  int [] { 31 ,131 ,1313 ,13131 ,131313 ,1313131 };
        BitSet bs  = null;
        int size = 0;
	    public Filter(int size){
	    	this.size = size;
	    	bs = new BitSet(size);
	    }
	    
	    public boolean  contain(String value){
	        if  (value  ==  null ) {
	             return   false ;
	        }
	         boolean  ret  =   true ;
	    	for(int i=0;i!= seeds.length;++i){
	    		ret = ret & bs.get( hash(value,seeds[i])) ;
	    	}
	    	return ret;
	    }
	    
	    public void add(String value){
	    	for(int i=0;i!= seeds.length;++i){
	    	   bs.set( hash(value,seeds[i])) ;
	    	}
	    }
	    
	    public int hash (String value,int seed){
	    int length = value.length();
	    int hash  = 0;
	    for(int i=0;i!=length ;++i){
	    	hash = hash * seed + value.charAt(i);
	    }
	    return Math.abs(hash)%size;
	    }
	}
	
	public static int getMax(int[] sum) {
		if(sum == null || sum.length == 0){
			throw new IllegalArgumentException("the array should be not null");
		}
		BitSet bs = new BitSet();
		System.out.println("default size: "+bs.size());
		for (int i = 0; i != sum.length; ++i) {
			bs.set(sum[i]);
		}
		System.out.println("real size: "+bs.size());
		for (int i = bs.size() - 1; i >= 0; --i) {
			if (bs.get(i) == true) {
				System.out.println("max num :" + i);
				return i;
			}
		}
		return -1;
	}

	public static void main(String[] args) {
		int[] sum = new int[] { 7, 18, 3, 123, 2118, 222 };
		getMax(sum);
		
		String value1 = "hello world!";
		String value2 = "2015";
		Filter filter = new Filter(Integer.MAX_VALUE);
		System.out.println(filter.contain(value1));
		filter.add(value1);
		filter.add(value2);
		System.out.println(filter.contain(value1));
		System.out.println(filter.contain(value2));
	}

}
