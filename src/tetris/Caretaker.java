package tetris;

import java.util.ArrayList;
import java.util.List;

public class Caretaker {
    public List<GameMemento> mementoList = new ArrayList<>();

    public void addMemento(GameMemento m) {
        mementoList.add(m);
    }

    public GameMemento getMemento(int index) {
        if (index >= 0 && index < mementoList.size()) {
            return mementoList.get(index);
        }
        return null;
    }

    public void clearMementos() {
        mementoList.clear();
    }
}