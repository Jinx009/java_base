package database.models.device.vo;

import java.io.File;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceLogVo {

    private String filename;
    private String path;
    private long size;
    private Integer type; //1.目录 2.文件
    private Long lastModifiedTime;

    public DeviceLogVo() {

    }

    public DeviceLogVo(File fs, String path) {
        this.filename = fs.getName();
        this.type = fs.isDirectory() ? 1 : 2;
        this.setSize(fs.length());
        this.path = path;
        this.lastModifiedTime = new Date(fs.lastModified()).getTime();
    }
	
}
