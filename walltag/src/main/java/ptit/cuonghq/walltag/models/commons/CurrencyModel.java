package ptit.cuonghq.walltag.models.commons;

import ptit.cuonghq.walltag.utils.Validate;

public class CurrencyModel {

    private long value;

    private String text;

    private String unit;

    public static CurrencyModel VND(long value) {
        return new CurrencyModel(value, Validate.convertCurrencyToString(value), "VND");
    }

    public CurrencyModel(long value, String text, String unit) {
        this.value = value;
        this.text = text;
        this.unit = unit;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
