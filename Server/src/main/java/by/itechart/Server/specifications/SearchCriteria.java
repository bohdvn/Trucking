package by.itechart.Server.specifications;

public class SearchCriteria {
    private String key;
    private Object value;

    public SearchCriteria(final String key, final Object value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }
}
