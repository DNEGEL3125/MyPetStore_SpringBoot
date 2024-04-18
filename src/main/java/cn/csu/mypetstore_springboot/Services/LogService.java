package cn.csu.mypetstore_springboot.Services;

import cn.csu.mypetstore_springboot.Repositories.LogRepository;
import cn.csu.mypetstore_springboot.domain.Log;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {
    private final LogRepository logRepository;

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> getLogs(int limit, int offset) {
        return logRepository.getByLimitAndOffset(limit, offset);
    }
}
