rootProject.name = "time-sale-ecommerce"

// Common 모듈들
include("common:common-exception")
include("common:common-security")

// User 서비스 모듈들
include("user-service:user-api")
include("user-service:user-application")
include("user-service:user-domain")
include("user-service:user-infrastructure")

// Product 서비스 모듈들 추가
include("product-service:product-api")
include("product-service:product-application")
include("product-service:product-domain")
include("product-service:product-infrastructure")

// Order 서비스 모듈들 추가
include("order-service:order-api")
include("order-service:order-application")
include("order-service:order-domain")
include("order-service:order-infrastructure")