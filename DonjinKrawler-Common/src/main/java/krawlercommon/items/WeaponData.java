package krawlercommon.items;

public class WeaponData extends ItemLocationData {

    public WeaponData() {
    }

    public WeaponData(int id, int x, int y) {
        super(id, x, y);
    }

    @Override
    public WeaponData deepCopy() throws CloneNotSupportedException {
        return (WeaponData) super.clone();
    }
}
