import org.jsoup.nodes.Document;

import java.util.Arrays;
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

        final String[] partDetails = new String[PartDetailType.values().length];
        Arrays.fill(partDetails, "");

        page.select(".item.padded")
                .stream()
                .forEach(detail -> {
                    int detailTypeIndex = PartDetailType.fromText(detail.child(0).text()).ordinal();
                    partDetails[detailTypeIndex] = detail.child(1).text();
                });

//        page.select(".auto-models-title");

        String csvItem = String.format("%s%s\t%s",
                partInfo.toString(), String.join("\t", partDetails), page.location());

        System.out.println(csvItem);

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