import org.jsoup.nodes.Document;

import java.util.stream.Stream;

public final class PartInfoParser {

    private static Document page;
    private static PartInfo partInfo;

    public static Stream<String> parseData(Document document) {
        page = document;
        partInfo = new PartInfo(
                parseName(),
                parsePriceRaw(),
                parsePriceDiscounted(),
                parseCount()
        );

        final String[] details = new String[]{""};

        page.select(".item.padded")
                .stream().forEach(i -> details[0] += String.format("\t%s\t%s", i.child(0).text(), i.child(1).text()));

        page.select(".auto-models-title");

        String csvItem = String.format("%s%s%s",
                partInfo.toString(), page.location(), details[0]);

        return Stream.of(csvItem);
    }

    private static String parseName() {
        return page
                .select(".item-inner-right > .label1")
                .text();
    }

    private static String parsePriceRaw() {
        return page
                .select(".primary .product-price-raw")
                .text();
    }

    private static String parsePriceDiscounted() {
        return page
                .select(".primary .product-price-with-discount")
                .text();
    }

    private static String parseCount() {
        return page
                .select("#show_item-info2")
                .text();
    }

}