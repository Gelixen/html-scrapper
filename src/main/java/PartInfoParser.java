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

    public enum PartDetailType {
        MOUNTED_ON("Montuojama"),
        HEIGHT("Auk\u0161tis, mm"),
        THICKNESS("Storis, mm"),
        CENTER_DIAMETER("Centr.skersmuo, mm"),
        OUTER_DIAMETER("I\u0161orinis skersmuo, mm"),
        HOLES_COUNT("Angos"),
        ALLOWED_MINIMUM("Leistinas minimumas, mm"),
        TYPE("Stabd\u017ei\u0173 disko tipas"),
        INNER_DIAMETER("\u012edubimo skersmuo, mm"),
        BRAND("Prek\u0117s \u017eenklas"),
        EAN_CODE("EAN kodas"),
        REQUIRED_AMMOUNT("Reikiamas kiekis"),
        DIAMETER("Skersmuo, mm"),
        SURFACE("Pavir\u0161ius"),
        BREAK_SYSTEM("Stabd\u017ei\u0173 sistema"),
        NOTES("Pastabos"),
        NO_MATCH("Nerasta");

        private final String value;

        PartDetailType(String value) {
            this.value = value;
        }

        public static PartDetailType fromText(String text) {
            for (PartDetailType detail : PartDetailType.values()) {
                if (detail.getValue().equals(text)) {
                    return detail;
                }
            }

            return NO_MATCH;
        }

        public static String[] getAllValues() {
            return Arrays
                    .stream(values())
                    .map(PartDetailType::getValue)
                    .toArray(String[]::new);
        }

        public String getValue() {
            return value;
        }
    }
}