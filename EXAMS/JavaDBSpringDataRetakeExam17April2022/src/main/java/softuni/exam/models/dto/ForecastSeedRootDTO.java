package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastSeedRootDTO {
    @XmlElement(name = "forecast")
    List<ForecastSeedDTO> forecasts;

    public ForecastSeedRootDTO() {
    }

    public List<ForecastSeedDTO> getForecasts() {
        return forecasts;
    }

    public void setForecasts(List<ForecastSeedDTO> forecasts) {
        this.forecasts = forecasts;
    }
}
