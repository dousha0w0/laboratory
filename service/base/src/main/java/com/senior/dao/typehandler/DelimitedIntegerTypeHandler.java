
package com.senior.dao.typehandler;

public class DelimitedIntegerTypeHandler extends AbstractDelimitedTypeHandler<Integer> {
    @Override
    protected Integer convert(String s) {
        return Integer.parseInt(s);
    }
}
