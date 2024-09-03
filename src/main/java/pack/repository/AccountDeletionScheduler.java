package pack.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import org.springframework.transaction.annotation.Transactional;
import pack.entity.UserDetailEntity;

@Component
public class AccountDeletionScheduler {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Autowired
    private UserRepository userRepository;


    // 매일 자정에 실행
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void deleteInactiveAccounts() {
        LocalDateTime thresholdDate = LocalDateTime.now().minusDays(7);  // 7일 전

        // 비활성화된 계정 중에서 7일이 지난 계정을 삭제
        List<UserDetailEntity> inactiveUsers = userDetailRepository.findByAccountDeleteDateBefore(thresholdDate);

        for (UserDetailEntity detail : inactiveUsers) {
            userRepository.deleteById(detail.getNo());  // UserEntity 삭제
            userDetailRepository.delete(detail);        // UserDetailEntity 삭제
        }
    }
}