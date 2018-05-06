
public final class PartInfo {

    private final String name;
    private final String priceRaw;
    private final String priceDiscounted;
    private final String count;

    public PartInfo(String name, String priceRaw, String priceDiscounted, String count) {
        this.name = name;
        this.priceRaw = priceRaw;
        this.priceDiscounted = priceDiscounted;
        this.count = count;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s\t",
                name, priceRaw, priceDiscounted, count);
    }

}