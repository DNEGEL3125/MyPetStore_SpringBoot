package cn.csu.mypetstore_springboot.DTO;

import lombok.EqualsAndHashCode;
import lombok.Value;


public record AccountCountDTO(int timePeriod, long accountCount) {
}
