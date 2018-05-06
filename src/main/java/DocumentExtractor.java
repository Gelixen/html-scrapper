import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Optional;

public final class DocumentExtractor {

    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:57.0) Gecko/20100101 Firefox";
    private static Document document;

    public static Optional<Document> getDocument(String url) {
        try {
            document = Jsoup
                    .connect(url)
                    .userAgent(USER_AGENT)
                    .get();

        } catch (IOException e) {
            System.err.println(LocalTime.now() + " Could not get document from: " + url + ", due to: " + e.getMessage());
        }

        return Optional.ofNullable(document);
    }
}