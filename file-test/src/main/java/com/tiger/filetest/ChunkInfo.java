package com.tiger.filetest;

import lombok.Data;

/**
 * @Author Zenghu
 * @Date 2022年12月27日 21:34
 * @Description
 * @Version: 1.0
 **/
@Data
public class ChunkInfo {
    /**
     * 索引项index
     */
    private int index;
    /**
     * 起始位置
     */
    private long position;
    /**
     * 总的大小
     */
    private long size;
    /**
     * 记录索引
     */
    private long recordIndex;
    /**
     * 记录数
     */
    private int recordCount;
}
