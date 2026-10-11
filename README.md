# student-pilot-app-tests

Java autotests for the **student-pilot** student contour — Jenkins freestyle job
[student-pilot-app-tests-freestyle-java-allure3-full-attachments](https://jenkins.qa.guru/job/student-pilot-app-tests-freestyle-java-allure3-full-attachments/).

| Link | Role |
|------|------|
| Target app | [autotests.ai/stack](https://autotests.ai/stack/backend-java-spring/frontend-typescript-react/) |

## Layout

| Path | Role |
|------|------|
| `tests/` | Selenide + JUnit 5 + Allure 3 — Gradle pyramid (`testE2e`, `testApi`, …) |

Full etalon stack (backend, frontend, deploy) lives in [autotests-ai/autotests-ai-multistack-app](https://github.com/autotests-ai/autotests-ai-multistack-app).
