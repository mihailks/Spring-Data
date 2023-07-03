package Entities.P06FootballBettingDatabase;

import Entities.BaseEntity;
import Entities.P06FootballBettingDatabase.Enums.Prediction;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "result_prediction")
public class ResultPrediction extends BaseEntity {
    private Prediction prediction;

    public ResultPrediction() {
    }

    public Prediction getPrediction() {
        return prediction;
    }

    public void setPrediction(Prediction prediction) {
        this.prediction = prediction;
    }
}
