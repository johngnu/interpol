package rest.client;

/**
 *
 * @author desktop
 */
public class Result {

    private int total;
    private Data _embedded;
    private Link self;
    private Link first;
    private Link netx;
    private Link last;

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

    public Link getSelf() {
        return self;
    }

    public void setSelf(Link self) {
        this.self = self;
    }

    public Link getFirst() {
        return first;
    }

    public void setFirst(Link first) {
        this.first = first;
    }

    public Link getNetx() {
        return netx;
    }

    public void setNetx(Link netx) {
        this.netx = netx;
    }

    public Link getLast() {
        return last;
    }

    public void setLast(Link last) {
        this.last = last;
    }

}
