package com.example.football.models.dto.stats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "stats")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatsSeedRootDTO {
    @XmlElement(name = "stat")
    private List<StatsSeedDTO> stats;

    public StatsSeedRootDTO() {
    }

    public List<StatsSeedDTO> getStats() {
        return stats;
    }

    public void setStats(List<StatsSeedDTO> stats) {
        this.stats = stats;
    }
}
