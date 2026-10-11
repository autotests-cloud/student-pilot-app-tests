package tests.e2e;

import tests.TestBase;
import annotations.Layer;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.Wait;
import static com.codeborne.selenide.Selenide.executeJavaScript;

/**
 * UI slice of the wrong-password contract: the form must show the readable error
 * and the failed attempt must not create a session. No HTTP status or raw JSON is
 * read in this test — that belongs to the api layer (ADR 009).
 */
@Layer("e2e")
@Epic("Authentication")
@Feature("Login")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Login")
class LoginTests extends TestBase {

    private static final String WRONG_CREDENTIALS_MESSAGE = "Wrong login or password";
    private static final String LOGIN_REQUIRED_MESSAGE =
            "Login is required (minimum 3 characters)";

    @Test
    @Tag("smoke")
    @Tag("negative")
    @DisplayName("Empty username shows validation error")
    void shouldShowValidationErrorWhenUsernameIsEmpty() {
        loginPage.openPage()
                .typePassword("password1")
                .submitExpectingError()
                .shouldHaveErrorMessage(LOGIN_REQUIRED_MESSAGE);
    }

    @Test
    @Tag("e2e")
    @Tag("negative")
    @DisplayName("Wrong password shows the readable error and creates no session token")
    void shouldShowErrorWhenPasswordIsWrong() {
        loginPage.openPage();
        shouldHaveNoAuthToken();

        loginPage.typeUsername("user1")
                .typePassword("wrongpassword")
                .submitExpectingError()
                .shouldHaveErrorMessage(WRONG_CREDENTIALS_MESSAGE);

        shouldHaveNoAuthToken();
    }

    @Step("Verify no authToken key exists in localStorage (plain or backend-scoped)")
    private void shouldHaveNoAuthToken() {
        Wait().until(driver -> {
            Long count = executeJavaScript(
                    "var n=0;for(var i=0;i<localStorage.length;i++){"
                            + "if(localStorage.key(i).indexOf('authToken')===0)n++;}return n;");
            return count != null && count == 0;
        });
    }
}
