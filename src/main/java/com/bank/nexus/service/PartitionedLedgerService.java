package com.bank.nexus.service;

import com.bank.nexus.dto.PaymentResultDto;

import java.math.BigDecimal;

public interface PartitionedLedgerService {
     PaymentResultDto deposit(String accountId, BigDecimal amount);
}
