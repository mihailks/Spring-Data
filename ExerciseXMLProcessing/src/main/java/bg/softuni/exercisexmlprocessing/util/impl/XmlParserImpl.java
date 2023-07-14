package bg.softuni.exercisexmlprocessing.util.impl;

import bg.softuni.exercisexmlprocessing.util.XmlParser;
import org.springframework.stereotype.Component;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

@Component
public class XmlParserImpl implements XmlParser {
    private JAXBContext jaxbContext;


    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromFile(String filePath, Class<T> tClass) throws JAXBException, FileNotFoundException {

        jaxbContext = JAXBContext.newInstance(tClass);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        return (T) unmarshaller.unmarshal(new FileReader(filePath));
    }

    @Override
    public <T> void writeToFile(String filePath, T entity) throws JAXBException {

        jaxbContext = JAXBContext.newInstance(entity.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(entity, new File(filePath));
    }
}
