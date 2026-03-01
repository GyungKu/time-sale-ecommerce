package com.timesale.payment.infrastructure.client;

import com.timesale.common.exception.BusinessException;
import com.timesale.payment.application.dto.response.PgResponse;
import com.timesale.payment.application.port.PgClient;
import com.timesale.payment.domain.Payment;
import com.timesale.payment.infrastructure.dto.request.PgConfirmRequest;
import com.timesale.payment.infrastructure.dto.request.PgPrepareRequest;
import com.timesale.payment.infrastructure.dto.response.PgConfirmResponse;
import com.timesale.payment.infrastructure.exception.PgExternalErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PgClientImpl implements PgClient {

    private final PgFeignClient pgFeignClient;

    @Override
    public void preparePayment(Long orderId, Integer amount) {
        try {
            pgFeignClient.preparePayment(new PgPrepareRequest(orderId.toString(), amount));
        } catch (FeignException e) {
            log.error("PG 서버 결제 등록 요청 실패: status={}, body={}",
                e.status(), e.contentUTF8());
            throw new BusinessException(PgExternalErrorCode.EXTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public PgResponse confirmPayment(Payment payment, String authKey) {
        try {
            PgConfirmResponse response = pgFeignClient.confirmPayment(
                new PgConfirmRequest(payment.getOrderId().toString(),
                    payment.getAmount(), authKey));

            return response.toPgResponse();
        } catch (FeignException e) {
            log.error("PG 서버 결제 검증 요청 실패: status={}, body={}",
                e.status(), e.contentUTF8());
            throw new BusinessException(PgExternalErrorCode.findByStatus(e.status())
                .orElse(PgExternalErrorCode.EXTERNAL_SERVER_ERROR));
        }
    }
}
