package pro.sky.java.course3.sql.controller;

import org.springframework.core.io.Resource;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pro.sky.java.course3.sql.record.AvatarRecord;
import pro.sky.java.course3.sql.service.AvatarService;

import javax.validation.constraints.Min;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatar")
@Validated
public class AvatarController {

    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping(value = "/{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable("studentId") long studentId,
                                               @RequestParam("avatar") MultipartFile multipartFile) {
        try {
            avatarService.upload(studentId, multipartFile);
            return ResponseEntity.ok("Сохранено!");
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "/fromDb/{studentId}")
    public ResponseEntity<byte[]> getAvatarFromDb(@PathVariable("studentId") long studentId) {
        Pair<byte[], String> result = avatarService.getAvatarFromDb(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.parseMediaType(result.getSecond()))
                .contentLength(result.getFirst().length)
                .body(result.getFirst());
    }

    @GetMapping(value = "/fromFs/{studentId}")
    public ResponseEntity<Resource> getAvatarFromFs(@PathVariable("studentId") long studentId) {
        try {
            Pair<Resource, String> result = avatarService.getAvatarFromFs(studentId);
            return ResponseEntity.status(HttpStatus.OK)
                    .contentType(MediaType.parseMediaType(result.getSecond()))
                    .contentLength(result.getFirst().contentLength())
                    .body(result.getFirst());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    public List<AvatarRecord> findByPagination(@RequestParam @Min(0) int page,
                                               @RequestParam @Min(0) int size) {
        return avatarService.findByPagination(page, size);
    }

}
