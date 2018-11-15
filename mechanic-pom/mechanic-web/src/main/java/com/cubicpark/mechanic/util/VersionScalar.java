package com.cubicpark.mechanic.util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;

public class VersionScalar implements TemplateScalarModel, Serializable {
    private static final long serialVersionUID = 8410290889285279226L;
    
    private static String VERSION = "?v=" +new SimpleDateFormat("yyyyMMddHH").format(Calendar.getInstance().getTime());
    
    public String getAsString() throws TemplateModelException {
        return VERSION;
    }

}
