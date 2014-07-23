package com.qfc.yft.util;
import java.io.ByteArrayOutputStream;
import java.util.Random;

/**
 * A class to encode/decode Base64 string/bytes.
 * See RFC 1521 section 5.2 for details of the Base64 algorithm.
 * Replace Base64 encoder/decoder in org.webmacro.util.Base64
 * and com.oreilly.servlet.util.Base64* because they don't handle
 * binary correctly (where 8-bit is set).
 *
 * @author Alex Cheng
 */
public class Base64 {
    // map 6-bit int to char
    private static final char[] chars64 = {
        'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 
        'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 
        'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 
        'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', 
        '4', '5', '6', '7', '8', '9', '+', '/'
    };

    // map char to 6-bit int
    private static final int[] ints64 = new int[128];

    static {
        for (int i = 0; i < 64; i++) {
            ints64[chars64[i]] = i;
        }
    }

    ///////////////////////////////////////////////////////////////
    //                        测试
    //////////////////////////////////////////////////////////////
    public static String str = "This is a test";

    ///////////////////////////////////////////////////////////////
    //                        加密
    //////////////////////////////////////////////////////////////

    /**
     * 把字节数组转换成Base64字符�?     *
     * @param byte[] unencoded  --要转换的字节数组
     *
     * @return String --返回转换后的字符�?     *
     */
    public static final String encode(byte[] unencoded) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(
                                            (int) (unencoded.length * 1.37));
        int byteCount = 0;
        int carryOver = 0;

        for (int i = 0; i < unencoded.length; i++) {
            int bc = (byteCount % 3);
            byte b = unencoded[i];
            int lookup = 0;

            // First byte use first six bits, save last two bits
            if (bc == 0) {
                lookup = (b >> 2) & 0x3F;
                carryOver = b & 0x03; // last two bits
                out.write(chars64[lookup]);
            } else if (bc == 1) {
                // Second byte use previous two bits and first four new bits,
                // save last four bits
                lookup = ((carryOver << 4) | ((b >> 4) & 0x0F));
                carryOver = b & 0x0F; // last four bits
                out.write(chars64[lookup]);
            } else if (bc == 2) {
                // Third byte use previous four bits and first two new bits,
                // then use last six new bits
                lookup = ((carryOver << 2) | ((b >> 6) & 0x03));
                out.write(chars64[lookup]);

                lookup = b & 0x3F; // last six bits
                out.write(chars64[lookup]);
                carryOver = 0;
            }

            byteCount++;
        }

        if ((byteCount % 3) == 1) { // one leftover

            int lookup = (carryOver << 4) & 0xF0;
            out.write(chars64[lookup]);
            out.write('=');
            out.write('=');
        } else if ((byteCount % 3) == 2) { // two leftovers

            int lookup = (carryOver << 2) & 0x3C;
            out.write(chars64[lookup]);
            out.write('=');
        }

        return out.toString();
    }

    ///////////////////////////////////////////////////////////////
    //                        解密
    //////////////////////////////////////////////////////////////

    /**
     * 把Base64字符串解码为字节数组
     *
     * @param String encoded --加密过的字符�?     *
     * @return byte[] -- 返回解码后的字符数组
     */
    public static final byte[] decode(String encoded) {
    	if(encoded == null || encoded.length()<=0){
    		return null;
    	}
        byte[] bytes = encoded.getBytes();

        ByteArrayOutputStream out = new ByteArrayOutputStream(
                                            (int) (bytes.length * 0.67));
        int byteCount = 0;
        int carryOver = 0;

DECODE_LOOP: 
        for (int i = 0; i < bytes.length; i++) {
            int ch = bytes[i];

            if (ch == '=') {
                break DECODE_LOOP;
            }

            // Convert from raw form to 6-bit form
            int newbits = ints64[ch];

            int bc = (byteCount % 4);

            if (bc == 0) {
                // First char save all six bits, go for another
                carryOver = newbits & 0x3F;
            } else if (bc == 1) {
                // second char use 6 previous bits and first 2 new bits
                int data = ((carryOver << 2) + ((newbits >> 4) & 0x03));
                out.write(data);
                carryOver = newbits & 0x0F; // save 4 bits
            } else if (bc == 2) {
                // Third char use previous four bits and first four new bits,
                // save last two bits
                int data = ((carryOver << 4) + ((newbits >> 2) & 0x0F));
                out.write(data);
                carryOver = newbits & 0x03; // save 2 bits
            } else if (bc == 3) {
                // Fourth char use previous two bits and all six new bits
                int data = ((carryOver << 6) + (newbits & 0x3F));
                out.write(data);
                carryOver = 0;
            }

            byteCount++;
        }

        return out.toByteArray();
    }
    
    /**
     * 打印字符�?     *
     * @param String prompt  --要打印的字符串名�?     * @param String s       --要打印的字符�?     */
    private static void printString(String prompt, String s) {
        System.err.println("** " + prompt + "='" + s + "' (length=" + 
                           s.length() + ")");
    }

    /**
     * 打印字节
     *
     * @param String prompt  --要打印的字节名称
     * @param byte[] data --要打印的字节数组
     */
    public static void printBytes(String prompt, byte[] data) {
        System.err.print("** " + prompt + " = [");

        for (int i = 0; i < data.length; i++) {
            String sep = ",";

            if (i == 0) {
                sep = "";
            }

            System.err.print(sep + data[i]);
        }

        System.err.println("] (length=" + data.length + ")");
    }
    
    // 测试入口
    public static void main(String[] args) throws Exception {
    	
    	test1();
//    	test2(args);
    	
        if(args.length > 0) {
        	String temp = args[0];
			System.err.println(temp);
			temp = encode(temp.getBytes());
			System.err.println(temp);
			temp = new String(decode(temp));
            System.err.println(temp);
        }
    }
    
    // 加解密默认使用UTF-8字符�?    
    public static void test1() throws Exception {
    	
    	String str = "12345我abcde";
    	
    	String encoded = encode(str.getBytes("GBK"));
    	System.out.println(encoded);
    	
    	byte[] decode = decode(encoded);
    	System.out.println(new String(decode, "GBK"));
    }
    
    // 测试函数
    public static void test2(String[] args) throws Exception {
        int count = 30;
        
        if (args.length > 0) {
            count = Integer.parseInt(args[0]);
        }
        
        if (count <= 0) {
            count = 30;
        }
        
        byte[] data = new byte[count];
        Random rand = new Random();
        rand.nextBytes(data);
        printBytes("Source", data);
        
        String encoded = Base64.encode(data);
        printString("Encoded", encoded);
        
        byte[] bin = Base64.decode(encoded);
        printBytes("Final", bin);
        
        if (bin.length != data.length) {
            System.err.println("::FAILURE::");
            
            return;
        }
        
        for (int i = 0; i < bin.length; i++) {
            if (bin[i] != data[i]) {
                System.err.println("::FAILURE::");
                
                return;
            }
        }
        
        System.err.println("::SUCCESS::");
    }
}