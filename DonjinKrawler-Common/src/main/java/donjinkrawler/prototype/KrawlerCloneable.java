package donjinkrawler.prototype;

public interface KrawlerCloneable extends Cloneable {

    Object clone() throws CloneNotSupportedException;

    Object deepCopy() throws CloneNotSupportedException;

}
