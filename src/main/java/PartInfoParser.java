import org.jsoup.nodes.Document;

import java.util.stream.Stream;

public final class PartInfoParser {

    public static Stream<String> parseData(Document document) {
        String name = document.select(".item-inner-right > .label1").text();
        String priceRaw = document.select(".primary .product-price-raw").text();
        String priceDiscount = document.select(".primary .product-price-with-discount").text();
        String count = document.select("#show_item-info2").text();

        final String[] details = new String[]{""};

        document.select(".item.padded")
                .stream().forEach(i -> details[0] += String.format("\t%s\t%s", i.child(0).text(), i.child(1).text()));

        document.select(".auto-models-title");

        String csvItem = String.format("%s\t%s\t%s\t%s\t%s%s",
                name, priceRaw, priceDiscount, count, document.location(), details[0]);

        return Stream.of(csvItem);
    }

}