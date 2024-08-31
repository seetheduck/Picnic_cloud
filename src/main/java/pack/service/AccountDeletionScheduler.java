package pack.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import pack.entity.UserDetailEntity;
import pack.repository.UserDetailRepository;
import pack.repository.UserRepository;

@Component
public class AccountDeletionScheduler {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserRepository userRepository;

    // 매일 자정에 실행 (예시)
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteInactiveAccounts() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusMonths(3);  // 3개월 전

        // 비활성화된 계정 중에서 3개월이 지난 계정을 삭제
        List<UserDetailEntity> inactiveUsers = userDetailRepository.findBySignoutDateBefore(thresholdDate);
        
        for (UserDetailEntity detail : inactiveUsers) {
            userRepository.deleteById(detail.getNo());  // UserMasterEntity 삭제
            userDetailRepository.delete(detail);        // UserDetailEntity 삭제
        }
    }
}