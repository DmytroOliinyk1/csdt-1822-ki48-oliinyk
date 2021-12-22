package com.lpnu.virtual.library.core.asset.model;

import com.lpnu.virtual.library.core.preset.model.PresetCode;
import com.lpnu.virtual.library.metadata.field.model.Field;
import com.lpnu.virtual.library.metadata.field.model.FieldDto;
import com.lpnu.virtual.library.util.ValuesUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
public class AssetUploadContext {
    private List<FieldDto> fields;
    private MultipartFile file;
    private MultipartFile thumbnail;
    private List<String> errors;
    private String md5;
    private String fileName;
    private Long assetId;
    private PresetCode code;

    public AssetUploadContext(List<FieldDto> fields, MultipartFile file, MultipartFile thumbnail, PresetCode code) {
        this.fields = fields;
        this.file = file;
        this.thumbnail = thumbnail;
        this.fileName = file != null ? file.getOriginalFilename() : StringUtils.EMPTY;
        this.code = code;
    }

    public Boolean isAuthorUpload() {
        return PresetCode.AUTHOR_UPLOAD.equals(getCode());
    }

    public void addErrors(Collection<String> errors) {
        if (this.errors == null) {
            this.errors = new ArrayList<>();
        }
        this.errors.addAll(errors);
    }

    public Boolean hasErrors() {
        return ValuesUtils.hasElements(this.errors);
    }
}
