package tests.api;

import annotations.Layer;
import api.ApiTestBase;
import api.model.LoginRequest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * API slice of the wrong-password contract: the 401 envelope is asserted here and
 * only here — the e2e test owns the visible form error (ADR 009 layer split).
 */
@Layer("api")
@Epic("Authentication")
@Feature("Authentication")
@Severity(SeverityLevel.CRITICAL)
@DisplayName("Auth API")
class AuthApiTests extends ApiTestBase {

    private static final String WRONG_CREDENTIALS_MESSAGE = "Wrong login or password";

    @Test
    @Tag("api")
    @DisplayName("POST /api/auth/login rejects a known user + wrong password with 401 and the contract body")
    void loginWithInvalidPassword() {
        given()
                .contentType(JSON)
                .body(new LoginRequest("user1", "wrongpassword"))
                .when()
                .post("/api/auth/login")
                .then()
                .statusCode(401)
                .body(matchesJsonSchemaInClasspath("schemas/error.json"))
                .body("message", equalTo(WRONG_CREDENTIALS_MESSAGE));
    }
}
