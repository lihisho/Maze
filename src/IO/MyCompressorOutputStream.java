package IO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MyCompressorOutputStream extends OutputStream{

    private OutputStream out;

    public MyCompressorOutputStream(OutputStream os){
        out = os;

    }

    @Override
    public void write(int b) throws IOException {
        out.write(b);
    }
    public void write(byte[] b) throws IOException {
        out.write( (byte[]) compress(b));
        out.flush();
    }
    public byte[] compress(byte[] b) {
        boolean isZero = true;
        int counter=0; //counts the number of zeros\ones in a row.
        int i=0;
        //int j=0;
        ArrayList<Byte> toSend=new ArrayList<Byte>();

        // The first 9 bytes in the array, represent num of columns, start and goal positions.
        for(;i < 12; i++ ){
            // out.write(b[i]);
            toSend.add(b[i]);
            // j++;
        }

        // Save one byte that represents the first number of the array.
        if(b[i]==0) {
            isZero = true;
            toSend.add((byte)0);
            //out.write(0);
            //j++;
        }
        else{
            isZero =false;
            toSend.add((byte)1);
            //j++;

            //out.write(1);
        }

        // Count number of zeros\ones in the byte array and compress them by writing only number of occurrences.
        for (;i<b.length; i++) {

            if (isZero) {
                if (b[i] == 0)
                    counter++;
                else { // the last input written was 0 and the cuurent input is 1.
                    //out.write(0);
                    //out.write(counter);
                    toSend.add((byte)counter);
                    //j++;
                    counter = 1;
                    isZero = false;
                }
            } else {
                if (b[i] == 1)
                    counter++;
                else { // the last input written was 0 and the cuurent input is 1.
                    //out.write(1);
                    //out.write(counter);
                    toSend.add((byte)counter);
                    //j++;
                    counter = 1;
                    isZero = true;
                }
            }
            if (counter == 256) {
                if (isZero) {
                    //out.write(0);
                    // out.write(255);
                    toSend.add((byte)255);

                    //out.write(1);
                    //out.write(0);
                    toSend.add((byte)0);
                    // j++;
                    counter = 1;
                }
                else {
                    //out.write(1);
                    //out.write(255);
                    toSend.add((byte)255);
                    //j++;
                    //out.write(1);
                    //out.write(0);
                    toSend.add((byte)0);
                    //j++;

                    counter = 1;
                }
            }
        } //for
        // out.write(b[i-1]);
        toSend.add((byte)counter);
        byte[] newtosend=new byte[toSend.size()];
        for( int j=0;j<newtosend.length;j++)
            newtosend[j]=toSend.get(j);
        //out.close();
        return newtosend;


    }
    public void writeObject(byte []b) throws IOException{
        ( (ObjectOutputStream)out).writeObject((byte[])compress(b));
        out.flush();
    }

}