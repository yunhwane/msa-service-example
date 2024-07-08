package com.example.userservice.client.error;

import feign.Response;
import feign.codec.ErrorDecoder;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        return switch (response.status()) {
            case 400 ->
                    new IllegalArgumentException("잘못된 요청입니다.");
            case 404 -> {
                if (methodKey.contains("getOrders")) {
                    yield new IllegalArgumentException("Order가 존재하지 않습니다.");
                }
                yield new IllegalArgumentException("리소스를 찾을 수 없습니다.");
            }
            default ->
                // 그 외 상태 코드에 대한 기본 예외 처리
                    new RuntimeException("예기치 않은 오류가 발생했습니다. 상태 코드: " + response.status());
        };
    }
}
