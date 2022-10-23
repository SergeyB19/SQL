
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AvatarService {

    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final String pathToAvatarDir;
    private final RecordMapper recordMapper;

    public AvatarService(AvatarRepository avatarRepository,
                         StudentRepository studentRepository,
                         @Value("${application.avatar.store}") String pathToAvatarDir,
                         RecordMapper recordMapper) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.pathToAvatarDir = pathToAvatarDir;
        this.recordMapper = recordMapper;
    }

    public void upload(long studentId,
                       MultipartFile multipartFile) throws IOException {
        String avatarName = UUID.randomUUID().toString();
        Path path = Paths.get(pathToAvatarDir).resolve(
                avatarName +
                        Optional.ofNullable(multipartFile.getOriginalFilename())
                                .map(fileName -> fileName.substring(fileName.lastIndexOf('.')))
                                .orElse("")
        );
        try (OutputStream outputStream = Files.newOutputStream(path);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) multipartFile.getSize())) {
            Student student = studentRepository.findById(studentId)
                    .orElseThrow(StudentNotFoundException::new);
            multipartFile.getInputStream().transferTo(byteArrayOutputStream);
            byte[] avatarBytes = byteArrayOutputStream.toByteArray();

            outputStream.write(avatarBytes);

            Avatar avatar = avatarRepository.findAvatarByStudent_Id(studentId)
                    .orElse(new Avatar());
            avatar.setData(avatarBytes);
            avatar.setFilePath(path.toAbsolutePath().toString());
            avatar.setFileSize(multipartFile.getSize());
            avatar.setStudent(student);
            avatar.setMediaType(multipartFile.getContentType());
            avatarRepository.save(avatar);
        }
    }

    public Pair<byte[], String> getAvatarFromDb(long studentId) {
        Avatar avatar = avatarRepository.findAvatarByStudent_Id(studentId)
                .orElseThrow(StudentNotFoundException::new);
        return Pair.of(avatar.getData(), avatar.getMediaType());
    }

    public Pair<Resource, String> getAvatarFromFs(long studentId) {
        Avatar avatar = avatarRepository.findAvatarByStudent_Id(studentId)
                .orElseThrow(StudentNotFoundException::new);
        return Pair.of(new FileSystemResource(avatar.getFilePath()), avatar.getMediaType());
    }

    public List<AvatarRecord> findByPagination(int page, int size) {
        return avatarRepository.findAll(PageRequest.of(page, size)).get()
                .map(recordMapper::toRecord)
                .collect(Collectors.toList());
    }

}
