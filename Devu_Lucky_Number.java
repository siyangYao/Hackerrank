import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */
        Scanner sc=new Scanner(System.in);
        String input=sc.nextLine();
        String[] nums=input.split(" ");
        int x=Integer.parseInt(nums[0]);
        int y=Integer.parseInt(nums[1]);
        int z=Integer.parseInt(nums[2]);
        System.out.println(devuLuckyNumber(x,y,z));
        
    }
    
    private static int mod=1000000007;
    
    /*
        I assume that there are only 4 and 5.
        When there are 1's 4 and 1's 5, the sum is 4+5+45+54
        When there are 2's 4 and 1's 5, the sum is 4+5+45+54+(44+454+445+544);
        
        It looks like a dynamic programming problem. The previous sum is pre=4+5+45+54, the current sum is
        sum=pre
            +4*100*2(100 is pow, 2 is the number of numbers which have 1's 4 and 1's 5; 45, 54)
            +99(the sum of numbers which have 1's 4 and 1's 5; 45+54)
            +5*100*1(100 is pow, 1 is the number of numbers which have 2's 4)
            
       Therefore, I use sum[x][y][z] to store the sum of numbers which have x's 4, y's 5, z's 6
       I use num[x][y][z] to store the number of numbers which have x's 4, y's 5, z's 6
    */
    public static long devuLuckyNumber(int x, int y, int z){
        long[][][] sum=new long[101][101][101]; // recode the sum of numbers which have x's 4, y's 5, z's 6
        int[][][] num=new int[101][101][101]; // recode the number of numbers which have x's 4, y's 5, z's 6
        long[] pow=new long[301];
        long result=0;
        num[0][0][0]=1;
        pow[0]=1;
        for(int i=1;i<=x+y+z;i++){
            pow[i]=(pow[i-1]*10)%mod;
        }
        for(int i=0;i<=x;i++){
            for(int j=0;j<=y;j++){
                for(int k=0;k<=z;k++){
                    if(i>0){
                        num[i][j][k]=(num[i][j][k]+num[i-1][j][k])%mod;
                        sum[i][j][k]=(sum[i][j][k]+sum[i-1][j][k]+4*pow[i+j+k-1]*num[i-1][j][k])%mod;
                    }
                    if(j>0){
                        num[i][j][k]=(num[i][j][k]+num[i][j-1][k])%mod;
                        sum[i][j][k]=(sum[i][j][k]+sum[i][j-1][k]+5*pow[i+j+k-1]*num[i][j-1][k])%mod;
                    }
                    if(k>0){
                        num[i][j][k]=(num[i][j][k]+num[i][j][k-1])%mod;
                        sum[i][j][k]=(sum[i][j][k]+sum[i][j][k-1]+6*pow[i+j+k-1]*num[i][j][k-1])%mod;
                    }
                    result=(result+sum[i][j][k])%mod;
                }
            }
        }
        return result;
    }
}