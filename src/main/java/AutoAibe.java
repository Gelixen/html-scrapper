import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Stream;

public final class AutoAibe {

    private static final String FIRST_URL = "https://www.autoaibe.lt/detales/stabdziu-diskai/page/1";
    private static final String FILE_NAME = "results.csv";
    private static final boolean ONLY_FIRST_PAGE = true;

    public static void scrap() {
        try (PrintStream output = new PrintStream(new FileOutputStream(new File(FILE_NAME), false), true, StandardCharsets.UTF_8.name())) {
            Document firstPartsPage = DocumentExtractor.getDocument(FIRST_URL)
                    .orElseThrow(() -> new RuntimeException("Could not get first page"));

            scrapAllLinks(firstPartsPage)
                    .parallel()
                    .map(DocumentExtractor::getDocument)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .flatMap(PartInfoParser::parseData)
                    .forEach(d -> writeToFile(output, d));

        } catch (IOException e) {
            System.err.println("Could not open " + FILE_NAME + " file, due to: " + e.getMessage());
        }
    }

    private static Stream<String> scrapAllLinks(Document page) {
        Stream<String> parsedLinks = page
                .select(".itemai > .image")
                .stream()
                .map(e -> e.attr("href"));

        String nextPageUrl = parseNextPageUrl(page);

        if (!StringUtil.isBlank(nextPageUrl)) {
            Document nextDocument = DocumentExtractor.getDocument(nextPageUrl).get();
            parsedLinks = Stream.concat(parsedLinks, scrapAllLinks(nextDocument));
        }

        return parsedLinks;
    }

    private static String parseNextPageUrl(Document page) {
        if (ONLY_FIRST_PAGE) {
            return null;
        }

        return page
                .select(".top-filter-wrap .right")
                .attr("href");

    }

    private static synchronized void writeToFile(PrintStream writer, String csv) {
        writer.append(csv).append("\n");
    }
}
