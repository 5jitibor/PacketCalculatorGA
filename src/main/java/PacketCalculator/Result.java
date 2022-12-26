package PacketCalculator;
import java.math.BigInteger;

public class Result {
	private BigInteger numFramesCells;
	private BigInteger numBytes;
	private BigInteger numBytesPadding;
	private double l3l2Efficiency;
	private String type;
	
	
	
	public Result(BigInteger numFramesCells, BigInteger numBytes, BigInteger numBytesPadding, double l3l2Efficiency,String type) {
		this.numFramesCells = numFramesCells;
		this.numBytes = numBytes;
		this.numBytesPadding = numBytesPadding;
		this.l3l2Efficiency = l3l2Efficiency;
		this.type = type;
	}
	public BigInteger getNumFramesCells() {
		return numFramesCells;
	}
	public void setNumFramesCells(BigInteger numFramesCells) {
		this.numFramesCells = numFramesCells;
	}
	public BigInteger getNumBytes() {
		return numBytes;
	}
	public void setNumBytes(BigInteger numBytes) {
		this.numBytes = numBytes;
	}
	public BigInteger getNumBytesPadding() {
		return numBytesPadding;
	}
	public void setNumBytesPadding(BigInteger numBytesPadding) {
		this.numBytesPadding = numBytesPadding;
	}
	public double getL3l2Efficiency() {
		return l3l2Efficiency;
	}
	public void setL3l2Efficiency(double l3l2Efficiency) {
		this.l3l2Efficiency = l3l2Efficiency;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void showResult(boolean isPadding) {
		System.out.print(type + "\n" +
                      numBytes + " bytes\n" +
                      numFramesCells);

        if (type.equals("Ethernet.1Q") || type.equals("Ethernet")) {
        	System.out.println(" frames");
        }
        else {
        	System.out.println(" cells");
        }
        
        System.out.println(l3l2Efficiency + " % L3/L2 Efficiency");
		if(isPadding){
			System.out.println(numBytesPadding + " bytes of padding\n");
		}
		else{
			System.out.println();
		}

    }
	

}
