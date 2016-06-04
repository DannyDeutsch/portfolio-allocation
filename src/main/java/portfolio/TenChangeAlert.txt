import java.util.*;

public class Testing
{
    public static void main(String[] args)
    {
    	double mark;
    	double curPrice;

    	//TODO: pull current price
    	curPrice = 48.54;

    	mark = curPrice;

    	every weekday after market closing
    	{
    		grab curPrice

    		// Check if +-10%
    		if (curPrice <= .9 * mark) {
    			mark = curPrice;
    			text message alert
    		}
    		else if (curPrice >= 1.1 * mark) {
    			mark = curPrice;
    			text message alert
    		}
    	}
    }
}
