package cn.csu.mypetstore_springboot.controllers.admin;

import cn.csu.mypetstore_springboot.Services.LogService;
import cn.csu.mypetstore_springboot.domain.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/log")
public class ServerLogController {
    private static final int DEFAULT_LOGS_CNT = 32;

    private final LogService logService;

    public ServerLogController(LogService logService) {
        this.logService = logService;
    }

    @RequestMapping("/offset/{offset}")
    ResponseEntity<List<Log>> getLogs(@PathVariable int offset) {
        List<Log> logs = logService.getLogs(DEFAULT_LOGS_CNT, offset);
        return ResponseEntity.ok(logs);
    }

}
