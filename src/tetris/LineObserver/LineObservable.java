package tetris.LineObserver;

import tetris.LineObserver.LineObserver;

import java.util.ArrayList;
import java.util.List;

public class LineObservable {
    private List<LineObserver> observers = new ArrayList<>();

    public void addObserver(LineObserver observer) {
        observers.add(observer);
    }

    public void notifyLineFilled(int lineNumber) {
        for (LineObserver observer : observers) {
            observer.lineFilled(lineNumber);
        }
    }
}