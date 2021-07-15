package com.wq.exporttableinfofromdb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class TableStructureInfo {
    private String column_name;
    private String data_type;
    private String column_type; // 详细类型
    private String character_maximum_length;
    private String is_nullable;
    private String column_default;
    private String column_comment;
}
