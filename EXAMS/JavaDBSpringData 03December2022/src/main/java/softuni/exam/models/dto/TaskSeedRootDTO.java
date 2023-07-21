package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tasks")
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskSeedRootDTO {
    @XmlElement(name = "task")
    List<TaskSeedDTO> tasks;

    public TaskSeedRootDTO() {
    }

    public List<TaskSeedDTO> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskSeedDTO> tasks) {
        this.tasks = tasks;
    }
}
