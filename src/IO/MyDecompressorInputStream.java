package IO;

import java.io.IOException;
import java.io.InputStream;

public class MyDecompressorInputStream extends InputStream{
    private InputStream in;

    public MyDecompressorInputStream(InputStream is){
        in = is ;

    }

    @Override
    public int read() throws IOException {
        if(in.available() == 0)
            return -1;
        return 0;//????
    }


    @Override
    public int read(byte[] b) throws IOException {
        if (b==null)
            throw new NullPointerException();
        else if (b.length==0)
            return 0;
        if(in.available() == 0)
            return -1;
        int i=0;
        int j;
        byte curr;
        byte [] inputArray=new byte[b.length];
        in.read(inputArray);

        for(; i<12; i++ ){
            //b[i] =(byte)in.read();
            b[i]=inputArray[i];
        }
        int k=i;
        //if(in.read()== 0)
        if(inputArray[k]==0) {
            k++;
            curr = 0;
        }
        else {
            curr = 1;
            k++;
        }
        for(;k<inputArray.length;k++){
            j=inputArray[k];
            //while ((j = in.read()) != -1 && i<b.length){
            if (curr ==0){
                while (j>0){
                    b[i]=curr;
                    i++;
                    j--;
                }
                curr=1;
            }
            else{
                while (j>0){
                    b[i]=curr;
                    i++;
                    j--;
                }
                curr=0;
            }
        }
        return i;
    }
}