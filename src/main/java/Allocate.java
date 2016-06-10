import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;

// import yahoofinance.*;


public class Allocate
{
	private static double VAL;
	private static double settlementVAL;
	private static List<Asset> portfolio = new ArrayList<Asset>();
	private static final Calendar cal = Calendar.getInstance();
	

	public static void main(String[] args)
	{
		// My portfolio
		settlementVAL = 18713.66;
		portfolio.add(new Asset("VTI", 500.0, .2));
		portfolio.add(new Asset("JPIN", 67.42, .2));
		portfolio.add(new Asset("VGSIX", 11.93, .15));
		portfolio.add(new Asset("Long-term US treasuries", 100.27, .15));
		portfolio.add(new Asset("VWO", 1229.83, .1));
		portfolio.add(new Asset("GLD", 8, .075));
		portfolio.add(new Asset("VIPSX", 3044.94, .075));
		portfolio.add(new Asset("Speculation", 17472.1, .05));


		for (Asset a : portfolio) a.print();

		// Calculate portfolio balance 'VAL'
		for (Asset a : portfolio) VAL += a.bal;
		VAL = VAL + settlementVAL - 2000;	//keep 2k in settlement fund


		// Create then write output file
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        File dir = new File("src/main/allocations/" + sdf.format(cal.getTime()));
        dir.mkdirs();

        sdf = new SimpleDateFormat("M-d_" + toDollar(VAL));

		File f = new File(dir, sdf.format(cal.getTime()) + ".txt");

		writeFile(f);
	}





	private static void writeFile(File f)
	{
		try
		{
			PrintWriter pw = new PrintWriter(f);
			SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy");

			pw.println(sdf.format(cal.getTime()));
			pw.printf("Portfolio balance:  %s%n", toDollar(VAL));
			pw.println("\n--------------------------------");
			pw.println("\nALLOCATION:\n");
			pw.println("20%   Domestic stock (VTI)");
			pw.println("20%   International stock (JPIN)");
			pw.println("15%   REITs (VGSIX)");
			pw.println("15%   Long-term US treasuries");
			pw.println("10%   Emerging markets stock (VWO)");
			pw.println("7.5%  Gold (GLD)");
			pw.println("7.5%  TIPs (VIPSX)");
			pw.println("5%    Speculation");
			pw.println("\n--------------------------------");


			pw.println("\nTARGET BALANCES:");
			for (Asset a : portfolio)
			{
				pw.printf("%n%s (%s)%n", a.symbol, String.format("%.1f%%", 100 * a.allocation));
				pw.printf("  Target:  %s%n", toDollar(a.allocation * VAL));
				pw.printf("  Balance: %s%n", toDollar(a.bal));
				
				try {
					pw.println("  Price:   " + a.yfStock.getQuote().getPrice());
					double shareDif = Math.floor(((VAL * a.allocation) - (a.bal)) / a.yfStock.getQuote().getPrice().doubleValue());

					if (shareDif > 0) {
						pw.printf("  Buy %d shares%n", (int)shareDif);
					} else {
						pw.printf("  Sell %d shares%n", -1 * (int)shareDif);
					}
				} catch (NullPointerException ex) {
					double valDif = (a.allocation * VAL) - a.bal;

					if (valDif > 0) {
						pw.printf("Buy %s worth%n", toDollar(valDif));
					} else {
						pw.printf("Sell %s worth%n", toDollar(-1 * valDif));
					}
				}
			}

			pw.close();

		} catch (FileNotFoundException ex) {
			System.out.println(ex);
		}
	}






	/* Adds commas, and truncates to 2 decimal places */
	private static String toDollar(double x)
	{
		String str = String.format("%1$.2f", x);

		StringBuilder sb = new StringBuilder();

		sb.append(str.charAt(str.length()-1));
		sb.append(str.charAt(str.length()-2));
		sb.append(".");

		int count = 0;
		for (int i = str.length()-4; i >= 0; i--)
		{
			sb.append(str.charAt(i));
			count++;

			if (count == 3) {
				sb.append(",");
				count = 0;
			}
		}

		if (sb.charAt(sb.length()-1) == ',') sb.deleteCharAt(sb.length()-1);

		sb.append("$");

		return sb.reverse().toString();
	}

}