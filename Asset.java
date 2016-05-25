public class Asset
{
	public String ticker;
	public double price;
	public List<Asset> assets = new ArrayList<Asset>();

	public Asset(String ticker)
	{
		this.ticker = ticker;
		//TODO: this.price = 

		assets.add(this(ticker));
	}
}