package hiberspring.domain.dtos.products;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ProductSeedDTO {
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "clients")
    private Integer Client;
    @XmlElement(name = "branch")
    private String branch;

    public ProductSeedDTO() {
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    public Integer getClient() {
        return Client;
    }

    public void setClient(Integer client) {
        Client = client;
    }

    @NotNull
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
