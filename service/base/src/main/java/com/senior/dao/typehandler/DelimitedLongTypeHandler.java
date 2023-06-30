
package com.senior.dao.typehandler;

public class DelimitedLongTypeHandler extends AbstractDelimitedTypeHandler<Long> {
    @Override
    protected Long convert(String s) {
        return Long.parseLong(s);
    }
}
