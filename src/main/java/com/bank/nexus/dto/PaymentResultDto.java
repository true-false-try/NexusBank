package com.bank.nexus.dto;

import java.math.BigDecimal;
import java.util.UUID;

public sealed interface PaymentResultDto permits PaymentResultDto.SuccessDto, PaymentResultDto.FailureDto {
    record SuccessDto(UUID transactionalId, BigDecimal newBalance) implements PaymentResultDto{}
    record FailureDto(String reason) implements PaymentResultDto{}
}
