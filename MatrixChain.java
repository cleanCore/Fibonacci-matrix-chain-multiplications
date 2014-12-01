//package project2;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class MatrixChain {
	private int[][] m; // 0 row 
	private int[][] s;
	private int[]p=null;
	private int length=0;
	public static ArrayList<Callable<Integer>> funs=new ArrayList<Callable<Integer>>();
	public static String [] funcNames={"recurrsive","top_down","bottom_up"};
	public MatrixChain(int[] matrix)//p array from 0 to n
	{
		length=matrix.length-1;
		m=new int[length+1][length+1];
		s=new int[length+1][length+1];
		p=matrix;
	}
	
	public  <T> T MatrixFuncs(Callable<T> func) throws Exception
	{
		return func.call();
	}
	
	public void setFuncs(final int n,final MatrixChain matrixChain)
	{
		funs.add(new Callable<Integer>() {
			int n_;
			MatrixChain matrix_;
			{
				n_=n;
				matrix_=matrixChain;
			}
			@Override
		    public Integer call() {
		        return matrix_.matrixRecur(1,n_);
		   }
		});
		
		funs.add(new Callable<Integer>() {
			MatrixChain matrix_;
			{
				matrix_=matrixChain;
			}
			@Override
		    public Integer call() {
		        return matrix_.memorizedMatrixChain();
		   }
		});
		funs.add(new Callable<Integer>() {
			MatrixChain matrix_;
			{
				matrix_=matrixChain;
			}
			@Override
		    public Integer call() {
		        return matrix_.matrixChianBottomUp();
		   }
		});
		
		
	}
	
	public void init()
	{
		for(int i=0;i<length+1;i++)
			for(int j=0;j<length+1;j++){
				m[i][j]=0;
				s[i][j]=0;
			}
	}
	
	public int matrixChianBottomUp() //bottom_up
	{
		matrixBottomUp();
		return m[1][length];
	}
	
	private void matrixBottomUp()
	{
		for(int l=2;l<=length;l++){
			for(int i=1;i<=length-l+1;i++)
			{
				int j=i+l-1;
				m[i][j]=Integer.MAX_VALUE;
				for(int k=i;k<j;k++){
					int q=m[i][k]+m[k+1][j]+p[i-1]*p[k]*p[j];
					if(q<m[i][j]){
						m[i][j]=q;
						s[i][j]=k;
					}
				}
			}
		}//end for
	}//end function
	
	public int matrixRecur(int i,int j)// no dynamic programming recusrive
	//s-> 1to n-1
	{
		if(i==j)return 0;
		m[i][j]=Integer.MAX_VALUE;
		for(int k=i;k<j;k++){
			int q=matrixRecur(i,k)+matrixRecur(k+1,j)+p[i-1]*p[k]*p[j];
			if(q<m[i][j]){
				m[i][j]=q;
				s[i][j]=k;
			}
			
		}
		return m[i][j];
	}
		
	
	public int memorizedMatrixChain()
	{
		memorizedMatrixChainInit();
		return m[1][length];
	}
	
	private void memorizedMatrixChainInit()
	{
		for(int i=1;i<=length;i++)
			for(int j=1;j<=length;j++)
				m[i][j]=Integer.MAX_VALUE;
		lookUpChain(1,length);
	}
	
	private int lookUpChain(int i,int j)
	{
		if(m[i][j]<Integer.MAX_VALUE)return m[i][j];
		if(i==j)
			m[i][j]=0;
		else{
			for(int k=i;k<j;k++){
				int q=lookUpChain(i,k)+lookUpChain(k+1,j)+p[i-1]*p[k]*p[j];
				if(q<m[i][j])
				{
					m[i][j]=q;
					s[i][j]=k;
				}
			}
		}
		return m[i][j];
	}
	
	public void printParens(int i ,int j){
		if(i==j)System.out.print("A"+Integer.toString(i));
		else{
			System.out.print("(");
			printParens(i,s[i][j]);
			printParens(s[i][j]+1,j);
			System.out.print(")");
		}
	}

	public static void printErr()
	{
		System.err.println("wrong arguments! Usage: java MatrixChain -r[t,b] [<value>] [vector[value+1]]");
		System.exit(-1);
	}
	
	public static void main(final String[] args) throws Exception
	{
		if(args.length<3){
			System.err.println("Usage: java MatrixChain [<value>] [vector[value+1]]");
			System.exit(-1);
		}else{
			int dimensions=Integer.parseInt(args[0]);
			if(dimensions<1)printErr();
			int[] inputMatrix=new int[dimensions+1];
			for(int i=0;i<=dimensions;i++){
				if(args[i+1]=="")printErr();
				inputMatrix[i]=Integer.parseInt(args[i+1]);
				
			}
			
			
			//int []inputMatrix={10,4,5,8,2,20};// for debug on eclipse. 
			MatrixChain maxChain=new MatrixChain(inputMatrix);	
			maxChain.setFuncs(inputMatrix.length-1,maxChain);	
			int i=0;
			for(Callable<Integer> func:funs){
				Integer result=0;
				long startTime = System.nanoTime();
				result=maxChain.MatrixFuncs(func);
				long endTime = System.nanoTime();
				System.out.println();
				System.out.println(	" value="+Integer.toString(result)+
										" function name="+funcNames[i++]
										);
				System.out.println(" time= "+Long.toString(endTime-startTime));
				System.out.print(" optimal solutions:");
				maxChain.printParens(1,inputMatrix.length-1);
				System.out.println();			
				maxChain.init();
			}//for
		}//ESLE
	}

}
