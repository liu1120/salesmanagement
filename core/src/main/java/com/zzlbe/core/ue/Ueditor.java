package com.zzlbe.core.ue;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Ueditor {
    private  String state;
    private  String url;
    private  String title;
    private  String original;
}