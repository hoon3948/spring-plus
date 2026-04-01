package org.example.expert.domain.manager.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.expert.domain.common.entity.Timestamped;
import org.example.expert.domain.manager.enums.LogStatus;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "manager_logs")
public class ManagerLog extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long requestId; //요청한 유저 아이디

    @Column(nullable = false)
    private Long managerId; //요청된 유저 아이디

    @Enumerated(EnumType.STRING)
    private LogStatus status;
    private String message;

    public ManagerLog(Long requestId, Long managerId) {
        this.requestId = requestId;
        this.managerId = managerId;
        this.status = LogStatus.PENDING;
    }

    public void SuccessLog(){
        this.status = LogStatus.SUCCESS;
    }

    public void FailureLog(String message){
        this.status = LogStatus.FAILED;
        this.message = message;
    }

}
