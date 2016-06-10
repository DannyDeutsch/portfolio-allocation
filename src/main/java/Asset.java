import java.io.*;
import yahoofinance.*;

public class Asset
{
	public String symbol;
	public double bal;
	public int shares;
	public double allocation;
	public Stock yfStock;


	public Asset(String symbol, double bal, double allocation)
	{
		try {
			yfStock = YahooFinance.get(symbol);
			this.symbol = symbol;

			try {
				this.shares = (int)(bal / yfStock.getQuote().getPrice().doubleValue());
			} catch (NullPointerException ex) { }
		} catch(IOException ex) {
			System.out.println(ex);
		}

		// If asset is not a real security
		try {
			if (yfStock.getName() == null) {
				yfStock = null;
				this.symbol = symbol;
			}
		} catch (NullPointerException ex) {
			yfStock = null;
			this.symbol = symbol;
		}

		this.bal = bal;
		this.allocation = allocation;
	}


	public Asset(String symbol, int shares, double allocation)
	{
		try {
			yfStock = YahooFinance.get(symbol);
		} catch(IOException ex) {
			System.out.println(ex);
		}

		// If using this constructor, symbol will represent a real
		// security (you couldn't own shares otherwise!)

		this.symbol = symbol;
		this.shares = shares;
		this.allocation = allocation;

		// Calculate bal(shares)
		this.bal = yfStock.getQuote().getPrice().doubleValue() * (double)shares;
	}



	public void print()
	{
		System.out.println(this.symbol);
		System.out.println(this.shares + " shares");
		System.out.println(this.bal + " balance");
		System.out.println(this.allocation + " allocation");
		System.out.print("Price:  ");
		try {
			System.out.println(yfStock.getQuote().getPrice());
		} catch (NullPointerException ex) {
			System.out.println("Not a real security");
		}
		System.out.println();
	}
}