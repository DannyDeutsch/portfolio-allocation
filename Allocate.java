import java.util.*;
import java.io.*;

import java.text.SimpleDateFormat;


public class Allocate
{
	
	public static void main(String[] args)
	{
		// Read value from stdin
		Scanner scanner = new Scanner(System.in);
		String valStr;

		System.out.print("Enter current portfolio value:\n> $");

		valStr = scanner.next();


		// Handle commas in input
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < valStr.length(); i++) {
			if (valStr.charAt(i) == ',') continue;

			sb.append(valStr.charAt(i));
		}

		double VAL = Double.parseDouble(sb.toString());


		// Build portfolio
		Asset domestic = new Asset("vti", .2);
		Asset intl     = new Asset("jpin", .2);
		Asset reits    = new Asset("vgsix", .15);
		Asset emerging = new Asset("vwo", .1);
		Asset treas    = new Asset("long-term treasuries", .1);
		Asset gold     = new Asset("gld", .075);
		Asset tips     = new Asset("vipsx", .075);
		Asset spec     = new Asset("speculation", .05);


		// Get timestamp of file created
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");

        File dir = new File("allocations/" + sdf.format(cal.getTime()));
        dir.mkdirs();

        sdf = new SimpleDateFormat("MM-dd_" + valStr);

		File f = new File(dir, sdf.format(cal.getTime()) + ".txt");

		try
		{
			PrintWriter pw = new PrintWriter(f);

			sdf = new SimpleDateFormat("MMMM dd, yyyy");

			pw.println(sdf.format(cal.getTime()));
			pw.println("\nCurrent portfolio value:  " + toDollar(VAL));

			pw.println("\n--------------------------------");
			pw.println("\nALLOCATION:");

			pw.println("\n20%   Domestic stock (VTI)");
			pw.println("\n20%   International stock (JPIN)");
			pw.println("\n15%   REITs (VGSIX)");
			pw.println("\n15%   Long-term US treasuries (?)");
			pw.println("\n10%   Emerging markets stock (VWO)");
			pw.println("\n7.5%  Gold (GLD)");
			pw.println("\n7.5%  TIPs (VIPSX)");
			pw.println("\n5%    Speculation");

			pw.println("\n--------------------------------");
			pw.println("\nTARGET VALUES:");

			for (Asset a : Asset.assets) {
				pw.printf("%n%s:  %s%n", a.ticker, toDollar(a.allocation * VAL));
			}

			pw.print("\n--------------------------------");

			pw.close();
		}
		catch (FileNotFoundException ex) { System.out.println(ex); }

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