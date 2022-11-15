package com.zkd.cloudStudy.vod.controller;



import com.zkd.cloudStudy.vod.service.FileService;
import com.zkd.cloudStudy.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "文件上传接口")
@RestController
@RequestMapping("/admin/vod/file")
//@CrossOrigin
public class FileUploadController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public Result uploadFile(@ApiParam(name = "file", value = "文件", required = true)
                                 @RequestParam("file") MultipartFile file) {
        String url = fileService.upload(file);
        return Result.ok(url).message("上传文件成功");
    }
}
