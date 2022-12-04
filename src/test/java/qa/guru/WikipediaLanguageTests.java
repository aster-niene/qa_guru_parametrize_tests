package qa.guru;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class WikipediaLanguageTests {

    @BeforeEach
    void setUp(){
        Selenide.open("https://www.wikipedia.org/");
    }

    @AfterEach
    void close(){
        closeWebDriver();
    }

    static Stream<Arguments> wikipediaLanguageButtonsTest(){
        return Stream.of(
                Arguments.of("en", List.of("Main page", "Contents", "Current events", "Random article", "About Wikipedia", "Contact us", "Donate")),
                Arguments.of("de", List.of("Hauptseite", "Themenportale", "Zufälliger Artikel"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Checking for Navigation Buttons {1} on the Wikipedia site in local {0}")
    void wikipediaLanguageButtonsTest(String locale, List<String> buttons){
        $$(".central-featured .central-featured-lang a").find(text(locale)).click();
        $$("#p-navigation a").filter(visible).shouldHave(texts(buttons));
    }

    @ParameterizedTest(name = "Checking wikipedia search for word {0}, base fo Csv file")
    @CsvFileSource(
            resources = "/data.csv"
    )
    void wikipediaLanguageButtonsTestCsvFile(String searchWord, String resultWord){
        $$(".central-featured .central-featured-lang a").find(text("en")).click();
        $(".vector-search-box-input").setValue(searchWord).pressEnter();
        $(".mw-page-title-main").shouldHave(text(resultWord));
    }

    @ValueSource(strings = {
            "Test",
            "Program"
            })
    @ParameterizedTest(name = "Checking wikipedia search for word {0}")
    void searchForWords(String word) {
        $$(".central-featured .central-featured-lang a").find(text("en")).click();
        $(".vector-search-box-input").setValue(word).pressEnter();
        $(".mw-page-title-main").shouldHave(text(word));
    }
}
