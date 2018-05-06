import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

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

        page.select(".item.padded").forEach(detail -> {
            int detailTypeIndex = PartDetailType.fromText(detail.child(0).text()).ordinal();
            partDetails[detailTypeIndex] = detail.child(1).text();
        });

        String suitableAutoModels = parseSuitableAutoModels();

        String csvItem = String.format("%s%s\t%s\t%s",
                partInfo.toString(), String.join("\t", partDetails), suitableAutoModels, page.location());

        return Stream.of(csvItem);
    }

    private static String parseSuitableAutoModels() {
        String joinedLines = "";

        String[] autoTitles = selectFromPage(".auto-models-title");
        String[] autoValues = selectFromPage(".auto-models-value");

        for (int i = 0; i < autoTitles.length; i++) {
            joinedLines += String.format("%s: %s<br>", autoTitles[i], autoValues[i]);
        }

        return joinedLines;
    }

    private static String[] selectFromPage(String selector) {
        return page
                .select(selector)
                .stream()
                .map(Element::text)
                .toArray(String[]::new);
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