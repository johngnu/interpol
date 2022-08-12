package rest.client;

/**
 *
 * @author desktop
 */
public class Result {

    private int total;
    private Data _embedded;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Data getEmbedded() {
        return _embedded;
    }

    public void setEmbedded(Data _embedded) {
        this._embedded = _embedded;
    }

}
