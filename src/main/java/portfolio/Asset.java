package portfolio;

import java.util.List;
import java.util.ArrayList;

// import com.yahoofinance-api;


public class Asset
{
	public String ticker;
	public double allocation;
	public double price;
	public static List<Asset> assets = new ArrayList<Asset>();

	public Asset(String ticker, double allocation)
	{
		this.ticker = ticker;
		this.allocation = allocation;

		//TODO: retrieve current price from Yahoo Finance
		this.price = 10;

		assets.add(this);
	}
}