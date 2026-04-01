package org.example.expert.domain.manager.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.manager.entity.ManagerLog;
import org.example.expert.domain.manager.repository.ManagerLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ManagerLogService {
    private final ManagerLogRepository managerLogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ManagerLog saveManagerLog(Long requestId, Long managerId) {
        ManagerLog managerLog = new ManagerLog(requestId, managerId);
        managerLogRepository.save(managerLog);
        return managerLog;
    }

    @Transactional // 실패 시에는 롤백되어도 상관없음
    public void successLog(ManagerLog managerLog){
        managerLog.SuccessLog();
        managerLogRepository.save(managerLog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void failureLog(ManagerLog managerLog, String message){
        managerLog.FailureLog(message);
        managerLogRepository.save(managerLog);
    }
}
