package krawlercommon;

public interface KrawlerCloneable extends Cloneable {

    Object clone() throws CloneNotSupportedException;

    Object deepCopy() throws CloneNotSupportedException;

}
