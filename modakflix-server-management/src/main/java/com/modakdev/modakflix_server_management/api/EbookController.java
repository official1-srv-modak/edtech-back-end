package com.modakdev.modakflix_server_management.api;


import com.modakdev.modakflix_server_management.api.service.impl.EbookServiceImpl;
import com.modakdev.modakflix_server_management.api.service.impl.FileSystemServiceImpl;
import com.modakdev.models.nonentities.response.management.ModakFlixEbookCategoricalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ebooks")
public class EbookController {
    @Value("${ebook-folder-path}")
    private String ebookPath;

    @Autowired
    FileSystemServiceImpl fileSystemService;

    @Autowired
    EbookServiceImpl ebookService;

    @GetMapping("health-check")
    public String healthCheck()
    {
        return "ok";
    }

    @GetMapping("get-ebooks")
    public ModakFlixEbookCategoricalResponse getEbookFolderStructureAsJson() {
        ModakFlixEbookCategoricalResponse response = ebookService.getDirectoryStructure(ebookPath);
        return response;
    }

    @GetMapping("get-ebook/{id}")
    ResponseEntity<byte[]> getEbookFromId(@PathVariable("id")Long id)
    {
        return ebookService.streamEbook(id);
    }
}
