package Commons;

import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Bytes {
	public static final String[] size = {"B","KB","MB","GB","TB"};
	
	public static BigInteger getnumBytes(String numBytesStringAux) {
        String lowerNumBytesString = numBytesStringAux.toLowerCase();
        Pattern pat = Pattern.compile("^[1-9][0-9]*(g|m|k|t)?b");
        Matcher mat= pat.matcher(lowerNumBytesString);  
        if (mat.matches()) {
        	pat = Pattern.compile("^[0-9]+b");
        	mat = pat.matcher(lowerNumBytesString);  	
            if (mat.matches())
            {
                return new BigInteger(lowerNumBytesString.split("b")[0]);
            }
            pat = Pattern.compile("^[0-9]+kb");
            mat = pat.matcher(lowerNumBytesString);  	
            if (mat.matches())
            {
                return new BigInteger(lowerNumBytesString.split("kb")[0]).multiply(BigInteger.valueOf(1024).pow(1));
            }
            pat = Pattern.compile("^[0-9]+mb");
            mat = pat.matcher(lowerNumBytesString);  	
            if (mat.matches())
            {
                return new BigInteger(lowerNumBytesString.split("mb")[0]).multiply(BigInteger.valueOf(1024).pow(2));
            }
            pat = Pattern.compile("^[0-9]+gb");
            mat = pat.matcher(lowerNumBytesString);  	
            if (mat.matches())
            {
                return new BigInteger(lowerNumBytesString.split("gb")[0]).multiply(BigInteger.valueOf(1024).pow(3));
            }
            pat = Pattern.compile("^[0-9]+tb");
            mat = pat.matcher(lowerNumBytesString);  	
            if (mat.matches())
            {
                return new BigInteger(lowerNumBytesString.split("tb")[0]).multiply(BigInteger.valueOf(1024).pow(4));
            }
        }
        return BigInteger.valueOf(-1);


    }
}
