package PacketCalculator;
import java.math.BigInteger;
import java.util.ArrayList;

public class Calculator {
    private final static BigInteger ATMPACKETSIZE = BigInteger.valueOf(53);
    private final static BigInteger ATMPACKETHEADERSIZE = BigInteger.valueOf(5);
    private final static BigInteger ATM34SIZE = BigInteger.valueOf(4);
    private final static BigInteger AAL5SIZE = BigInteger.valueOf(8);
    private final static BigInteger AAL5PAYLOADSIZE = ATMPACKETSIZE.subtract(ATMPACKETHEADERSIZE);
    private final static BigInteger AAL5MAXPAYLOADSIZE = BigInteger.valueOf(9180);
    private final static BigInteger AAL34PAYLOADSIZE = ATMPACKETSIZE.subtract(ATMPACKETHEADERSIZE).subtract(ATM34SIZE);
    private final static BigInteger ETHERNETPAYLOADSIZE = BigInteger.valueOf(1500);
    private final static BigInteger MINETHERNETPAYLOADSIZE = BigInteger.valueOf(46);
    private final static BigInteger ETHERNETHEADERSIZE = BigInteger.valueOf(18);
    private final static BigInteger ETHERNET1QHEADERSIZE = ETHERNETHEADERSIZE.add(BigInteger.valueOf(4));
    private final static BigInteger EMPTY = BigInteger.ZERO;
    private final static double PERCENTAGE = 100.0;

    public static ArrayList<Result> calculateResults(ArrayList<TypeProtocol> typeList, BigInteger numbytes, boolean isPadding) {
    	ArrayList<Result> resultsList = new ArrayList<Result>();
        for (TypeProtocol type : typeList) {
            switch (type) {
                case AAL5oATM:
                    resultsList.add(aal5oatm(numbytes, isPadding));
                    break;
                case AAL34oATM:
                	resultsList.add(aal34oatm(numbytes, isPadding));
                    break;
                case Ethernet:
                	resultsList.add(ethernet(numbytes, isPadding));
                    break;
                case Ethernet1Q:
                	resultsList.add(ethernet1Q(numbytes, isPadding));
                    break;
            }

        }
        return resultsList;

    }

    public static Result aal5oatm(BigInteger b, boolean isPadding)
    {
        BigInteger numTotalBytes = EMPTY;
        BigInteger numCells9180 = EMPTY;
        BigInteger numBytesPadding9180 = EMPTY;

        if (b.compareTo(AAL5MAXPAYLOADSIZE)>0) {
            Result maxValue = aal5oatm(AAL5MAXPAYLOADSIZE, isPadding);
            numTotalBytes = maxValue.getNumBytes().multiply(b.subtract(BigInteger.ONE).divide(AAL5MAXPAYLOADSIZE));
            numCells9180 = maxValue.getNumFramesCells().multiply(b.subtract(BigInteger.ONE).divide(AAL5MAXPAYLOADSIZE));
            numBytesPadding9180 = maxValue.getNumBytesPadding().multiply(b.subtract(BigInteger.ONE).divide(AAL5MAXPAYLOADSIZE));
        }
        
        BigInteger numRealBytes = (b.subtract(BigInteger.ONE)).mod(AAL5MAXPAYLOADSIZE).add(BigInteger.ONE).add(AAL5SIZE);
        
        BigInteger numCells = calculateCellsFrames(numRealBytes, AAL5PAYLOADSIZE);
       
        BigInteger numBytesPadding = EMPTY;

        numBytesPadding = numBytesPadding.add(numCells.multiply(AAL5PAYLOADSIZE).subtract(numRealBytes));

        numTotalBytes = numTotalBytes.add(ATMPACKETHEADERSIZE.multiply(numCells).add(numBytesPadding).add(numRealBytes));

        return new Result(numCells.add(numCells9180), numTotalBytes,numBytesPadding.add(numBytesPadding9180), calculateL3l2Efficiency(b, numTotalBytes), "AAL5oATM");
    }

    public static Result aal34oatm(BigInteger b, boolean isPadding)
    {

    	BigInteger numCells = calculateCellsFrames(b, AAL34PAYLOADSIZE);
    	
        BigInteger numBytesPadding = EMPTY;

        numBytesPadding = numBytesPadding.add(numCells.multiply(AAL34PAYLOADSIZE).subtract(b));

        BigInteger numTotalBytes = ATMPACKETHEADERSIZE.add(ATM34SIZE).multiply(numCells).add(numBytesPadding).add(b);

        return new Result(numCells, numTotalBytes, numBytesPadding, calculateL3l2Efficiency(b, numTotalBytes), "AAL3/4oATM");

    }

    public static Result ethernet(BigInteger b, boolean isPadding){ return ethernetCommon(b, isPadding, ETHERNETHEADERSIZE, "Ethernet"); }

    public static Result ethernet1Q(BigInteger b, boolean isPadding){ return ethernetCommon(b, isPadding, ETHERNET1QHEADERSIZE, "Ethernet.1Q"); }

    public static Result ethernetCommon(BigInteger b, boolean isPadding, BigInteger numHeaderSize, String type)
    {

    	BigInteger numFrames = calculateCellsFrames(b, ETHERNETPAYLOADSIZE);
    	
        BigInteger numBytesPadding = EMPTY;
        

        if (b.mod(ETHERNETPAYLOADSIZE).compareTo(MINETHERNETPAYLOADSIZE) < 0)
        {
            numBytesPadding = MINETHERNETPAYLOADSIZE.subtract(b.mod(ETHERNETPAYLOADSIZE));
        }
        BigInteger numTotalBytes = b.add(numHeaderSize.multiply(numFrames)).add(numBytesPadding);

        return new Result(numFrames, numTotalBytes, numBytesPadding, calculateL3l2Efficiency(b, numTotalBytes), type);

    }

    public static double calculateL3l2Efficiency(BigInteger l2, BigInteger l3)
    {
        double l3l2 = (l2.doubleValue() / l3.doubleValue()) * PERCENTAGE;
        return Math.round(l3l2 * PERCENTAGE) / PERCENTAGE;

    }
    
    
    public static BigInteger calculateCellsFrames(BigInteger b, BigInteger payload)
    {
    	BigInteger[] resultsDivision = b.divideAndRemainder(payload);
        
        BigInteger numCells = resultsDivision[0];
        
        if(resultsDivision[1].compareTo(BigInteger.ZERO)!=0) {
        	numCells= numCells.add(BigInteger.ONE);
        }
        return numCells;

    }
    
}
