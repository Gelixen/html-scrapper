
public final class PartInfo {

    private String name;
    private String priceRaw;
    private String priceDiscounted;
    private String count;

    private String mountedOn;
    private String height;
    private String thickness;
    private String diameter;
    private String holesCount;
    private String allowedMinimum;
    private String type;
    private String surface;
    private String brand;
    private String eanCode;

    public PartInfo(String name, String priceRaw, String priceDiscounted, String count) {
        this.name = name;
        this.priceRaw = priceRaw;
        this.priceDiscounted = priceDiscounted;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPriceRaw() {
        return priceRaw;
    }

    public void setPriceRaw(String priceRaw) {
        this.priceRaw = priceRaw;
    }

    public String getPriceDiscounted() {
        return priceDiscounted;
    }

    public void setPriceDiscounted(String priceDiscounted) {
        this.priceDiscounted = priceDiscounted;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getMountedOn() {
        return mountedOn;
    }

    public void setMountedOn(String mountedOn) {
        this.mountedOn = mountedOn;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getThickness() {
        return thickness;
    }

    public void setThickness(String thickness) {
        this.thickness = thickness;
    }

    public String getDiameter() {
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }

    public String getHolesCount() {
        return holesCount;
    }

    public void setHolesCount(String holesCount) {
        this.holesCount = holesCount;
    }

    public String getAllowedMinimum() {
        return allowedMinimum;
    }

    public void setAllowedMinimum(String allowedMinimum) {
        this.allowedMinimum = allowedMinimum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getEanCode() {
        return eanCode;
    }

    public void setEanCode(String eanCode) {
        this.eanCode = eanCode;
    }

    @Override
    public String toString() {
        return String.format("%s\t%s\t%s\t%s\t",
                name, priceRaw, priceDiscounted, count);
    }

}