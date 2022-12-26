package PacketCalculatorCLI;
import java.math.BigInteger;
import java.util.ArrayList;

import Commons.Bytes;
import PacketCalculator.Calculator;
import PacketCalculator.Result;
import PacketCalculator.TypeProtocol;

public class CLI {
	
	public static void executeConsole(String[] args){
		String numbytesString = "-1";

        ArrayList<String> types = new ArrayList<String>();

        boolean isPadding = false;
        boolean isType = true; 

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "?":
                    System.out.println(" -B option to introduce the number of bytes that are send\n -L2 option to set the L2 protocols for the calculations. Protocols AAL5oATM, AAL3/4oATM, Ethernet and Ethernet.1Q must be separated by a space.\n -P option to set that padding bytes must be included in the results too\n ? option to show the parameters that can be used to execute the program");
                    return;
                case "-B":
                    if (!args[i+1].contains("-")) {
                        i++;
                        numbytesString = args[i];   
                    }
                    
                    break;
                case "-P":
                    isPadding = true;
                    break;
                case "-L2":
                    i++;
                    do
                    {
                        switch (args[i].toLowerCase())
                        {
                            case "aal5oatm":
                            case "aal34oatm":
                            case "ethernet":
                            case "ethernet1q":
                            	if(!types.contains(args[i].toLowerCase())) {
                            		types.add(args[i].toLowerCase());
                            	}
                                i++;
                                break;
                            default:
                                isType = false;
                                i--;
                                break;
                        }
                    } while (isType && i < args.length);
                    
                    break;
            }

        }

        BigInteger numBytes = Bytes.getnumBytes(numbytesString);
        if (!numBytes.equals(BigInteger.valueOf(-1)) && types.size() > 0)
        {
            ArrayList<Result> resultsList = Calculator.calculateResults(selectTypes(types), numBytes, isPadding);
            for (Result result : resultsList)
            {
                result.showResult(isPadding);
            }
        }
        else {
            if (numBytes.equals(BigInteger.valueOf(-1)))
            {
            	System.out.println("ERROR: Bytes number not valid");
            }
            if (types.size() == 0) {
            	System.out.println("ERROR: You must select at least one protocol");
            }
            System.out.println("Use example: .\\PacketCalculatorCLI.exe -B 80B -L2 AAL5oATM Ethernet -P");
            System.out.println("For more info: .\\PacketCalculatorCLI.exe ?");

        }
        
	}
	
	

    private static ArrayList<TypeProtocol> selectTypes(ArrayList<String> typesString) {
    	ArrayList<TypeProtocol> typeProtocolsList = new ArrayList<TypeProtocol>();
        for(String typeProtocolString : typesString) {
                switch (typeProtocolString.toLowerCase())
                {
                    case "aal5oatm":
                        typeProtocolsList.add(TypeProtocol.AAL5oATM);
                        break;
                    case "aal34oatm":
                        typeProtocolsList.add(TypeProtocol.AAL34oATM);
                        break;
                    case "ethernet":
                        typeProtocolsList.add(TypeProtocol.Ethernet);
                        break;
                    case "ethernet1q":
                        typeProtocolsList.add(TypeProtocol.Ethernet1Q);
                        break;
                }
            
        }

        return typeProtocolsList;

    }

}
