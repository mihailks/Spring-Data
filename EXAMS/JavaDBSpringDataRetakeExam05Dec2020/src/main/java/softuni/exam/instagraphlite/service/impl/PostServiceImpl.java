package softuni.exam.instagraphlite.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.instagraphlite.models.dto.post.PostSeedRootDTO;
import softuni.exam.instagraphlite.models.entity.Post;
import softuni.exam.instagraphlite.repository.PictureRepository;
import softuni.exam.instagraphlite.repository.PostRepository;
import softuni.exam.instagraphlite.repository.UserRepository;
import softuni.exam.instagraphlite.service.PostService;
import softuni.exam.instagraphlite.util.ValidationUtil;
import softuni.exam.instagraphlite.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PostServiceImpl implements PostService {
    public static final String POSTS_FILE_PATH = "src/main/resources/files/posts.xml";
    private PostRepository postRepository;
    private ModelMapper modelMapper;
    private ValidationUtil validationUtil;
    private XmlParser xmlParser;
    private PictureRepository pictureRepository;
    private UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, PictureRepository pictureRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.pictureRepository = pictureRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean areImported() {
        return postRepository.count() > 0;
    }

    @Override
    public String readFromFileContent() throws IOException {
        return Files.readString(Path.of(POSTS_FILE_PATH));
    }

    @Override
    public String importPosts() throws IOException, JAXBException {
        StringBuilder stringBuilder = new StringBuilder();

        xmlParser.fromFile(POSTS_FILE_PATH, PostSeedRootDTO.class)
                .getPosts()
                .stream()
                .filter(postSeedDTO -> {
                    boolean isValid = validationUtil.isValid(postSeedDTO)
                            && userRepository.findFirstByUsername(postSeedDTO.getUser().getUsername()).isPresent()
                            && pictureRepository.findFirstByPath(postSeedDTO.getPicture().getPath()).isPresent();

                    stringBuilder.append(isValid
                                    ? String.format("Successfully imported post, made by %s",
                                    postSeedDTO.getUser().getUsername())
                                    : "Invalid post")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(postSeedDTO -> {
                    Post post = modelMapper.map(postSeedDTO, Post.class);
                    post.setUser(userRepository.findFirstByUsername(postSeedDTO.getUser().getUsername()).orElse(null));
                    post.setPicture(pictureRepository.findFirstByPath(postSeedDTO.getPicture().getPath()).orElse(null));
                    return post;
                })
                .forEach(postRepository::save);


        return stringBuilder.toString().trim();
    }
}
