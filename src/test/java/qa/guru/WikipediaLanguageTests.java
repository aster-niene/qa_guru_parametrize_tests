package qa.guru;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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
                Arguments.of("en", List.of("Main page", "Contents", "Current events", "Random article", "About Wikipedia", "Contact us", "Donate")), //это количество вариантов. Если-б было несколько языков, то было-бы несколько строк
                Arguments.of("de", List.of("Hauptseite", "Themenportale", "Zufälliger Artikel"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Проверка наличия кнопок навигации {1} на сайте Wikipedia в локале {0}")
    void wikipediaLanguageButtonsTest(String locale, List<String> buttons){
        $$(".central-featured .central-featured-lang a").find(text(locale)).click();
        $$("#p-navigation a").filter(visible).shouldHave(texts(buttons));
    }
}
