package com.tarasov;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;


import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.CollectionCondition.*;
import static com.codeborne.selenide.Selenide.*;

public class Junit5TestLaunch {


    @BeforeEach
    public void setUp(){
        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        open("https://marmelab.com/react-admin-demo/#/login");
    }


    @CsvSource(
            {
                    "demo, demo",
                    "test, test"
            }
    )
    @ParameterizedTest(name = "Login {0}, password {1}")
    @Tag("UI_TEST")
    public void logIn(String name, String password) {
        $("#username").setValue(name);
        $("#password").setValue(password);
        $(byText("Sign in")).click();
    }

    @CsvFileSource(resources = "/data.csv")
    @DisplayName("Customers test")
    @ParameterizedTest(name = "On {0} should be more or equal {1} customers")
    @Tag("UI_TEST")
    public void checkCustomersSize(String page, int size) {
        $("#username").setValue("demo");
        $("#password").setValue("demo");
        $(byText("Sign in")).click();
        $(".RaSidebar-fixed").$x(".//a[@href='#/customers']").click();
        $(".MuiTableBody-root").shouldBe(Condition.visible).$$("tr").shouldBe(CollectionCondition.sizeGreaterThanOrEqual(size));
    }


    @MethodSource("dataForSideBar")
    @ParameterizedTest(name = "On page {0} should be visible {1}")
    @Tag("UI_TEST")
    public void checkSideBar(String pageName, List<String> values) {
        $("#username").setValue("demo");
        $("#password").setValue("demo");
        $(byText("Sign in")).click();

        $$(".RaSidebar-fixed a").shouldHave(texts(values));
    }

    private static Stream<Arguments> dataForSideBar() {
        return Stream.of(
                Arguments.of("Main page", List.of(
                        "Dashboard",
                        "Orders",
                        "Invoices",
                        "Posters",
                        "Categories",
                        "Customers",
                        "Segments",
                        "Reviews"
                ))
        );
    }


}
