import java.util.List;
import java.util.ArrayList;


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
		//TODO:
		this.price = 10;

		assets.add(this);
	}
}