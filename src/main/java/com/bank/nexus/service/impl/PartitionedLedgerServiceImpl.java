package com.bank.nexus.service.impl;

import com.bank.nexus.dto.PaymentResultDto;
import com.bank.nexus.service.PartitionedLedgerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
@Service
@RequiredArgsConstructor
public class PartitionedLedgerServiceImpl implements PartitionedLedgerService {
    private static final int NUMBER_OF_SLOTS = 10;
    private static final String nameOfMethod = PartitionedLedgerServiceImpl.class.getCanonicalName();
    private final ReentrantLock reentrantLock = new ReentrantLock();

    @Override
    public PaymentResultDto deposit(String accountId, BigDecimal amount) {
        log.info("{} was running", nameOfMethod);
        int targetSlot = ThreadLocalRandom.current().nextInt(0, NUMBER_OF_SLOTS);
        reentrantLock.lock();
        try {
            boolean update = executeSlotUpdateInDb(accountId, targetSlot, amount);
            if (!update) {
                return new PaymentResultDto.FailureDto("Optimistic Lock Failure on Slot " + targetSlot);
            }
            BigDecimal totalBalance = calculateTotalBalance(accountId);
            return new PaymentResultDto.SuccessDto(UUID.randomUUID(), totalBalance);
        } finally {
            reentrantLock.unlock();
        }

    }

    private boolean executeSlotUpdateInDb(String accountId, int slotId, BigDecimal amount) {
        return true;
    }
    private BigDecimal calculateTotalBalance(String accountId) {
        return BigDecimal.valueOf(10000.00);
    }
}
