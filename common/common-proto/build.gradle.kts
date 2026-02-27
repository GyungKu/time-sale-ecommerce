import com.google.protobuf.gradle.*

plugins {
    // Java 플러그인과 함께 protobuf 플러그인을 적용 (버전은 호환성에 맞게 조정)
    id("com.google.protobuf") version "0.9.6"
}

dependencies {
    // gRPC와 Protobuf의 핵심 라이브러리들
    implementation("io.grpc:grpc-protobuf:1.76.3")
    implementation("io.grpc:grpc-stub:1.76.3")
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
}

// Protobuf 컴파일러 설정
protobuf {
    protoc {
        // 내 컴퓨터 운영체제에 맞는 protoc(컴파일러) 바이너리를 자동으로 다운로드 받습니다.
        artifact = "com.google.protobuf:protoc:3.7.0"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.60.0"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
            }
        }
    }
}

// (주의) 멀티 모듈 환경에서 다른 모듈(Order, Product)이
// 자동 생성된 Java 코드를 참조할 수 있도록 소스 디렉토리를 추가
sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/java", "build/generated/source/proto/main/grpc")
        }
    }
}