package cn.csu.mypetstore_springboot.controllers;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ImageController {

//    @GetMapping("/images/{fileName:.+}")
//    @ResponseBody
//    public Resource getImage(@PathVariable String fileName) throws MalformedURLException {
//        Path filePath = Paths.get("src/main/resources/static/images/", fileName);
//        Resource resource = new UrlResource(filePath.toUri());
//
//        if (resource.exists() || resource.isReadable()) {
//            return resource;
//        } else {
//            throw new RuntimeException("Could not read the file!");
//        }
//    }
}
