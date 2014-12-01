//package project2;
//code and designed by coreClean(maohua zheng)
import java.util.ArrayList;
import java.util.concurrent.Callable;

public class Fib {
	public static ArrayList<Callable<Integer>> funs=new ArrayList<Callable<Integer>>();
	public static String [] funcNames={"recurrsive","top_down","bottom_up"};
	
	public static <T> T FibFuncs(Callable<T> func) throws Exception
	{
		return func.call();
	}
	
	public static void setFuncs(final int n)
	{
		funs.add(new Callable<Integer>() {
			int n_;
			{
				n_=n;
			}
			@Override
		    public Integer call() {
		        return Fib.fibRecur(n_);
		   }
		});
		
		funs.add(new Callable<Integer>() {
			int n_;
			{
				n_=n;
			}
			@Override
		    public Integer call() {
		        return Fib.fibTopDpwn(n_);
		   }
		});
		funs.add(new Callable<Integer>() {
			int n_;
			{
				n_=n;
			}
			@Override
		    public Integer call() {
		        return Fib.fibBottomUp(n_);
		   }
		});
		
		
	}
	
	public static Callable<Integer> getRecurFun(final int n)
	// I just created it but did not use it. Maohua Zheng
	{
		return new Callable<Integer>() {
			int n_ ;
			 {
                n_=n;
	         }
			@Override
		    public Integer call() {
		        return Fib.fibRecur(n_);
		   }
		};
	}
	
	
	public static int fibRecur(int n)
	{
		if(n<2)return n;
		else{
			return fibRecur(n-1)+fibRecur(n-2);
		}
			
	}
	
	public static int fibBottomUp(int n)
	{
		if(n<2)return n;
		else{
			int []r=new int[n+1]; //in java all be initalized to 0 by default;
			//r[0]=0;
			r[1]=1;
			for(int i=2;i<=n;i++){
				r[i]=r[i-2]+r[i-1];
			}
			return r[n];
		}
			
	}
	
	public static int fibTopDpwn(int n)
	{
		if(n<2)return n;
		else{
			int []r=new int[n+1]; //in java all be initalized to 0 by default;
			//r[0]=0;
			r[1]=1;
			return fibTopDpwnMeorized(n,r);
		}
						
	}
	

	private static int fibTopDpwnMeorized(int n,int r[]){
		if(n<2)return n;
		if(r[n]>0)return r[n];
		else{
			r[n]=fibTopDpwnMeorized(n-1,r)+fibTopDpwnMeorized(n-2,r);
			return r[n];
		}			
	}
		
	
	public static void main(final String[] args) throws Exception
	{
		if(args.length!=1){
			System.err.println("Usage: java Fib value");
			System.exit(-1);
		}else{
			int n=Integer.parseInt(args[0]);
			setFuncs(n);	
			int i=0;
			for(Callable<Integer> func:funs)
			{
				Integer result=0;
				long startTime = System.nanoTime();
				result=FibFuncs(func);
				long endTime = System.nanoTime();
				System.out.println("value="+Integer.toString(result)+" time="
									+Long.toString(endTime-startTime)+" function name="+
									 funcNames[i++]);				
			}
	
		}//else
	}

}
