import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalTime;
import java.util.Optional;
import java.util.stream.Stream;

public final class AutoAibe {

    public static final String TAB = "\t";
    private static final String FULL_URL = "https://www.autoaibe.lt/detales/stabdziu-diskai/page/1";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox";
    private static final String FILE_NAME = "results.csv";
    private static long COUNT = 0;

    public static void scrap() {
        try (PrintStream output = new PrintStream(new FileOutputStream(new File(FILE_NAME), false))) {
            Document partsPage = getDocument(FULL_URL)
                    .orElseThrow(() -> new RuntimeException("Could not get list page"));

            parseUrls(partsPage)
                    .parallel()
                    .map(AutoAibe::getDocument)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .flatMap(AutoAibe::parseData)
                    .forEach(d -> writeToFile(output, d));

        } catch (IOException e) {
            System.err.println("Could not open " + FILE_NAME + " file, due to: " + e.getMessage());
        }
    }

    private static Stream<String> parseData(Document document) {
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

    private static Stream<String> parseUrls(Document html) {
        Stream<String> res = html
                .select(".itemai > .image")
                .stream()
                .map(e -> e.attr("href"));

        String nextPageUrl = html.select(".top-filter-wrap .right").attr("href");

        if (!nextPageUrl.isEmpty()) {
//            Document nextDocument = getDocument(nextPageUrl).get();
//            res = Stream.concat(res, parseUrls(nextDocument));
        }

        return res;
    }

    private static Optional<Document> getDocument(String url) {
        COUNT++;
        Document document = null;

        try {
            document = Jsoup
                    .connect(url)
                    .userAgent(USER_AGENT)
                    .get();
            System.out.println(LocalTime.now() + " " + url + " " + COUNT);

        } catch (IOException e) {
            System.err.println(LocalTime.now() + " [" + COUNT + "] Could not get document from: " + url + ", due to: " + e.getMessage());
        }

        return Optional.ofNullable(document);
    }

    private static synchronized void writeToFile(PrintStream writer, String csv) {
        writer.append(csv).append("\n");
    }
}
