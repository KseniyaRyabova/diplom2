package api;

import io.restassured.specification.RequestSpecification;

public class BaseClient {
    RequestSpecification specification;

    public BaseClient(RequestSpecification specification) {
        this.specification = specification;
    }
}
